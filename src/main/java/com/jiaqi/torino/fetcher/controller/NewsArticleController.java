package com.jiaqi.torino.fetcher.controller;

import java.time.ZonedDateTime;
import java.util.List;

import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleFilter;
import com.jiaqi.torino.fetcher.model.api.newsapi.Pager;
import com.jiaqi.torino.fetcher.model.domain.NewsArticle;
import com.jiaqi.torino.fetcher.model.web.Response;
import com.jiaqi.torino.fetcher.service.message.NewsMessageService;
import com.jiaqi.torino.fetcher.service.newsapi.NewsAPIConfigService;
import com.jiaqi.torino.fetcher.service.newsapi.NewsAPIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class NewsArticleController {

    @Autowired
    private NewsAPIService newsAPIService;

    @Autowired
    private NewsAPIConfigService newsAPIConfigService;

    @Autowired
    private NewsMessageService newsMessageService;

    @GetMapping("/headlines/{countryCode}")
    public Response getHeadlinesByCountry(@PathVariable String countryCode) {
        return Response.success().data("articles", newsAPIService.getTopHeadlinesByCountry(countryCode));
    }

    @GetMapping("/test")
    public Response test() {
        Pager pager = new Pager(1, 100);
        ArticleFilter filter = ArticleFilter.builder()
                .sources(newsAPIConfigService.getNewsAPISourcesConfig())
                .domains(newsAPIConfigService.getNewsAPIDomainsConfig())
                .from(ZonedDateTime.now().minusMinutes(40))
                .to(ZonedDateTime.now().minusMinutes(20))
                .build();
        List<NewsArticle> articles = newsAPIService.getArticlesByFilter(filter, pager);
        articles.stream().forEach(article ->
                newsMessageService.produceNewsMessage(article));
        return Response.success().data("articles", articles);
    }
}