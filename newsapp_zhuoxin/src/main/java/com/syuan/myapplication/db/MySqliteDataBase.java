package com.syuan.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.syuan.myapplication.entity.NewsListInfo;

import java.util.ArrayList;
import java.util.List;

import static com.syuan.myapplication.db.MySQLiteOpenHelper.getMySQLiteOpenHelper;

/**
 * Created by sy on 2017/6/12.
 */

public class MySqliteDataBase {
    private static MySQLiteOpenHelper mySQLiteOpenHelper;
    private static MySqliteDataBase mySqliteDataBase;
    private static Context context;

    public MySqliteDataBase(Context context) {
        this.context = context;
        mySQLiteOpenHelper = getMySQLiteOpenHelper(context);
    }

    //向数据库存数据
    public static void insertNews(NewsListInfo info) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        //判断这条新闻是否已经被收藏，是则返回，否则继续
        if (isSaved(info)) {
            Toast.makeText(context, "已经收藏过了", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
//        String str="select * from news";//查询语句
//        Cursor cursor=db.rawQuery(str,null);//执行查询语句
        ContentValues values = new ContentValues();//数据值
        values.put("nid", info.getNid());
        values.put("stamp", info.getStamp());
        values.put("icon", info.getIcon());
        values.put("title", info.getTitle());
        values.put("summary", info.getSummary());
        values.put("link", info.getLink());
        db.insert(MySQLiteOpenHelper.getTableName(), null, values);//向数据库中插入一条数据
    }

    /**
     * 判断新闻是否已经被收藏
     *
     * @param info 该新闻信息
     * @return true 已被收藏，false 没被收藏
     */
    private static boolean isSaved(NewsListInfo info) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        String str = "select * from " + MySQLiteOpenHelper.getTableName() + " where nid=" + info.getNid();//sql语句
        Cursor c = db.rawQuery(str, null);
        if (c.moveToFirst()) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }

    /**
     * 获得收藏
     *
     * @return 返回收藏内容
     */
    public static List<NewsListInfo> getNewsList() {
        List<NewsListInfo> list = new ArrayList<>();

        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        String str = "select * from news";//查询语句
        Cursor cursor = db.rawQuery(str, null);
        if (cursor.moveToFirst()) {
            do {
                NewsListInfo info = new NewsListInfo();

                info.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                info.setLink(cursor.getString(cursor.getColumnIndex("link")));
                info.setStamp(cursor.getString(cursor.getColumnIndex("stamp")));
                info.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
                info.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                info.setNid(cursor.getInt(cursor.getColumnIndex("nid")));

                list.add(info);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return list;
    }

    public static void delItem(NewsListInfo info) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        String str = "delete from news where nid=" + info.getNid();
        db.execSQL(str);
    }

    public static void delAll() {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        String str = "delete from news ";
        db.execSQL(str);
    }
}
