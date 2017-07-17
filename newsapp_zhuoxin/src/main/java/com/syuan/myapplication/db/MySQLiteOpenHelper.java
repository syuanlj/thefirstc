package com.syuan.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sy on 2017/6/9.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static MySQLiteOpenHelper mySQLiteOpenHelper;
    private static final String DB_NAME = "news.db";
    private static final String TABLE_NAME = "news";
    private static final int VERSION = 2;
    private static final String CREATE_TABLE = "create table "+TABLE_NAME+" (" +
            "id integer primary key autoincrement," +
            "nid integer," +
            "stamp text," +
            "icon text," +
            "title text," +
            "summary text," +
            "link text)";

    public static MySQLiteOpenHelper getMySQLiteOpenHelper(Context context){
        if (mySQLiteOpenHelper==null){
            mySQLiteOpenHelper=new MySQLiteOpenHelper(context);
        }
        return mySQLiteOpenHelper;
    }
    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static String getTableName(){
        return TABLE_NAME;
    }
}
