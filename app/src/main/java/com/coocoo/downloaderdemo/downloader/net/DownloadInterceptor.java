package com.coocoo.downloaderdemo.downloader.net;

import com.coocoo.downloaderdemo.downloader.Downloader;
import com.coocoo.downloaderdemo.downloader.IDownloadCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadInterceptor implements Interceptor {

    private final Downloader.UIHandler mUIHandler;
    private Map<String, IDownloadCallback> mCallbacks;
    private Map<String, Long> mOffsets;

    public DownloadInterceptor(Downloader.UIHandler uiHandler) {
        mCallbacks = new HashMap<>();
        mOffsets = new HashMap<>();
        mUIHandler = uiHandler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        Request request = chain.request();
        return response.newBuilder().body(new DownloadResponseBody(response, getOffset(request.url()), mUIHandler, getCallback(request.url()))).build();
    }

    public void addCallback(String url, IDownloadCallback callback) {
        mCallbacks.put(url, callback);
    }

    public void addOffset(String url, long offset) {
        mOffsets.put(url, offset);
    }

    private IDownloadCallback getCallback(HttpUrl url) {
        if (url == null) {
            return null;
        }
        return mCallbacks.get(url.toString());
    }

    private long getOffset(HttpUrl url) {
        if (url == null) {
            return 0;
        }
        return mOffsets.get(url.toString());
    }
}
