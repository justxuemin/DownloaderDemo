package com.coocoo.downloaderdemo.downloader.model.bean;

public class Data {
    private String downloadUrl;
    private String localUrl;
    private String name;
    private DataState state = DataState.PART;
    private long timeStamp;
    private String md5;
    private long offset;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public String getName() {
        return name;
    }

    public DataState getState() {
        return state;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getMd5() {
        return md5;
    }

    public long getOffset() {
        return offset;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRawState(int stateInt) {
        state = DataState.valueOf(stateInt);
    }

    public void setState(DataState dataState) {
        state = dataState;
    }

    public int getRawState() {
        return state.getValue();
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "Data{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", timeStamp=" + timeStamp +
                ", md5='" + md5 + '\'' +
                ", offset=" + offset +
                '}';
    }
}
