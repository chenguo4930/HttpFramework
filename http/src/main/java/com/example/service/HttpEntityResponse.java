package com.example.service;

/**
 * 网络响应接口回调
 * Created by cheng on 2017/4/1.
 */

public abstract class HttpEntityResponse<T> {
    public abstract void success(HttpRequestEntity requestEntity, T data);

    public abstract void fail(int errorCode, String errorMsg);
}
