package com.shenyuan.mytest.testactivity;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ListView;
import android.widget.TextView;

import com.shenyuan.mytest.R;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ListView listView;
    private DataAdapter adapter;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addData();
    }

    public void addData() {


        for (int i = 0; i < 100000; i++) {
            new Delay().execute();

        }
    }

    private void init() {
        textView = (TextView) findViewById(R.id.tv_main_time);
        listView = (ListView) findViewById(R.id.lv_main_count);

        adapter = new DataAdapter(this);
        listView.setAdapter(adapter);
    }

    class Delay extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
//
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            i++;
            adapter.add(i + "");
            adapter.notifyDataSetChanged();
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            long sysTime = System.currentTimeMillis();
            CharSequence sysTimeStr = DateFormat.format("yyyy年MM月dd日"+"HH:mm:ss", sysTime);//“HH”为24小时制，“hh”为12小时制
            textView.setText(sysTimeStr); //更新时间
            return false;
        }
    });
}
