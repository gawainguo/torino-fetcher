package com.jiaqi.torino.fetcher.model.api.newsapi;

import com.jiaqi.torino.fetcher.constant.NewsAPIConstants;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private Source source;

    private String author;

    private String title;

    private String descrption;

    private String url;

    private String urlToImage;

    private String publishedAt;

    private String content;

    public NewsArticle toNewsArticle() {
        return NewsArticle.builder().fetcherSource(NewsAPIConstants.FETCHER_NAME)
                .url(url)
                .title(title)
                .publishTime(publishedAt)
                .build();
    }

}