package com.syuan.testservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.syuan.service.MyIntentService;
import com.syuan.service.MyService2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView start, destroy, connection;
    private MyService2.DownloadBindelr bindDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
    }

    private void findViewById() {
        start = (TextView) findViewById(R.id.start_service);
        destroy = (TextView) findViewById(R.id.destroy_service);
        connection = (TextView) findViewById(R.id.connection_service);

        start.setOnClickListener(this);
        destroy.setOnClickListener(this);
        connection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MyService2.class);
        Intent intent1 = new Intent(this, MyIntentService.class);
        switch (v.getId()) {
            case R.id.start_service:
                if (start.getText().equals("开启服务")) {
                    start.setText("关闭服务");
                    startService(intent);
                } else {
                    start.setText("开启服务");
                    stopService(intent);
                }
                break;
            case R.id.destroy_service:
                stopService(intent);
                break;
            case R.id.connection_service:
                if (start.getText().equals("连接服务")) {
                    start.setText("断开连接");
                    bindService(intent1, scon, BIND_AUTO_CREATE);
                } else {
                    start.setText("连接服务");
                    unbindService(scon);
                }
                break;
        }
    }

    private ServiceConnection scon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bindDownload = (MyService2.DownloadBindelr) service;
            bindDownload.download();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
