package com.syuan.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


import com.syuan.https.HttpsRequest;
import com.syuan.videozx.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by sy on 2017/6/6.
 */

public class LoadImage {
    //开辟存储空间
    private static LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(1024 * 1024 * 3) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getByteCount();
        }
    };

    public static void loadImage(Context context, String url, ImageView imageView) {
        //获取图片途径：缓存中，本地存储，网络下载
        //从缓存中获得图片
        Bitmap bitmap = lruCache.get(url);
        if (bitmap == null && context != null) {
            //本地
            File cashDir = context.getCacheDir();
            if (cashDir.exists()) {
                File[] files = cashDir.listFiles();
                //首先判断files是否为空
                if (files != null) {
                    for (File file : files) {
                        //首先获得文件名称
                        int a = url.lastIndexOf("/");
                        String fileName = url.substring(a + 1);
                        //匹配file，看哪一个是需要的
                        if (fileName.equals(file.getName())) {
                            //将绝对路径下的文件转换成Bitmap
                            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                            return;
                        }
                    }
                }
            }
        } else {
            imageView.setImageBitmap(bitmap);
            return;
        }
        if (url==null){
            imageView.setImageResource(R.mipmap.ic_launcher);
            return;
        }
        //开启异步任务，网络下载头像
        LoadImageAsyncTask loadImageAsyncTask = new LoadImageAsyncTask(context, imageView);
        loadImageAsyncTask.execute(url);//执行异步任务
    }


    private static class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private Context context;
        private ImageView imageView;

        public LoadImageAsyncTask(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            InputStream inputStream = HttpsRequest.loadInputStreamBitmap(url);//网络下载
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//转换
            lruCache.put(url, bitmap);//保存到缓存
            //保存到本地的缓存目录
            File file = context.getCacheDir();
            if (!file.exists()) {
                file.mkdir();
            }
            int a = url.lastIndexOf("/");
            String fileName = url.substring(a + 1);
            //输出流
            try {
                OutputStream outputStream = new FileOutputStream(new File(file, fileName));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);//压缩图片到某位置，100表示不压缩
                inputStream.close();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
