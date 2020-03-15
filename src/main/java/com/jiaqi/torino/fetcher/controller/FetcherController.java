package com.jiaqi.torino.fetcher.controller;

import com.jiaqi.torino.fetcher.model.web.Response;
import com.jiaqi.torino.fetcher.service.fetcher.NewsAPIFetcherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fetcher")
public class FetcherController {

    @Autowired
    private NewsAPIFetcherService newsAPIFetcher;

    @PostMapping("/news-api/recent")
    public Response fetchNewsAPIByRecentTime(@RequestParam Integer minutes) {
        newsAPIFetcher.fetchForRecent(minutes, null);
        return Response.success();
    }
}
