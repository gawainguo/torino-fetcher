package com.jiaqi.torino.fetcher.service.newsapi;

import java.util.List;

import com.jiaqi.torino.fetcher.constant.NewsAPIConstants;
import com.jiaqi.torino.fetcher.exception.NewsAPIConfigException;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsAPIConfigServiceImpl implements NewsAPIConfigService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<String> getNewsAPISourcesConfig() {
        RList<String> rSources = redissonClient.getList(NewsAPIConstants.SOURCES_CONFIG_KEY);
        List<String> sources = rSources.readAll();
        return sources;
    }

    @Override
    public List<String> getNewsAPIDomainsConfig() {
        RList<String> rDomains = redissonClient.getList(NewsAPIConstants.DOMAINS_CONFIG_KEY);
        List<String> domains = rDomains.readAll();
        return domains;
    }

    @Override
    public void updateNewsAPISourcesConfig(List<String> sources) {
        RList<String> rSources = redissonClient.getList(NewsAPIConstants.SOURCES_CONFIG_KEY);
        rSources.delete();
        boolean success = rSources.addAll(sources);
        if (!success) {
            throw new NewsAPIConfigException("Config sources failed: failed to save to cache");
        }
        return;
    }

    @Override
    public void updateNewsAPIDomainConfig(List<String> domains) {
        RList<String> rDomains = redissonClient.getList(NewsAPIConstants.DOMAINS_CONFIG_KEY);
        rDomains.delete();
        boolean success = rDomains.addAll(domains);
        if (!success) {
            throw new NewsAPIConfigException("Config domains failed: failed to save to cache");
        }
        return;
    }
    
}