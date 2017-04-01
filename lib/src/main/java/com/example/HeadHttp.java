/*
 * HeadHttp    2017-03-30
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;

import java.io.IOException;

import okhttp3.Headers;
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
public class HeadHttp {

    public static void main (String args[]){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://www.imooc.com")
                .addHeader("User-Agent","from seven http")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(headers.name(i)+":"+headers.value(i));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}