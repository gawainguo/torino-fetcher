package com.jiaqi.torino.fetcher.service.newsapi;

import java.util.List;

import com.jiaqi.torino.fetcher.constant.NewsAPIConstants;
import com.jiaqi.torino.fetcher.exception.NewsAPIConfigException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NewsAPIConfigServiceImpl implements NewsAPIConfigService {

    @Autowired
    private StringRedisTemplate redis;

    @Override
    public List<String> getNewsAPISourcesConfig() {
        List<String> sources = redis.opsForList().range(NewsAPIConstants.SOURCES_CONFIG_KEY, 0, -1);
        return sources;
    }

    @Override
    public List<String> getNewsAPIDomainsConfig() {
        List<String> sources = redis.opsForList().range(NewsAPIConstants.DOMAINS_CONFIG_KEY, 0, -1);
        return sources;
    }

    @Override
    public void updateNewsAPISourcesConfig(List<String> sources) {
        redis.delete(NewsAPIConstants.SOURCES_CONFIG_KEY);
        redis.opsForList().leftPushAll(NewsAPIConstants.SOURCES_CONFIG_KEY, sources);
    }

    @Override
    public void updateNewsAPIDomainConfig(List<String> domains) {
        redis.delete(NewsAPIConstants.DOMAINS_CONFIG_KEY);
        redis.opsForList().leftPushAll(NewsAPIConstants.DOMAINS_CONFIG_KEY, domains);
    }
    
}