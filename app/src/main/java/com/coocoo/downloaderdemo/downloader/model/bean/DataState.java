package com.coocoo.downloaderdemo.downloader.model.bean;

/**
 * 数据状态
 */
public enum DataState {
    /**
     * 完整数据
     */
    COMPLETE(0),
    /**
     * 未下载完全的部分数据
     */
    PART(1),
    /**
     * 被损坏数据
     */
    INTERUPT(2);

    private int value = 0;

    DataState(int value) {
        this.value = value;
    }

    public static DataState valueOf(int value) {
        switch (value) {
            case 0:
                return COMPLETE;
            case 1:
                return PART;
            case 2:
                return INTERUPT;
            default:
                return null;
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "DataState{" +
                "value=" + value +
                '}';
    }}
