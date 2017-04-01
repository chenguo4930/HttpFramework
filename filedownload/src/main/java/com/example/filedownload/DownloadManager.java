package com.example.filedownload;

import android.support.annotation.NonNull;

import com.example.filedownload.db.DownloadEntity;
import com.example.filedownload.db.DownloadHelper;
import com.example.filedownload.file.FileStorageManager;
import com.example.filedownload.http.DownloadCallBack;
import com.example.filedownload.http.HttpManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 下载管理类
 * <p>
 * Created by cheng on 2017/3/31.
 */

public class DownloadManager {
    private List<DownloadEntity> mCache;

    public static final int MAX_THREAD = 2;
    public static final int LOCAL_PROGRESS_SIZE = 1;
    private long mLength;

    private static ExecutorService sLocalProgressPool;

    private static DownloadManager ourInstance = new DownloadManager();

    //下载队列
    private HashSet<DownloadTask> mHashSet = new HashSet<>();

    private static ThreadPoolExecutor sThreadPool;

    public static DownloadManager getInstance() {
        return ourInstance;
    }

    private DownloadManager() {
    }

    public void init(DownloadConfig config) {
        sThreadPool = new ThreadPoolExecutor(config.getCoreThreadSize(),
                config.getMaxThreadSize(),
                60,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new ThreadFactory() {

                    private AtomicInteger mInteger = new AtomicInteger(1);

                    @Override
                    public Thread newThread(@NonNull Runnable r) {
                        Thread thread = new Thread(r, "download thread #" + mInteger.getAndIncrement());
                        return thread;
                    }
                });

        sLocalProgressPool = Executors.newFixedThreadPool(config.getLocalProgressThreadSize());
    }

    private void finish(DownloadTask task) {
        mHashSet.remove(task);
    }

    public void download(final String url, final DownloadCallBack callBack) {

        final DownloadTask task = new DownloadTask(url, callBack);

        if (mHashSet.contains(task)) {
            callBack.fail(HttpManager.TASK_RUNNING_ERROR_CODE, "任务已经执行了");
            return;
        }
        mHashSet.add(task);

        mCache = DownloadHelper.getInstance().getAll(url);
        if (mCache == null || mCache.size() == 0) {
            HttpManager.getInstance().asyncRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    finish(task);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful() && callBack != null) {
                        callBack.fail(HttpManager.NETWORK_ERROR_CODE, "网络出问题了");
                        return;
                    }
                    mLength = response.body().contentLength();
                    if (mLength == -1) {
                        callBack.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE, "内容为空");
                        return;
                    }
                    processDownload(url, mLength, callBack, mCache);
                    finish(task);
                }
            });
        } else {
            //处理已经下载过的数据
            for (int i = 0; i < mCache.size(); i++) {
                DownloadEntity entity = mCache.get(i);
                if (i == mCache.size() - 1) {
                    mLength = entity.getEnd_position() + 1;
                }
                long startSize = entity.getStart_position() + entity.getProgress_position();
                long endSize = entity.getEnd_position();
                sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callBack, entity));
            }
        }

        //统计下载进度
        sLocalProgressPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        File file = FileStorageManager.getInstance().getFileByName(url);
                        long fileSize = file.length();
                        int progress = (int) (fileSize * 100.0 / mLength);
                        if (progress >= 100) {
                            //保证100的时候能够同步到进度
                            callBack.progress(progress);
                            return;
                        }
                        callBack.progress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 多线程下载
     */
    private void processDownload(String url, long length, DownloadCallBack callBack, List<DownloadEntity> cache) {

        //100 2 50 0-49 50-99
        long threadDownloadSize = length / MAX_THREAD;
        if (cache == null && cache.size() == 0) {
            mCache = new ArrayList<>();
        }
        for (int i = 0; i < MAX_THREAD; i++) {
            DownloadEntity entity = new DownloadEntity();
            long startSize = i * threadDownloadSize;
            long endSize = 0;
            if (i == MAX_THREAD - 1) {
                endSize = length - 1;
            } else {
                endSize = (i + 1) * threadDownloadSize - 1;
            }

            entity.setDownload_url(url);
            entity.setStart_position(startSize);
            entity.setEnd_position(endSize);
            //线程id从1开始
            entity.setThread_id(i + 1);

            sThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callBack, entity));
        }
    }
}
