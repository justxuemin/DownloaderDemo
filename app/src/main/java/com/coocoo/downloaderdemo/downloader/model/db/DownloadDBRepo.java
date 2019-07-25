package com.coocoo.downloaderdemo.downloader.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coocoo.downloaderdemo.downloader.model.Repo;
import com.coocoo.downloaderdemo.downloader.model.bean.Data;

import java.util.ArrayList;
import java.util.List;

public class DownloadDBRepo extends BaseDBHelper implements Repo, DownloadDBConstant {


    private final SQLiteDatabase mDatabase;

    public DownloadDBRepo(Context context) {
        super(context);
        mDatabase = getWritableDatabase();
    }

    @Override
    protected void onDBCrearte(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_DOWNLOAD_NAME + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DOWNLOAD_URL + " TEXT," +
                LOCAL_URL + " TEXT," +
                NAME + " TEXT," +
                DATA_STATE + " INTEGER," +
                TIME_STAMP + " LONG," +  MD5 + " TEXT, " + OFFSET + " LONG" +
                ")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public boolean insert(Data data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DOWNLOAD_URL, data.getDownloadUrl());
        contentValues.put(LOCAL_URL, data.getLocalUrl());
        contentValues.put(NAME, data.getName());
        contentValues.put(MD5, data.getMd5());
        contentValues.put(TIME_STAMP, data.getTimeStamp());
        contentValues.put(DATA_STATE, data.getRawState());
        contentValues.put(OFFSET, data.getOffset());
        mDatabase.insert(TABLE_DOWNLOAD_NAME, null, contentValues);
        return true;
    }

    @Override
    public boolean delete(String url) {
        mDatabase.delete(TABLE_DOWNLOAD_NAME, DOWNLOAD_URL + " = ?", new String[]{url});
        return true;
    }

    @Override
    public boolean modify(Data data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOCAL_URL, data.getLocalUrl());
        contentValues.put(NAME, data.getName());
        contentValues.put(MD5, data.getMd5());
        contentValues.put(TIME_STAMP, data.getTimeStamp());
        contentValues.put(DATA_STATE, data.getRawState());
        contentValues.put(OFFSET, data.getOffset());
        mDatabase.update(TABLE_DOWNLOAD_NAME, contentValues, DOWNLOAD_URL + " = ? " , new String[]{data.getDownloadUrl()});
        return true;
    }

    @Override
    public Data query(String url) {
        Cursor cursor = mDatabase.query(TABLE_DOWNLOAD_NAME, TABLE_COLUMNS, DOWNLOAD_URL + " = ?", new String[]{url}, null, null, null);
        Data data = null;
        while (cursor != null && cursor.moveToNext()) {
            data = wrapData(cursor);
        }
        return data;
    }

    private Data wrapData(Cursor cursor) {
        Data data;
        data = new Data();
        data.setDownloadUrl(cursor.getString(cursor.getColumnIndex(DOWNLOAD_URL)));
        data.setLocalUrl(cursor.getString(cursor.getColumnIndex(LOCAL_URL)));
        data.setMd5(cursor.getString(cursor.getColumnIndex(MD5)));
        data.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        data.setOffset(cursor.getLong(cursor.getColumnIndex(OFFSET)));
        data.setTimeStamp(cursor.getLong(cursor.getColumnIndex(TIME_STAMP)));
        data.setRawState(cursor.getInt(cursor.getColumnIndex(DATA_STATE)));
        return data;
    }

    @Override
    public List<Data> queryAll() {
        String sql = "SELECT * FROM " + TABLE_DOWNLOAD_NAME;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        List<Data> dataList = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            dataList.add(wrapData(cursor));
        }
        return dataList;
    }
}
