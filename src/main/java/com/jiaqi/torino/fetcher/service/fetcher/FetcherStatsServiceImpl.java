package com.jiaqi.torino.fetcher.service.fetcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.jiaqi.torino.fetcher.constant.RedisKeyConstants;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FetcherStatsServiceImpl implements FetcherStatsService {

    private Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public Integer updateDailyDomainStats(List<NewsArticle> articles, String fetcher) {
        Map<String, List<NewsArticle>> domainCount = Maps.newHashMap();
        if (CollectionUtils.isEmpty(articles)) {
            logger.warn("No articles fetched, abort stats");
            return 0;
        }
        domainCount = articles.stream()
                .collect(Collectors.groupingBy(article -> getArticleDomain(article)));
        
        domainCount.entrySet().parallelStream().forEach(entry ->
                increDomainDailyStats(entry.getKey(), fetcher, entry.getValue().size()));
        
        return articles.size();
    }

    private String getArticleDomain(NewsArticle article) {
        String url = article.getUrl();
        try {
            URL urlObj = new URL(url);
            return urlObj.getHost();
        } catch (MalformedURLException e) {
            logger.warn("URL format is incorrect: " + article.getUrl());
            return "";
        }
    }

    private void increDomainDailyStats(String domain, String source, Integer count) {
        if (StringUtils.isEmpty(domain)) {
            return;
        }
        String key = RedisKeyConstants.getFetcherDailyDomainStatsKey(domain, ZonedDateTime.now(), source);
        logger.debug("Daily stats redis key generated: " + key);

        redis.opsForValue().setIfAbsent(key, "0", 24 * 5, TimeUnit.HOURS);
        Long current = redis.opsForValue().increment(key, count);

        logger.info(String.format("Add domain %s count to %s", domain, current));
    }
}