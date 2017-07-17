package com.Syuan.azy170331.activitis.sqlactivity;


import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.activitis.GarbageCollectionActivity;
import com.Syuan.azy170331.activitis.entity.CommunicationInfo;
import com.Syuan.azy170331.activitis.entity.GarbageInfo;
import com.Syuan.azy170331.activitis.manager.MemoryManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/5/3.
 */

public class GarbageSQLite {
    private static FileSizeListener listener;

    /**
     * 拷贝数据库到程序，并返回数据库数据
     *
     * @param context
     * @return 返回数据数据list
     */
    public static List<GarbageInfo> getData(Context context) {
        List<GarbageInfo> list = new ArrayList<>();//实例化一个集合

        //输入输出流将assets下的commonnum.db写入到指定的位置
        CopySqlDB.copyDb(context, "clearpath.db");
        String path = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/clearpath.db";
        //打开指定路径下的数据库
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        //查询数据库，得到指定数据
        Cursor cursor = db.query("softdetail", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                GarbageInfo info = new GarbageInfo();
                info.setName(cursor.getString(cursor.getColumnIndex("softChinesename")));
                info.setApkname(cursor.getString(cursor.getColumnIndex("apkname")));
                info.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                info.setClear(false);

                String filepath = MemoryManager.getPhoneInSDCardPath() + cursor.getString(cursor.getColumnIndex("filepath"));
                File file = new File(filepath);
                if (file.exists()) {//判断手机中是否存在该垃圾路径
                    if (listener != null) {
                        listener.sizeListener(getFileSize(file));
                    }

                    info.setSize(getFileSize(file));
                    info.setPath(filepath);
                    info.setFile(file);
                    try {
                        info.setIcon(context.getPackageManager().getApplicationIcon(cursor.getString(cursor.getColumnIndex("apkname"))));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                        info.setIcon(context.getResources().getDrawable(R.mipmap.ic_launcher));
                    }
                    list.add(info);
                }
            }
            while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

    /**
     * 获取文件file大小
     *
     * @param file
     * @return 返回文件file大小
     */

    public static long getFileSize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                size += getFileSize(file1);
            }
        } else {
            return file.length();
        }
        return size;
    }

    /**
     * 删除文件file
     *
     * @param file
     */
    public static void delet(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                delet(file1);
            }
        }
        file.delete();
    }

    //声明接口
    public interface FileSizeListener {
        void sizeListener(long fileSize);
    }

    //使用接口
    public void setListener(FileSizeListener listener) {
        this.listener = listener;
    }
}
