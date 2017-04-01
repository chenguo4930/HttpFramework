/*
 * HttpRunnable    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.service;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class HttpRunnable implements Runnable {

    private HttpRequest mHttpRequest;

    private HttpRequestEntity mRequest;

    private WorkStation mWorkStation;

    public HttpRunnable(HttpRequest httpRequest, HttpRequestEntity request, WorkStation workStation) {
        this.mHttpRequest = httpRequest;
        this.mRequest = request;
        this.mWorkStation = workStation;
    }

    @Override
    public void run() {
        try {
            mHttpRequest.getBody().write(mRequest.getData());
            HttpResponse response = mHttpRequest.execute();

            if (response.getStatus().isSuccess() && mRequest.getResponse() != null) {
                mRequest.getResponse().success(mRequest, new String(getData(response)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mWorkStation.finish(mRequest);
        }
    }

    public byte[] getData(HttpResponse response) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) response.getContentLength());

        int len;
        byte[] data = new byte[512];
        try {
            while ((len = response.getBody().read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}