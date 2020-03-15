package com.jiaqi.torino.fetcher.service.fetcher;

import java.util.List;
import java.util.concurrent.Future;

import com.jiaqi.torino.fetcher.model.api.newsapi.Pager;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;

public interface NewsAPIFetcherService {
    public Future<List<NewsArticle>> fetchForRecent(Integer minutes, Pager pager);
}