/*
 * MyApplication    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.cheng.downloadexample;

import android.app.Application;

import com.example.filedownload.DownloadConfig;
import com.example.filedownload.DownloadManager;
import com.example.filedownload.db.DownloadHelper;
import com.example.filedownload.file.FileStorageManager;
import com.example.filedownload.http.HttpManager;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
        DownloadHelper.getInstance().init(this);

        DownloadConfig config = new DownloadConfig.Builder()
                .setCoreThreadSize(2)
                .setMaxThreadSize(4)
                .setLocalProgressThreadSize(1)
                .builder();
        DownloadManager.getInstance().init(config);

    }
}