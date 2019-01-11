package com.master.app.nfct.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Create By Master
 * On 2019/1/2 11:00
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "master.db";// 数据库名
    private static final int DATABASE_VERSION = 0x01;// 版本号
    private static final String DATABASE_TABLE_NAME = "memo";// 表名
    private static final String DATABASE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_NAME + " (id integer primary key autoincrement, data varchar)";

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /**
     * 获取表名
     *
     * @return
     */
    public String getTableName() {
        return DATABASE_TABLE_NAME;
    }

}
