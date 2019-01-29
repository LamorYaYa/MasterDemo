package com.master.app.dao;

/**
 * Create By Master
 * On 2019/1/2 14:22
 */
public class SQLiteBean {
    private int id;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SQLiteBean{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }
}
