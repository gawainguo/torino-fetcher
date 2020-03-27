package com.jiaqi.torino.fetcher.service.fetcher;

import java.util.List;

import com.jiaqi.torino.fetcher.model.domain.NewsArticle;

public interface FetcherStatsService {

    Integer updateDailyDomainStats(List<NewsArticle> articles, String fetcher);

}