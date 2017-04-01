/*
 * HttpRequestProvider    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;

import com.example.http.HttpMethod;
import com.example.http.HttpRequest;
import com.example.utils.Utils;

import java.io.IOException;
import java.net.URI;


/**
 * 网络请求提供者
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class HttpRequestProvider {

    private HttpRequestFactory mHttpRequestFactory;

    private static boolean OKHTTP_REQUEST = Utils.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    public HttpRequestProvider() {
        if (OKHTTP_REQUEST) {
            mHttpRequestFactory = new OkHttpRequestFactory();
        } else {
            //如果没有OkHttp3就用原生请求
            mHttpRequestFactory = new OriginHttpRequestFactory();
        }
    }

    public HttpRequest getHttpRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return mHttpRequestFactory.createHttpRequest(uri, httpMethod);
    }

    public HttpRequestFactory getmHttpRequestFactory() {
        return mHttpRequestFactory;
    }

    public void setmHttpRequestFactory(HttpRequestFactory mHttpRequestFactory) {
        this.mHttpRequestFactory = mHttpRequestFactory;
    }
}