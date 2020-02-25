package com.jiaqi.torino.fetcher.handler.api;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jiaqi.torino.fetcher.constant.APIConstants;
import com.jiaqi.torino.fetcher.constant.NewsAPIConstants;
import com.jiaqi.torino.fetcher.exception.NewsAPIException;
import com.jiaqi.torino.fetcher.handler.HttpHandler;
import com.jiaqi.torino.fetcher.model.api.newsapi.ArticleList;
import com.jiaqi.torino.fetcher.model.api.newsapi.Pager;
import com.jiaqi.torino.fetcher.model.web.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import okhttp3.Response;

@Component
public class NewsAPIHandler {

    @Autowired
    private HttpHandler httpHandler;

    private String token = "19f8edd7bfd9440091a636dea19efef8";

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
            throw new NewsAPIException("News API response error code:" + res.code());
        } catch(IOException e) {
            throw new NewsAPIException(e.getMessage());
        }
    }

    private List<Parameter> getPagerParams(Pager pager) {
        List<Parameter> params = Lists.newArrayList();
        if (pager == null) {
            return params;
        }
        params.add(Parameter.builder().key("page").value(pager.getPageSize().toString()).build());
        params.add(Parameter.builder().key("pageSize").value(pager.getPageSize().toString()).build());
        return params;
    }
}