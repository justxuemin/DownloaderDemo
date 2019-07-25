package com.coocoo.downloaderdemo.downloader.model;

import com.coocoo.downloaderdemo.downloader.model.bean.Data;

import java.util.List;

public interface Repo {
    boolean insert(Data data);
    boolean delete(String url);
    boolean modify(Data data);
    Data query(String url);
    List<Data> queryAll();
}
