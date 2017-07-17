package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.example.enity.DatabaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/19.
 */

public class ControlMyDatabase {
    private MySQLiteHelper helper;
    private ControlMyDatabase myDatabase;

    public ControlMyDatabase(Context context) {
        if (helper==null)
            helper=new MySQLiteHelper(context);
    }

    public ControlMyDatabase getMydatabase(Context context){
        if (myDatabase==null){
            myDatabase=new ControlMyDatabase(context);
        }
        return myDatabase;
    }
    //增
    public void insertData(){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        for (int i=0;i<6;i++){
            values.put("a",i);
            values.put("b",i);
            values.put("c",i);
            values.put("d",i);
            db.insert(helper.getTableName(),null,values);
        }
        db.close();
    }

    //删
    public void delData(DatabaseInfo info){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete(helper.getTableName(),"a=?",new String[]{info.getA()+""});
//        String str="delete from nuo where a="+info.getA();
//        db.execSQL(str);
        db.close();
    }
    //改
    public void changeData(DatabaseInfo info){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("c",-1);
        db.update(helper.getTableName(),values,"c=?",new String[]{info.getC()+""});
        db.close();
//        String str
    }
    //查
    public List<DatabaseInfo> getDataList(){
        List<DatabaseInfo> list=new ArrayList<>();
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query(helper.getTableName(),null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                int a=cursor.getInt(cursor.getColumnIndex("a"));
                int b=cursor.getInt(cursor.getColumnIndex("b"));
                int c=cursor.getInt(cursor.getColumnIndex("c"));
                int d=cursor.getInt(cursor.getColumnIndex("d"));
                DatabaseInfo info=new DatabaseInfo(a,b,c,d);
                list.add(info);
            }while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }
}
