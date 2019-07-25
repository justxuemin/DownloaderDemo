package com.coocoo.downloaderdemo.downloader;

import com.coocoo.downloaderdemo.downloader.model.bean.Data;

public interface IDownloadCallback {
    void onBefore();
    void onProcess(int process);
    void onComplete(Data data);
    void onFailed(String error);
    void onCanceled();
    void onPause();
    void onResume();
}
