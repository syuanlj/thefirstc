package com.shenyuan.baidumap;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GetSPActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sp);

        SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);
        String name =sp.getString("name",null);
        String password=sp.getString("password",null);

        textView= (TextView) findViewById(R.id.tv);
        textView.setText("用户名："+name+"\n密码："+password);
    }
}
