/*
 * OriginHttpRequestFactory    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;

import com.example.http.HttpMethod;
import com.example.http.HttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * 原始请求post
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class OriginHttpRequestFactory implements HttpRequestFactory{

    private HttpURLConnection mConnection;

    public OriginHttpRequestFactory(){

    }

    public void setReadTimeOut(int readTimeOut){
        mConnection.setReadTimeout(readTimeOut);
    }

    public void setConnectionTimeOut(int connectionTimeOut){
        mConnection.setConnectTimeout(connectionTimeOut);
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException {
        mConnection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(mConnection, method, uri.toString());
    }

}