package com.coocoo.downloaderdemo.downloader.model.db;

public interface DownloadDBConstant {
    String TABLE_DOWNLOAD_NAME = "download_data";
    String ID = "data_id";
    String DOWNLOAD_URL = "download_url";
    String LOCAL_URL = "local_url";
    String NAME = "name";
    String DATA_STATE = "data_state";
    String TIME_STAMP = "time_stamp";
    String MD5 = "md5";
    String OFFSET = "offset";

    String[] TABLE_COLUMNS = {
            DOWNLOAD_URL,
            LOCAL_URL,
            NAME,
            DATA_STATE,
            TIME_STAMP,
            MD5,
            OFFSET
    };
}
