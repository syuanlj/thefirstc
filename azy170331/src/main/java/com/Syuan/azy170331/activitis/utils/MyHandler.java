package com.Syuan.azy170331.activitis.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by sy on 2017/4/12.
 */

public class MyHandler extends Handler {

    public MyHandler() {
    }

    public MyHandler(Looper L) {
        super(L);
    }          // 子类必须重写此方法，接受数据

    @Override
    public void handleMessage(Message msg) {
        Log.d("MyHandler", "handleMessage......");
        super.handleMessage(msg);
        Bundle b = msg.getData();
        String color = b.getString("color");
    }


}
