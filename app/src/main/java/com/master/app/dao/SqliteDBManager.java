package com.master.app.nfct.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By Master
 * On 2019/1/2 10:59
 */

public class SqliteDBManager {

    private static SqliteDBManager iSqliteDBManager;
    private SQLiteDBHelper iSQLiteDBHelper;
    private SQLiteDatabase iSQLiteDatabase;


    private SqliteDBManager(Context context) {
        this.iSQLiteDBHelper = new SQLiteDBHelper(context);
        this.iSQLiteDatabase = iSQLiteDBHelper.getWritableDatabase();
    }

    public static synchronized SqliteDBManager getInstances(Context context) {
        if (iSqliteDBManager != null) {
            return iSqliteDBManager;
        }
        if (context != null) {
            iSqliteDBManager = new SqliteDBManager(context);
        }
        return iSqliteDBManager;
    }

    public void insertOrUpdate(SQLiteBean sqLiteBean) {
        synchronized (SqliteDBManager.class) {
            Cursor cursor = iSQLiteDatabase.query(iSQLiteDBHelper.getTableName(),
                    new String[]{"id"},
                    "id =?",
                    new String[]{String.valueOf(sqLiteBean.getId())},
                    null, null, null);

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", sqLiteBean.getId());
            contentValues.put("data", sqLiteBean.getData());

            if (cursor != null && cursor.getCount() > 0) {
                // 更新数据
                iSQLiteDatabase.update(iSQLiteDBHelper.getTableName(), contentValues, "id =?", new String[]{String.valueOf(sqLiteBean.getId())});
            } else {
                // 插入数据
                iSQLiteDatabase.insert(iSQLiteDBHelper.getTableName(), null, contentValues);
            }
        }
    }

    public List<SQLiteBean> queryData(SQLiteBean sqLiteBean) {
        synchronized (SqliteDBManager.class) {
            List<SQLiteBean> sqLiteBeans = new ArrayList<>();
            Cursor cursor = iSQLiteDatabase.query(iSQLiteDBHelper.getTableName(),
                    new String[]{"id"},
                    "id =?",
                    new String[]{String.valueOf(sqLiteBean.getId())},
                    null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SQLiteBean sqLiteBean1 = new SQLiteBean();
                    sqLiteBean1.setId(cursor.getInt(0));
                    sqLiteBean1.setData(cursor.getString(1));
                    sqLiteBeans.add(sqLiteBean1);
                }
            }
            return sqLiteBeans;
        }

    }


    public List<SQLiteBean> queryAllData() {
        synchronized (SqliteDBManager.class) {
            List<SQLiteBean> sqLiteBeans = new ArrayList<>();
            Cursor cursor = iSQLiteDatabase.query(iSQLiteDBHelper.getTableName(), null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SQLiteBean sqLiteBean = new SQLiteBean();
                    sqLiteBean.setId(cursor.getInt(0));
                    sqLiteBean.setData(cursor.getString(1));
                    sqLiteBeans.add(sqLiteBean);
                }
            }
            return sqLiteBeans;
        }
    }

    public void delData(SQLiteBean sqLiteBean) {
        synchronized (SqliteDBManager.class) {
            iSQLiteDatabase.delete(iSQLiteDBHelper.getTableName(), "id =?", new String[]{String.valueOf(sqLiteBean.getId())});
        }
    }

    public void clearData() {
        synchronized (SqliteDBManager.class) {
            iSQLiteDatabase.delete(iSQLiteDBHelper.getTableName(), null, null);
        }
    }

}
