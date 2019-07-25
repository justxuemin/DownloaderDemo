package com.coocoo.downloaderdemo.downloader.model.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "downloder.db";
    private static final int DB_VERSION = 1;

    public BaseDBHelper(Context context) {
        this(context, null);
    }

    public BaseDBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        this(context, factory, null);
    }

    public BaseDBHelper(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onDBCrearte(sqLiteDatabase);
    }

    protected abstract void onDBCrearte(SQLiteDatabase sqLiteDatabase);

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
