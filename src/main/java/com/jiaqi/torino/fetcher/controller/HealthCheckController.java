package com.jiaqi.torino.fetcher.controller;

import com.jiaqi.torino.fetcher.handler.api.NewsAPIHandler;
import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleList;
import com.jiaqi.torino.fetcher.model.web.Response;
import com.jiaqi.torino.fetcher.service.message.NewsMessageService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private NewsAPIHandler handler;

    @Autowired
    private NewsMessageService newsMessageService;

    @GetMapping("/check")
    public Response healthCheck() {
        ArticleList articles = handler.getTopHeadlines("us", null);
        articles.getArticles().stream().forEach(article ->
                newsMessageService.produceNewsMessage(article.toNewsArticle()));
        return Response.success().data("result", articles);
    }
}