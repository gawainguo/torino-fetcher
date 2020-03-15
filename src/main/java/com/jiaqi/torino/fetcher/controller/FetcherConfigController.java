package com.jiaqi.torino.fetcher.controller;

import java.util.List;

import com.jiaqi.torino.fetcher.model.web.Response;
import com.jiaqi.torino.fetcher.service.newsapi.NewsAPIConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class FetcherConfigController {

    @Autowired
    private NewsAPIConfigService newsAPIConfigService;

    @GetMapping("/newsapi/sources")
    public Response getNewsAPISources() {
        return Response.success().data("sources", newsAPIConfigService.getNewsAPISourcesConfig());
    }

    @PostMapping("/newsapi/sources")
    public Response updateNewsAPISources(@RequestParam List<String> sources) {
        newsAPIConfigService.updateNewsAPISourcesConfig(sources);
        return Response.success().data("sources", sources);
    }

    @GetMapping("/newsapi/domains")
    public Response getNewsAPIDomains() {
        return Response.success().data("domains", newsAPIConfigService.getNewsAPIDomainsConfig());
    }

    @PostMapping("/newsapi/domains")
    public Response updateNewsAPIDomains(@RequestParam List<String> domains) {
        newsAPIConfigService.updateNewsAPIDomainConfig(domains);
        return Response.success().data("domains", domains);
    }
}