package com.syuan.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.syuan.https.HttpsRequest;
import com.syuan.testservice.MainActivity;
import com.syuan.testservice.R;

public class MyService2 extends Service {
    private static final String TAG="MyService2";
    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class DownloadBindelr extends Binder{

        public void download(){
            Log.i(TAG,"download");
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
        Intent intent =new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        Notification builder=new NotificationCompat.Builder(this).setContentTitle("通知")
                .setContentText("设置界面的通知消息按钮")
                .setWhen(System.currentTimeMillis())//通知时间
                .setSmallIcon(R.mipmap.ic_launcher)//通知栏的小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//拉开通知栏的大图标
                .setContentIntent(pi)//跳转到指定界面
                .setAutoCancel(true)//点击通知的时候，自动取消
                .build();
        startForeground(1,builder);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsRequest.RequestNewsType();
                Log.i(TAG,"网络请求");
                stopSelf();//停止服务
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

}
