package com.syuan.https;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sy on 2017/6/25.
 */

public class HttpsRequest {


    public static InputStream loadInputStreamBitmap(String url) {
        Response response = RequestCallBack(url);
        if (response == null) {
            Log.e("====info2","44");
            return null;
        } else {
            Log.e("====info2","55");
            return response.body().byteStream();
        }
    }

    /**
     * 获取网络信息
     *
     * @param url 某类型视频地址
     * @return
     */
    public static String MyHttpsRquest(String url) {
        //初始化okHttpClient，设置连接5秒超时，5秒读取超时
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();//设置http请求
        try {
            //网络连接请求
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Response RequestCallBack(String url) {
        //初始化okHttpClient，设置连接5秒超时，5秒读取超时
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();//设置http请求
        try {
            //网络连接请求
            Response response = okHttpClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
