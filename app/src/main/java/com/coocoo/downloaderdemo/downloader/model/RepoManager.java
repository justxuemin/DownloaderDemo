package com.coocoo.downloaderdemo.downloader.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.coocoo.downloaderdemo.downloader.Downloader;
import com.coocoo.downloaderdemo.downloader.IDownloadCallback;
import com.coocoo.downloaderdemo.downloader.model.bean.Data;
import com.coocoo.downloaderdemo.downloader.model.bean.DataState;
import com.coocoo.downloaderdemo.downloader.model.db.DownloadDBRepo;
import com.coocoo.downloaderdemo.downloader.utils.MD5Util;

import java.io.File;

import okhttp3.Response;

public class RepoManager {

    private final String mRootPath;
    private final DownloadDBRepo mDBRepo;
    private final ProcessDispatcher mDispatcher;

    public RepoManager(Context context, String rootPath) {
        mDBRepo = new DownloadDBRepo(context);
        mRootPath = rootPath;
        File file = new File(mRootPath);
        if (!file.exists()) {
            file.mkdir();
        }
        mDispatcher = new ProcessDispatcher();
    }

    public Data getData(String url) {
        Data data = mDBRepo.query(url);
        if (checkFileIsValid(data)) {
            return data;
        } else {
            if (data != null) {
                mDBRepo.delete(data.getDownloadUrl());
                deleteFile(data.getLocalUrl());
            }
            data = new Data();
            data.setDownloadUrl(url);
            String fileSuffix = getFileSuffix(url);
            String fileMD5Name = MD5Util.getMD5(url);
            data.setLocalUrl(mRootPath + fileMD5Name + "." + fileSuffix);
            Log.e("xuemin", "download_local : " + mRootPath + fileMD5Name + "." + fileSuffix);
            mDBRepo.insert(data);
            return data;
        }
    }

    private void deleteFile(String localUrl) {
        if (TextUtils.isEmpty(localUrl)) {
            return;
        }
        File file = new File(localUrl);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * TODO : 没有校验文件大小，文件MD5等信息
     * @param data
     * @return
     */
    private boolean checkFileIsValid(Data data) {
        if (data == null || data.getState() == DataState.INTERUPT) {
            return false;
        }
        File file = new File(data.getLocalUrl());
        if (!file.exists()) {
            return false;
        }
        return true;
    }



    public ProcessCall enqueue(Response response, Data data, Downloader.UIHandler handler, IDownloadCallback callback) {
        ProcessCall call = new ProcessCall(mDispatcher, mDBRepo, response, data, handler, callback);
        call.enqueue();
        return call;
    }

    private String getFileSuffix(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String[] split = url.split("\\.");
        if (split.length > 1) {
            return split[split.length -1];
        }
        return "";
    }
}
