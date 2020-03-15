package com.jiaqi.torino.fetcher.constant;

public class NewsAPIConstants {

    public static final String FETCHER_NAME = "NEWS_API";

    public static final String NEWS_API_DOMAIN = "https://newsapi.org/v2";

    public static final String TOP_HEADLINES_URL = "/top-headlines";

    public static final String ARTICLES_URL = "/everything";

    public static String getUrl(String path) {
        return NEWS_API_DOMAIN + path;
    }

    public static final String SOURCES_CONFIG_KEY = "news-api-sources";

    public static final String DOMAINS_CONFIG_KEY = "news-api-domains";
}