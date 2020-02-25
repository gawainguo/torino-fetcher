package com.jiaqi.torino.fetcher.constant;

public class NewsAPIConstants {

    public static final String FETCHER_NAME = "NEWS_API";

    public static final String NEWS_API_DOMAIN = "https://newsapi.org/v2";

    public static final String TOP_HEADLINES_URL = "/top-headlines";

    public static String getUrl(String path) {
        return NEWS_API_DOMAIN + path;
    }
}