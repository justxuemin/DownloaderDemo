package com.coocoo.downloaderdemo.downloader;

import com.coocoo.downloaderdemo.downloader.model.ProcessCall;

import okhttp3.Call;

public interface IDownloadTask {
    void cancel();
    void pause();
    void resume();
    ProcessCall getProcessCall();
    Call getCall();
    String getDownloadUrl();
}
