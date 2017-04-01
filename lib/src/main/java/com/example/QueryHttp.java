/*
 * QueryHttp    2017-03-30
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;

import java.io.IOException;

import okhttp3.HttpUrl;
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
public class QueryHttp {

    public static void main(String args[]) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl httpUrl = HttpUrl.parse("https://free-api.heweather.com/v5/weather")
                .newBuilder()
                .addQueryParameter("city", "shenzhen")
                .addQueryParameter("key", "a2d9ebf1c1bc46598ec2e148cc295b94")
                .build();

        System.out.println(httpUrl.toString());
        String url = httpUrl.toString();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                System.out.print(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}