package com.jiaqi.torino.fetcher.exception;

public class NewsAPIException extends RuntimeException {

    public NewsAPIException(String msg) {
        super("News API Exception: " + msg);
    }
}
