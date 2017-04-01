/*
 * HttpManager    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload.http;

import android.content.Context;

import com.example.filedownload.file.FileStorageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
 * @since 2017-03-31
 */
public class HttpManager {

    private static final HttpManager sManager = new HttpManager();
    public static final int NETWORK_ERROR_CODE = 1;
    public static final int CONTENT_LENGTH_ERROR_CODE = 2;
    public static final int TASK_RUNNING_ERROR_CODE = 3;

    private OkHttpClient mClient;

    private Context mContext;

    public static HttpManager getInstance() {
        return sManager;
    }

    private HttpManager() {
        mClient = new OkHttpClient();
    }

    public void init(Context context) {
        mContext = context;
    }

    /**
     * 同步请求
     *
     * @param url
     * @return
     */
    public Response syncRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步请求,范围请求
     *
     * @param url
     * @return
     */
    public Response syncRequestRange(String url, long start, long end) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Range", "bytes=" + start + "-" + end)
                .build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步请求
     *
     * @param url
     * @param callback
     */
    public void asyncRequest(final String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(callback);
    }


    /**
     * 异步请求
     */
    public void asyncRequest(final String url, final DownloadCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && callBack != null) {
                    callBack.fail(NETWORK_ERROR_CODE, "请求失败");
                }
                File file = FileStorageManager.getInstance().getFileByName(url);

                byte[] buffer = new byte[1024 * 500];
                int len;
                FileOutputStream fileOut = new FileOutputStream(file);
                InputStream inStream = response.body().byteStream();
                while ((len = inStream.read(buffer, 0, buffer.length)) != -1) {
                    fileOut.write(buffer, 0, buffer.length);
                    fileOut.flush();
                }
                callBack.success(file);
            }
        });

    }

}