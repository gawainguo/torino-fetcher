package com.jiaqi.torino.fetcher.service.fetcher;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.jiaqi.torino.fetcher.constant.NewsAPIConstants;
import com.jiaqi.torino.fetcher.exception.NewsAPIException;
import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleFilter;
import com.jiaqi.torino.fetcher.model.api.newsapi.Pager;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;
import com.jiaqi.torino.fetcher.service.message.NewsMessageService;
import com.jiaqi.torino.fetcher.service.newsapi.NewsAPIConfigService;
import com.jiaqi.torino.fetcher.service.newsapi.NewsAPIService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class NewsAPIFetcherServiceImpl implements NewsAPIFetcherService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private NewsAPIConfigService newsAPIConfigService;

    @Autowired
    private NewsMessageService newsMessageService;

    @Autowired
    private NewsAPIService newsAPIService;

    @Autowired
    private FetcherStatsService statsService;

    private ExecutorService fetcherTaskPool = Executors.newFixedThreadPool(10);

    @Override
    public Future<List<NewsArticle>> fetchForRecent(Integer minutes, Pager pager) {
        if (minutes <= 0) {
            throw new NewsAPIException("Last minutes cannot be negative or 0");
        }

        Pager taskPager = pager == null ? new Pager(1, 100) : pager;

        ZonedDateTime now = ZonedDateTime.now();
        // News API have 15 minutes delay
        ZonedDateTime to = now.minusMinutes(20);
        ZonedDateTime from = to.minusMinutes(minutes);
        ArticleFilter filter = ArticleFilter.builder()
                .sources(newsAPIConfigService.getNewsAPISourcesConfig())
                .domains(newsAPIConfigService.getNewsAPIDomainsConfig())
                .language("en")
                .from(from)
                .to(to)
                .build();
        
        logger.info(String.format("Fetch from NewsAPI: from %s to %s", from, to));
        return fetcherTaskPool.submit(() -> {
            List<NewsArticle> articles = newsAPIService.getArticlesByFilter(filter, taskPager);
            if (CollectionUtils.isEmpty(articles)) {
                logger.warn("No articles fetched from NewsAPI");
                return articles;
            }
            logger.info("Fetched articles count: " + articles.size());
    
            // Submit stats data
            statsService.updateDailyDomainStats(articles, NewsAPIConstants.FETCHER_NAME);

            // Produce to message queue
            articles.stream().forEach(article ->
                    newsMessageService.produceNewsMessage(article));
            return articles;
        });
    }
}