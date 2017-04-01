/*
 * OkHttpRequestFactory    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;


import com.example.http.HttpMethod;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class OkHttpRequestFactory implements HttpRequestFactory{

    private OkHttpClient mClient;

    protected OkHttpRequestFactory(){
        this.mClient = new OkHttpClient();
    }

    protected OkHttpRequestFactory(OkHttpClient client){
        this.mClient = client;
    }

    public void setReadTimeOut(int readTimeOut){
        this.mClient = mClient.newBuilder().readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
                .build();
    }

    public void setWriteTimeOut(int writeTimeOut){
        this.mClient = mClient.newBuilder().writeTimeout(writeTimeOut,TimeUnit.MILLISECONDS)
                .build();
    }

    public void setConnectionTime(int connectionTime){
        this.mClient = mClient.newBuilder().connectTimeout(connectionTime,TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public OkHttpRequest createHttpRequest(URI uri, HttpMethod method) {
        return new OkHttpRequest(mClient,method,uri.toString());
    }


}