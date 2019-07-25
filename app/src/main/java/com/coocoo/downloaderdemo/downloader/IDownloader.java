package com.coocoo.downloaderdemo.downloader;

public interface IDownloader {

    /**
     * 下载开始
     *
     * @param url 下载地址
     * @param callback 结果回调
     * @return 下载任务对象
     */
    IDownloadTask download(String url, IDownloadCallback callback);
}
