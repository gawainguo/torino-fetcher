package com.jiaqi.torino.fetcher.exception;

public class NewsAPIException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NewsAPIException(String msg) {
        super("News API Exception: " + msg);
    }
}
