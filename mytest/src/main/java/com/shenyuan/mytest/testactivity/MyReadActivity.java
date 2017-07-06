package com.shenyuan.mytest.testactivity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.shenyuan.mytest.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MyReadActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_read);
        textView = (TextView) findViewById(R.id.tv_read);
        setText();
    }

    private void setText() {
        textView.setText(getFromAssets("tease.txt"));
    }
//    public static final String ENCODING = "GBK";

    //从assets 文件夹中获取文件并读取数据
    public String getFromAssets(String fileName) {
        InputStream is = null;
        String result = "11111111";
        try {
            is = getAssets().open(fileName);
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            result = new String(buffer, "GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
