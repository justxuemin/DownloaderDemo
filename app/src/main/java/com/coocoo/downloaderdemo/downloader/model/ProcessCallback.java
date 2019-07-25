package com.coocoo.downloaderdemo.downloader.model;

public interface ProcessCallback {
    void onProcess();
    void onCancel();
    void onFailed();
    void onComplete();
}
