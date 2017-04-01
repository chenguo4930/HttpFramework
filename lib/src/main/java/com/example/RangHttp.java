/*
 * RangHttp    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public class RangHttp {

    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.imooc.com")
                .addHeader("Range", "bytes=0-2")   // [0,2]闭区间
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.print("content-length:" + response.body().contentLength());
            if (response.isSuccessful()) {
                System.out.print(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}