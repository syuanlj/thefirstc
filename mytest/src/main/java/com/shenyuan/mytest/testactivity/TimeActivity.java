package com.shenyuan.mytest.testactivity;

import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.shenyuan.mytest.R;

public class TimeActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        textView= (TextView) findViewById(R.id.tv_time);

        new TimeHandler().start();
    }
    class TimeHandler extends Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);

                }
                catch (Exception e){

                }
            }while (true);
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                    textView.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;

            }
        }
    };


}
