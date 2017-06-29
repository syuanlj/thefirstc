package com.syuan.network;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by sy on 2017/6/26.
 */

public class EasyShopClient {
    private static EasyShopClient easyShopClient;
    private OkHttpClient okHttpClient;

    public EasyShopClient(){

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public static EasyShopClient getInstance(){
        if (easyShopClient==null){
            easyShopClient=new EasyShopClient();
        }
        return easyShopClient;
    }

    //注册
    public Call register(String username, String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=register")//添加URL
                .post(requestBody)//请求方式为post
                .build();

        return okHttpClient.newCall(request);
    }

    //登陆
    public Call login(String username, String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://wx.feicuiedu.com:9094/yitao/UserWeb?method=login")//添加URL
                .post(requestBody)//请求方式为post
                .build();

        return okHttpClient.newCall(request);
    }
}
