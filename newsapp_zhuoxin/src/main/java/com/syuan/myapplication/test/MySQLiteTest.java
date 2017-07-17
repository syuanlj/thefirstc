package com.syuan.myapplication.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sy on 2017/6/9.
 */

public class MySQLiteTest extends SQLiteOpenHelper {
    private static MySQLiteTest mySQLiteTest;
    private static final String DB_NAME = "ABC.db";
    private static final String TABLE_NAME = "abc";
    private static final int VERSION = 3;
    private static final String CREATE_TABLE = "create table "+TABLE_NAME+" (" +
            "a integer," +
            "b boolean," +
            "c text)" ;
//增删改查
    public static MySQLiteTest getMySQLiteTest(Context context){
        if (mySQLiteTest==null){
            mySQLiteTest=new MySQLiteTest(context);
        }
        return mySQLiteTest;
    }
    public MySQLiteTest(Context context) {
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
