package com.jiaqi.torino.fetcher.service.newsapi;

import java.util.List;

public interface NewsAPIConfigService {

    List<String> getNewsAPISourcesConfig();

    List<String> getNewsAPIDomainsConfig();

    void updateNewsAPISourcesConfig(List<String> sources);

    void updateNewsAPIDomainConfig(List<String> domains);
}