/*
 * OriginHttpRequest    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example;

import com.example.http.HttpHeader;
import com.example.http.HttpMethod;
import com.example.http.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class OriginHttpRequest extends BufferHttpRequest {
    private HttpURLConnection mConnection;
    private String mUrl;

    private HttpMethod mMethod;

    public OriginHttpRequest(HttpURLConnection mConnection,HttpMethod mMethod, String mUrl){
        this.mConnection = mConnection;
        this.mUrl = mUrl;
        this.mMethod = mMethod;
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException {

        for (Map.Entry<String, String> entry : header.entrySet()) {
            mConnection.addRequestProperty(entry.getKey(), entry.getValue());
        }
        mConnection.setDoOutput(true);
        mConnection.setDoInput(true);
        mConnection.setRequestMethod(mMethod.name());
        mConnection.connect();
        if (data != null && data.length > 0) {
            OutputStream out = mConnection.getOutputStream();
            out.write(data, 0, data.length);
            out.close();
        }
        OriginHttpResponse response = new OriginHttpResponse(mConnection);

        return response;
    }

    @Override
    public HttpMethod getMethod() {
        return mMethod;
    }

    @Override
    public URI getUri() {
        return URI.create(mUrl);
    }


}