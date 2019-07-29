package com.coocoo.downloaderdemo.downloader.net;

import android.util.Log;

import com.coocoo.downloaderdemo.downloader.DownloadTask;
import com.coocoo.downloaderdemo.downloader.Downloader;
import com.coocoo.downloaderdemo.downloader.IDownloadCallback;
import com.coocoo.downloaderdemo.downloader.IDownloadTask;
import com.coocoo.downloaderdemo.downloader.model.ProcessCall;
import com.coocoo.downloaderdemo.downloader.model.RepoManager;
import com.coocoo.downloaderdemo.downloader.model.bean.Data;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager implements INetworkManager {

    private final OkHttpClient mClient;
    private final DownloadInterceptor mInterceptor;
    private final Downloader.UIHandler mUIHandler;
    private final RepoManager mRepoManager;

    public NetworkManager(Downloader.UIHandler uiHandler, RepoManager repoManager) {
        mUIHandler = uiHandler;
        mRepoManager = repoManager;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES));
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "Download dispatcher");
                thread.setDaemon(false);
                return thread;
            }
        });
        builder.dispatcher(new Dispatcher(executor));
        mInterceptor = new DownloadInterceptor(uiHandler);
        builder.addNetworkInterceptor(mInterceptor);
        mClient = builder.build();
    }

    public IDownloadTask enqueue(boolean isResume, final Data data, final IDownloadCallback callback) {
        mUIHandler.sendBeforeMsg(callback);
        Request.Builder builder = new Request.Builder();
        String url = data.getDownloadUrl();
        long offset = data.getOffset();
        final Request request = builder.url(url).header("RANGE", "bytes=" + offset + "-").build();
        Call call = mClient.newCall(request);
        mInterceptor.addCallback(url, callback);
        mInterceptor.addOffset(url, offset);
        final DownloadTask downloadTask = new DownloadTask(this, data.getDownloadUrl(), callback);
        if (isResume) {
            mUIHandler.sendResumeMsg(callback);
        }
        downloadTask.setCall(call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mUIHandler.sendFailedMsg(e.getMessage(), callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ProcessCall processCall = handleResponse(response, data, callback);
                Log.e("xuemin", "设置ProcessCall");
                downloadTask.setProcessCall(processCall);
            }
        });
        return downloadTask;
    }

    private ProcessCall handleResponse(Response response, Data data, IDownloadCallback callback) {
        // TODO: 需要任务队列与下载任务队列匹配
        return mRepoManager.enqueue(response, data, mUIHandler, callback);
    }

    public RepoManager getRepoManager() {
        return mRepoManager;
    }
}
