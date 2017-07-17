package com.Syuan.azy170331.activitis.sqlactivity;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sy on 2017/5/5.
 */

public class CopySqlDB {
    /**
     * 复制数据库到程序
     * @param context
     * @param dbname
     */
    public static void copyDb(Context context,String dbname){
        try {
            //输入流,获得文件
            InputStream inputStream = context.getAssets().open(dbname);
            //路径：将assets中的文件写到指定位置的路径（绝对路径）
            String directory = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases";//文件夹
            String path = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/"+dbname;//文件
            //不是文件，也不是路径，而是相当于一个对象，判断文件是否存在的对象
            File file1 = new File(directory);
            File file = new File(path);
            //判断
            if (!file1.exists()) {//不存在则创建
                file1.mkdirs();
            }
            if (!file.exists()) {//不存在则创建
                file.createNewFile();
                //文件输出流
                FileOutputStream outputStream = new FileOutputStream(file);
                //缓冲池1K
                byte[] bytes = new byte[1024];
                int c = 0;
                //写入
                while ((c = inputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, c);
                }
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件file大小
     * @param file
     * @return 返回文件file大小
     */
    public long getSize(File file){
        long size=0;
        if (file.isDirectory()){
            File[] files=file.listFiles();
            for (File file1:files){
                size+=getSize(file1);
            }
        }else {
            return file.length();
        }
        return size;
    }

    /**
     * 删除文件file
     * @param file
     */
    public void delet(File file){
        if (file.isDirectory()){
            File[] files=file.listFiles();
            for (File file1:files){
                delet(file1);
            }
        }
        file.delete();
    }
}
