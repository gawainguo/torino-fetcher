package com.jiaqi.torino.fetcher.handler.api;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jiaqi.torino.fetcher.constant.APIConstants;
import com.jiaqi.torino.fetcher.constant.NewsAPIConstants;
import com.jiaqi.torino.fetcher.exception.NewsAPIException;
import com.jiaqi.torino.fetcher.handler.HttpHandler;
import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleFilter;
import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleList;
import com.jiaqi.torino.fetcher.model.api.newsapi.Pager;
import com.jiaqi.torino.fetcher.model.web.Parameter;
import com.jiaqi.torino.fetcher.utils.TimeUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import okhttp3.Response;

@Component
public class NewsAPIHandler {

    @Autowired
    private HttpHandler httpHandler;

    private String token = "19f8edd7bfd9440091a636dea19efef8";

    public ArticleList getArticles(ArticleFilter filter, Pager pager) {
        List<Parameter> params = Lists.newArrayList();
        params.addAll(getPagerParams(pager));
        params.addAll(getArticleFilterParams(filter));
        return getNewsApi(NewsAPIConstants.getUrl(NewsAPIConstants.ARTICLES_URL), params, ArticleList.class);
    }

    public ArticleList getTopHeadlines(String country, Pager pager) {
        List<Parameter> params = Lists.newArrayList();
        params.add(Parameter.builder().key("country").value(country).build());
        params.addAll(getPagerParams(pager));
        ArticleList articles = getNewsApi(
                NewsAPIConstants.getUrl(NewsAPIConstants.TOP_HEADLINES_URL), params, ArticleList.class);
        return articles;
    }

    private <T> T getNewsApi(String url, List<Parameter> queryParams, Class<T> cls) {
        List<Parameter> headers = Lists.newArrayList();
        headers.add(getAuthHeader());
        String res = doGetForNewsAPI(url, headers, queryParams);
        return new Gson().fromJson(res, cls);
    }

    private Parameter getAuthHeader() {
        String authHeader = "Bearer " + this.token;
        return Parameter.builder()
                .key(APIConstants.AUTHORIZATION_HEADER)
                .value(authHeader)
                .build();
    }

    private String doGetForNewsAPI(String url, List<Parameter> headers, List<Parameter> queryParams) {
        Response res = null;
        try {
            res = httpHandler.doGet(url, headers, queryParams);
            if (res.code() == APIConstants.RESPONSE_CODE_OK) {
                return res.body().string();
            }
            throw new NewsAPIException("News API response error code:" + res.code() + " \n" + res.body().string());
        } catch(IOException e) {
            throw new NewsAPIException(e.getMessage());
        }
    }

    private List<Parameter> getPagerParams(Pager pager) {
        List<Parameter> params = Lists.newArrayList();
        if (pager == null) {
            return params;
        }
        params.add(Parameter.builder().key("page").value(pager.getPage().toString()).build());
        params.add(Parameter.builder().key("pageSize").value(pager.getPageSize().toString()).build());
        return params;
    }

    private List<Parameter> getArticleFilterParams(ArticleFilter filter) {
        List<Parameter> params = Lists.newArrayList();
        if (filter == null) {
            throw new NewsAPIException("Error when query articles from NewsAPI: filter cannot be null");
        }
        if (filter.getFrom() == null || filter.getTo() == null) {
            throw new NewsAPIException("Error when query articles from NewsAPI: time range cannot be null");
        }

        params.add(Parameter.builder().key("from").value(TimeUtils.formatUTC(filter.getFrom())).build());
        params.add(Parameter.builder().key("to").value(TimeUtils.formatUTC(filter.getTo())).build());

        if (!CollectionUtils.isEmpty(filter.getSources())) {
            String sources = String.join(",", filter.getSources());
            params.add(Parameter.builder().key("sources").value(sources).build());
        }

        if (!CollectionUtils.isEmpty(filter.getDomains())) {
            String domains = String.join(",", filter.getDomains());
            params.add(Parameter.builder().key("domains").value(domains).build());
        }
        
        if (!StringUtils.isEmpty(filter.getLanguage())) {
            params.add(Parameter.builder().key("language").value(filter.getLanguage()).build());
        }

        if (!StringUtils.isEmpty(filter.getKeyword())) {
            params.add(Parameter.builder().key("q").value(filter.getKeyword()).build());
        }

        return params;
    }
}