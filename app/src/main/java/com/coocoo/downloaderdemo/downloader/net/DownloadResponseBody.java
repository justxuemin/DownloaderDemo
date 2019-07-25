package com.coocoo.downloaderdemo.downloader.net;

import android.util.Log;

import com.coocoo.downloaderdemo.downloader.Downloader;
import com.coocoo.downloaderdemo.downloader.IDownloadCallback;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

public class DownloadResponseBody extends ResponseBody {

    private final Response mResponse;
    private final IDownloadCallback mCallback;
    private final long mOffset;
    private final Downloader.UIHandler mUIHandler;

    DownloadResponseBody(Response response, long offset, Downloader.UIHandler uiHandler, IDownloadCallback callback) {
        mResponse = response;
        mCallback = callback;
        mOffset = offset;
        mUIHandler = uiHandler;
    }

    @Override
    public MediaType contentType() {
        return mResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return mResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(mResponse.body().source()) {
            private long totalReadBytes = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long readBytes = super.read(sink, byteCount);
                totalReadBytes += (readBytes == -1 ? 0 : readBytes);
                int process = (int) ((totalReadBytes + mOffset) / ((float) mResponse.body().contentLength() + mOffset)* 100);
                mUIHandler.sendProcessMsg(process, mCallback);
                return readBytes;
            }
        });
    }
}
