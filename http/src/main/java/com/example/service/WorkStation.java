/*
 * WorkStation    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.service;

import android.support.annotation.NonNull;

import com.example.HttpRequestProvider;
import com.example.http.HttpRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程的管理，队列的控制,工作栈
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class WorkStation {
    //最大的正在运行的线程数量
    private static final int MAX_REQUEST_SIZE = 60;

    private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        //线程的索引，ID
        private AtomicInteger index = new AtomicInteger();

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("http thread name is " + index.getAndIncrement());
            return thread;
        }
    });

    //双端队列 Deque
    private Deque<HttpRequestEntity> mRunning = new ArrayDeque<>();
    private Deque<HttpRequestEntity> mCache = new ArrayDeque<>();

    private HttpRequestProvider mRequestProvider;

    public WorkStation() {
        mRequestProvider = new HttpRequestProvider();
    }


    public void add(HttpRequestEntity requestEntity) {

        if (mRunning.size() > MAX_REQUEST_SIZE) {
            mCache.add(requestEntity);
        } else {
            doHttpRequest(requestEntity);
        }
    }

    public void doHttpRequest(HttpRequestEntity requestEntity) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = mRequestProvider.getHttpRequest(URI.create(requestEntity.getUrl()), requestEntity.getMethod());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sThreadPool.execute(new HttpRunnable(httpRequest, requestEntity, this));
    }

    public void finish(HttpRequestEntity request) {
        mRunning.remove(request);
        if (mRunning.size() > MAX_REQUEST_SIZE) {
            return;
        }
        if (mCache.size() == 0) {
            return;
        }
        Iterator<HttpRequestEntity> iterator = mCache.iterator();
        while (iterator.hasNext()) {
            HttpRequestEntity next = iterator.next();
            mRunning.add(next);
            iterator.remove();
            doHttpRequest(request);
        }

    }
}