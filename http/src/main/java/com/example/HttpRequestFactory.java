/*
 * HttpRequestFactory    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;


import com.example.http.HttpMethod;

import java.net.URI;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public interface HttpRequestFactory {

   OkHttpRequest createHttpRequest(URI uri, HttpMethod method);
}