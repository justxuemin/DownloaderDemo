package com.coocoo.downloaderdemo.downloader.model.sp;

import com.coocoo.downloaderdemo.downloader.model.Repo;
import com.coocoo.downloaderdemo.downloader.model.bean.Data;

import java.util.List;

public class SPRepo implements Repo {

    @Override
    public boolean insert(Data data) {
        return false;
    }

    @Override
    public boolean delete(String url) {
        return false;
    }

    @Override
    public boolean modify(Data data) {
        return false;
    }

    @Override
    public Data query(String url) {

        return null;
    }

    @Override
    public List<Data> queryAll() {
        return null;
    }
}
