package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sy on 2017/6/19.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="NUO.db";
    private static final String TABLE_NAME="nuo";
    private static final int VERSION=1;
    private static final String CREATE_TABLE="create table "+TABLE_NAME+" (a int,b int,c int,d int)";
    private MySQLiteHelper helper;
    public MySQLiteHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    public String getTableName(){
        return TABLE_NAME;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
