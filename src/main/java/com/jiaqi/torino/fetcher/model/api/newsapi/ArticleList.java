package com.jiaqi.torino.fetcher.model.api.newsapi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleList {

    private String status;

    private Integer totalResults;

    private List<Article> articles;
}