package com.Syuan.azy170331.activitis.sqlactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sy on 2017/5/3.
 */

public class MySQLiteHelp extends SQLiteOpenHelper {
    private static final String DB_NAME = "cup.db";
    private static final String TABLE_NAME = "cup";
    private static final int VERSION = 2;
    private static final String CREATE_TABLE = "create table "+TABLE_NAME+" (name varchar,type varchar,price int,number int)";

    public MySQLiteHelp(Context context) {
        //上下文，数据库名称，游标工厂（传空即可），数据库版本号
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);//创建数据库
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);//删除旧表
        onCreate(db);
    }
}
