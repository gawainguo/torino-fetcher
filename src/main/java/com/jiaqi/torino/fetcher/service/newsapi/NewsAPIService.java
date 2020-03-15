package com.jiaqi.torino.fetcher.service.newsapi;

import java.time.ZonedDateTime;
import java.util.List;

import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleFilter;
import com.jiaqi.torino.fetcher.model.api.newsapi.Pager;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;

public interface NewsAPIService {
    
    List<NewsArticle> getTopHeadlinesByCountry(String countryCode);

    List<NewsArticle> getArticlesByFilter(ArticleFilter filter, Pager pager);

}
