package com.jiaqi.torino.fetcher.handler;

import java.io.IOException;
import java.util.List;

import com.jiaqi.torino.fetcher.model.web.Parameter;

import org.springframework.stereotype.Component;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class HttpHandler {

    public Response doGet(String url, List<Parameter> headers, List<Parameter> queryParams) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (Parameter param : queryParams) {
            urlBuilder = urlBuilder.addQueryParameter(param.getKey(), param.getValue());
        }
        for (Parameter param: headers) {
            requestBuilder = requestBuilder.addHeader(param.getKey(), param.getValue());
        }
        Request request = requestBuilder.get().url(urlBuilder.build()).build();
        return client.newCall(request).execute();
    }
}