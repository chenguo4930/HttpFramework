package com.example;


import com.example.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-04-01
 */
public abstract class AbstractHttpResponse implements HttpResponse {

    private static final String GZIP = "gzip";

    private InputStream mGzipInputStream;

    @Override
    public void close()  {
        if (mGzipInputStream != null) {
            try {
                mGzipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        closeInternal();

    }

    @Override
    public InputStream getBody() throws IOException {

        InputStream body = getBodyInternal();
        if (isGzip()) {
            return getBodyGzip(body);
        }

        return body;
    }

    protected abstract InputStream getBodyInternal() throws IOException;

    protected abstract void closeInternal();

    private InputStream getBodyGzip(InputStream body) throws IOException {

        if (this.mGzipInputStream == null) {
            this.mGzipInputStream = new GZIPInputStream(body);
        }
        return mGzipInputStream;

    }

    private boolean isGzip() {
        String contentEncoding = getHeaders().getContentEncoding();

        if (GZIP.equals(contentEncoding)) {
            return true;
        }

        return false;
    }

}
