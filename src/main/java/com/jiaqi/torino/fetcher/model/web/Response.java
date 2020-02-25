package com.jiaqi.torino.fetcher.model.web;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private Map<String, Object> data = new HashMap();
    private boolean success;

    public Map<String, Object> getData() {
        return data;
    }

    public void setSuccess(boolean value) {
        success = value;
    }

    public boolean getSuccess() {
        return success;
    }
    
    public static Response success() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public Response data(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }
}