package com.Syuan.azy170331.activitis.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.Syuan.azy170331.activitis.utils.ToastUtil;

/**
 * Created by sy on 2017/4/18.
 */

public class BroadcastReceiver3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String str=intent.getStringExtra("BC");
//        LogUtil.e(str);
        Log.e("BroadcastReceiver3：",str);
        ToastUtil.shortshow(context,str);
    }
}
