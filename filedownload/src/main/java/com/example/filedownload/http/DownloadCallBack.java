/*
 * DownloadCallBack    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload.http;

import java.io.File;

/**
 * 网络请求的监听接口
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public interface DownloadCallBack {

    void success(File file);

    void fail(int errorCode, String errorMessage);

    void progress(int progress);
}