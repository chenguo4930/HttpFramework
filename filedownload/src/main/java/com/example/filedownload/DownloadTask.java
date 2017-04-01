/*
 * DownloadTash    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload;

import com.example.filedownload.http.DownloadCallBack;

/**
 * 线程下载队列
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public class DownloadTask {

    private String mUrl;

    private DownloadCallBack mCallback;

    public DownloadTask(String mUrl, DownloadCallBack mCallback) {
        this.mUrl = mUrl;
        this.mCallback = mCallback;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public DownloadCallBack getCallback() {
        return mCallback;
    }

    public void setCallback(DownloadCallBack mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadTask that = (DownloadTask) o;

        if (mUrl != null ? !mUrl.equals(that.mUrl) : that.mUrl != null) return false;
        return mCallback != null ? mCallback.equals(that.mCallback) : that.mCallback == null;

    }

    @Override
    public int hashCode() {
        int result = mUrl != null ? mUrl.hashCode() : 0;
        result = 31 * result + (mCallback != null ? mCallback.hashCode() : 0);
        return result;
    }
}