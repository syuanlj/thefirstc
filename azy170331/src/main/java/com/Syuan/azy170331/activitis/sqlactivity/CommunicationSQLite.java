package com.Syuan.azy170331.activitis.sqlactivity;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.Syuan.azy170331.activitis.entity.CommunicationInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/5/3.
 */

public class CommunicationSQLite {
    public static List<CommunicationInfo> getData(Context context) {
        List<CommunicationInfo> list = new ArrayList<>();//实例化一个集合
        //输入输出流将assets下的commonnum.db写入到指定的位置
        CopySqlDB.copyDb(context,"commonnum.db");
        String path = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/commonnum.db";
        //打开指定路径下的数据库
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        //查询数据库，得到指定数据
        Cursor cursor = db.query("classlist", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CommunicationInfo info = new CommunicationInfo();
                info.setid(cursor.getInt(cursor.getColumnIndex("idx")));
                info.setname(cursor.getString(cursor.getColumnIndex("name")));
                list.add(info);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }
}
