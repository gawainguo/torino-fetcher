package com.jiaqi.torino.fetcher.service.newsapi;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.jiaqi.torino.fetcher.exception.NewsAPIException;
import com.jiaqi.torino.fetcher.handler.api.NewsAPIHandler;
import com.jiaqi.torino.fetcher.model.api.newsapi.Article;
import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleFilter;
import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleList;
import com.jiaqi.torino.fetcher.model.api.newsapi.Pager;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class NewsAPIServiceImpl implements NewsAPIService {

    private static final Integer HEADLINES_PAGE_SIZE = 20;

    @Autowired
    private NewsAPIHandler newsAPIHandler;

    private Logger logger = LogManager.getLogger();

    /**
     * Get top headline articles by country code. Will call News API by pages to get
     * all articles.
     * 
     * @param countryCode 2-letter ISO 3166-1 code of the country
     * @return List of NewsArticle
     */
    @Override
    public List<NewsArticle> getTopHeadlinesByCountry(String countryCode) {
        List<NewsArticle> articles = Lists.newArrayList();
        Pager pager = new Pager(1, HEADLINES_PAGE_SIZE);
        do {
            ArticleList articleList = null;
            try {
                articleList = newsAPIHandler.getTopHeadlines(countryCode, pager);
            } catch (NewsAPIException e) {
                logger.warn("Get articles from news api failed: " + e.getMessage());
                break;
            }
            if (articleList == null || CollectionUtils.isEmpty(articleList.getArticles())) {
                logger.info("No more articles to get, return the article list");
                break;
            }
            articles.addAll(
                    articleList.getArticles().stream().map(Article::toNewsArticle).collect(Collectors.toList()));
            logger.info(String.format("Get %s articles from news api, total: %s", HEADLINES_PAGE_SIZE,
                    articleList.getTotalResults()));
            if (articleList.getArticles().size() < HEADLINES_PAGE_SIZE) {
                logger.info("Current page of article is not full, will not query for next page.");
                break;
            }
            pager.nextPage();
        } while (true);
        return articles;
    }

    @Override
    public List<NewsArticle> getArticlesByFilter(ArticleFilter filter, Pager pager) {
        List<NewsArticle> articles = Lists.newArrayList();
        logger.info("Query articles by filter: " + filter.toString());
        ArticleList articleList = newsAPIHandler.getArticles(filter, pager);
        if (articleList != null && !CollectionUtils.isEmpty(articleList.getArticles())) {
            articles = articleList.getArticles().stream().map(Article::toNewsArticle).collect(Collectors.toList());
        }
        logger.info("Query articles success: total " + articles.size());
        return articles;
    }
}
