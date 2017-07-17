package com.syuan.myapplication.https;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sy on 2017/6/5.
 */

public class HttpsRequest {
    /**
     * 登录请求
     *
     * @param name     用户名
     * @param password 密码
     * @return
     */
    public static String Login(String name, String password) {
        //user_login?ver=版本号&uid=用户名&pwd=密码&device=0
        String url = HttpUrl.MAIN_URL + "user_login?ver=1&uid=" + name + "&pwd=" + password + "&device=" + 0;//地址
        return MyHttpsRquest(url);
    }

    /**
     * 注册网络请求
     *
     * @param email
     * @param name
     * @param password
     * @return
     */
    public static String Register(String email, String name, String password) {
        //user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
        String url = HttpUrl.MAIN_URL + "user_register?ver=1&uid=" + name + "&email=" + email + "&pwd=" + password;

        return MyHttpsRquest(url);
    }

    public static String ForgetPassword(String email) {
        String url = HttpUrl.MAIN_URL + "user_login?ver=1&email=" + email;
        return MyHttpsRquest(url);
    }

    /**
     * 发送评论
     * @param token 手机令牌
     * @param nid 新闻id
     * @param imei 手机识别码
     * @param comment 评论内容
     * @return
     */
    public static String sendComment(String token, int nid, String imei, String comment) {
        //发送评论
        String url = "http://118.244.212.82:9092/newsClient/cmt_commit?ver=1&nid="
                + nid + "&token=" + token + "&imei=" + imei + "&ctx=" + comment;
        return MyHttpsRquest(url);
    }

    /**
     * 获取评论内容
     * @param nid 新闻id
     * @param dir 方向上的操作：上拉1，下拉2
     * @param cid 评论编号
     * @return
     */
    public static String requestComment(int nid, int dir, int cid) {
        //请求评论
        //cmt_list?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
        String url = HttpUrl.MAIN_URL + "cmt_list?ver=2&nid=" + nid
                + "&type=1&stamp=null&cid=" + cid + "&dir=" + dir + "&cnt=20";
        return MyHttpsRquest(url);
    }

    public static String requestCommentNum(int nid){
        //请求评论数量
        //cmt_num?ver=版本号& nid=新闻编号
        String url= HttpUrl.MAIN_URL+"cmt_num?ver=2&nid="+nid;
        return MyHttpsRquest(url);
    }

    /**
     * 利用手机令牌获得用户信息
     *
     * @param token 手机令牌
     * @return
     */
    public static String RequestUser(String token) {
        //user_home?ver=版本号&imei=手机标识符& token =用户令牌
        String url = HttpUrl.MAIN_URL + "user_home?ver=2&imei=0&token=" + token;
        return MyHttpsRquest(url);
    }

    public static String RequestNewsType() {
        //news_sort?ver=版本号&imei=手机标识符
        //地址与参数之间用？链接，参数之间用&链接
        String url = "http://118.244.212.82:9092/newsClient/news_sort?ver=2&imei=2";
        return MyHttpsRquest(url);
    }

    public static InputStream loadInputStreamBitmap(String url) {
        Response response = RequestCallBack(url);
        if (response == null) {
            return null;
        } else {
            return response.body().byteStream();
        }
    }

    /**
     * 请求新闻列表
     *
     * @param subid
     * @param dir
     * @param nid
     * @return
     */
    public static String requestNewsList(int subid, int dir, int nid) {
        //news_list?ver=版本号&subid=分类名&dir=1&nid=新闻id&stamp=20140321&cnt=2
        //地址与参数之间用？链接，参数之间用&链接
        String url = "http://118.244.212.82:9092/newsClient/news_list?ver=2&subid=" + subid +
                "&dir=" + dir + "&nid=" + nid + "&stamp=null&cnt=2";
        return MyHttpsRquest(url);
    }

    public static String MyHttpsRquest(String url) {
        //初始化okHttpClient，设置连接5秒超时，5秒读取超时
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url).build();//设置http请求
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
                .connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url).build();//设置http请求
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
