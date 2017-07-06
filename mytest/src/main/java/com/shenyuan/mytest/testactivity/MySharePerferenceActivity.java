package com.shenyuan.mytest.testactivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shenyuan.mytest.R;


public class MySharePerferenceActivity extends Activity implements View.OnClickListener {
    private EditText et_name, et_password;
    private Button bt;
    private TextView tv;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_perference);
        findViewById();
        initdata();
    }

    private void findViewById() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        bt = (Button) findViewById(R.id.sp_bt);
        tv = (TextView) findViewById(R.id.sp_tv);
    }

    private void initdata() {
        sp = getSharedPreferences("user", this.MODE_PRIVATE);//实例化SharedPreferences
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp_bt:
                setUserData();
                tv.setText(getUserData());//显示内存中的用户名密码
                break;
        }
    }

    //获得edittext中的用户名与密码，并存储
    private void setUserData() {
        String name = et_name.getText().toString();
        String password = et_password.getText().toString();

        //存数据
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.putString("password", password);
        editor.apply();//提交所要存储的数据
    }

    //取得内存中的用户名与密码
    private String getUserData() {
        String name = sp.getString("name", null);//获得相应字段的内容
        String password = sp.getString("password", null);
        return "用户名：" + name + "\n密码：" + password;
    }
}
