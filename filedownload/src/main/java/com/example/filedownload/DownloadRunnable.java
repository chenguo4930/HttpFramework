/*
 * DownloadRunnable    2017-03-31
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload;

import com.example.filedownload.db.DownloadEntity;
import com.example.filedownload.db.DownloadHelper;
import com.example.filedownload.file.FileStorageManager;
import com.example.filedownload.http.DownloadCallBack;
import com.example.filedownload.http.HttpManager;
import com.example.filedownload.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-31
 */
public class DownloadRunnable implements Runnable {

    private long mStart;

    private long mEnd;

    private String mUrl;

    private DownloadCallBack mCallBack;

    private DownloadEntity mEntity;

    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallBack mCallBack, DownloadEntity mEntity) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mCallBack = mCallBack;
        this.mEntity = mEntity;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority( android.os.Process.THREAD_PRIORITY_BACKGROUND);

        Response response = HttpManager.getInstance().syncRequestRange(mUrl, mStart, mEnd);
        if (response == null && mCallBack != null) {
            mCallBack.fail(HttpManager.NETWORK_ERROR_CODE, "网络出现了错误");
            return;
        }
        File file = FileStorageManager.getInstance().getFileByName(mUrl);

        long progress = 0;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(mStart);
            byte[] buffer = new byte[1024 * 500];
            int len;
            InputStream inStream = response.body().byteStream();
            while ((len = inStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer, 0, buffer.length);
                progress += len;
                mEntity.setProgress_position(progress);
                Logger.debug("nate", "progress  ----->" + progress);
            }
            randomAccessFile.close();
            mCallBack.success(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DownloadHelper.getInstance().insert(mEntity);
        }
    }
}