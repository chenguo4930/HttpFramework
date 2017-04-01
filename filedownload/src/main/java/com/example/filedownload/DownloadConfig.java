/*
 * DownloadConfig    2017-04-01
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.filedownload;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public class DownloadConfig {

    private int coreThreadSize;
    private int maxThreadSize;
    private int localProgressThreadSize;

    public int getCoreThreadSize() {
        return coreThreadSize;
    }

    public int getMaxThreadSize() {
        return maxThreadSize;
    }

    public int getLocalProgressThreadSize() {
        return localProgressThreadSize;
    }

    private DownloadConfig(Builder builder) {
        coreThreadSize = builder.coreThreadSize == 0 ? DownloadManager.MAX_THREAD : builder.coreThreadSize;
        maxThreadSize = builder.maxThreadSize == 0 ? DownloadManager.MAX_THREAD : builder.maxThreadSize;
        localProgressThreadSize = builder.localProgressThreadSize == 0 ? DownloadManager.LOCAL_PROGRESS_SIZE : builder.localProgressThreadSize;
    }

    public static class Builder {
        private int coreThreadSize;
        private int maxThreadSize;
        private int localProgressThreadSize;

        public Builder setCoreThreadSize(int coreThreadSize) {
            this.coreThreadSize = coreThreadSize;
            return this;
        }

        public Builder setMaxThreadSize(int maxThreadSize) {
            this.maxThreadSize = maxThreadSize;
            return this;
        }

        public Builder setLocalProgressThreadSize(int localProgressThreadSize) {
            this.localProgressThreadSize = localProgressThreadSize;
            return this;
        }

        public DownloadConfig builder() {
            return new DownloadConfig(this);
        }
    }
}