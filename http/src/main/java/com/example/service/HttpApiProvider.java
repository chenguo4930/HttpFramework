/*
 * HttpApiProvider    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.service;

import com.example.http.HttpMethod;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class HttpApiProvider {

    private static final String ENCODING = "utf-8";

    private static WorkStation mWorkStation = new WorkStation();

    public static byte[] encodeParam(Map<String, String> value) {
        if (value == null || value.size() == 0) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        int count = 0;
        try {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                buffer.append(URLEncoder.encode(entry.getKey(), ENCODING)).append("=")
                        .append(URLEncoder.encode(entry.getValue(), ENCODING));
                if (count != value.size() - 1) {
                    buffer.append("&");
                }
                count++;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return buffer.toString().getBytes();
    }

    public static void helloWord(String url, Map<String, String> value, HttpEntityResponse<String> response) {
        HttpRequestEntity requestEntity = new HttpRequestEntity();
        requestEntity.setUrl(url);
        requestEntity.setMethod(HttpMethod.POST);
        requestEntity.setData(encodeParam(value));
        requestEntity.setResponse(response);
        mWorkStation.add(requestEntity);
    }
}