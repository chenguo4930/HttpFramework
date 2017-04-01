/*
 * AsyncHttp    2017-03-30
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-30
 */
public class AsyncHttp {

    /**
     * 同步请求
     *
     * @param url
     */
    public static void sendRequest(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.println(response.body().toString());
                System.out.println(Thread.currentThread().getId());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步请求
     *
     * @param url
     */
    public static void sendAsyncRequest(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(Thread.currentThread().getId());
                }
            }
        });
    }

    public static void main(String args[]) {
        sendRequest("http://www.imooc.com");
        sendAsyncRequest("http://www.imooc.com");
    }

}