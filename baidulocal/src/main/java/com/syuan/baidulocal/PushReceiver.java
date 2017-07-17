package com.syuan.baidulocal;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;

/**
 * Created by sy on 2017/6/21.
 */

public class PushReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {

        Log.i("onBind", s);
        Log.i("onBind", s1);
        Log.i("onBind", s2);
        Log.i("onBind", s3);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        Log.i("onUnbind", s);

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.i("onSetTags", s);
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.i("onDelTags", s);
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        Log.i("onListTags", s);
    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        Log.i("onMessage", s);
        Log.i("onMessage", s1);
    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {
        Log.i("onNotificationClicked", s);
        Log.i("onNotificationClicked", s1);
        Log.i("onNotificationClicked", s2);
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {
        Log.i("onNotificationArrived", s);
        Log.i("onNotificationArrived", s1);
        Log.i("onNotificationArrived", s2);

    }
}
