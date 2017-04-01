/*
 * HttpRequest    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.service;

import com.example.http.HttpMethod;

/**
 * 网络请求对象
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class HttpRequestEntity {
    private String url;
    private HttpMethod method;
    private byte[] data;
    private HttpEntityResponse response;
    private String contentType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public HttpEntityResponse getResponse() {
        return response;
    }

    public void setResponse(HttpEntityResponse response) {
        this.response = response;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}