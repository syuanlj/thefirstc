package com.Syuan.azy170331.activitis.test;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Syuan.azy170331.R;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    BroadcastReceiver3 bcr_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bc_bt_bc:
                intent.putExtra("BC", "标准广播一次");
                intent.setAction("com.Syuan.azy170331");
                sendBroadcast(intent);
                break;
            case R.id.bc_bt_bcp:
                intent.putExtra("BC", "有序广播一次");
                intent.setAction("com.Syuan.azy170331");
                sendOrderedBroadcast(intent, null);
                break;
            case R.id.bc_bt_sbc:
                intent.putExtra("BC", "异步广播一次");
                intent.setAction("com.Syuan.azy170331");
                sendStickyBroadcast(intent);//需要注册权限
                if (bcr_3==null){
                    //初始化广播
                    BroadcastReceiver3 bcr_3 = new BroadcastReceiver3();
                    //过滤条件
                    IntentFilter filter = new IntentFilter("com.Syuan.azy170331");
                    //动态注册广播
                    registerReceiver(bcr_3, filter);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("======:"+111111);
        if (bcr_3 != null) {//判断广播bcr_3是否存在
            unregisterReceiver(bcr_3);//注销广播
        }
    }
}
