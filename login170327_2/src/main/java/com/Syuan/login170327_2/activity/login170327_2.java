package com.Syuan.login170327_2.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Syuan.login170327_2.R;
import com.Syuan.login170327_2.activity.chatActivity;
import com.Syuan.login170327_2.util.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class login170327_2 extends AppCompatActivity implements View.OnClickListener {
    private Button bt;
    private ImageView iv;
    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login170327_2);
        init();


    }

    /**
     * 初始化
     */
    public void init() {
        //找到控件
        bt = (Button) findViewById(R.id.bt1);
        iv = (ImageView) findViewById(R.id.iv);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);


        //设置监听
        bt.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (nameIsNull() || passwordIsNull()) {
            return;
        }
        if (isRight() ) {

            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, chatActivity.class);
            startActivity(intent);
           save();
            return;
        }
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();

    }

    private void save() {
        String userName="name:"+et1.getText().toString()
                ,userPassword="\npassword:"+et2.getText().toString();
        File diretory=new File(Environment.getExternalStorageDirectory()+"/text");
        File file=new File(Environment.getExternalStorageDirectory()+"/text/userinfo.txt");

        if(diretory.exists()){
            diretory.delete();
        }
        diretory.mkdirs();
        try {
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            LogUtil.e("1111111111111");
            OutputStream os=new FileOutputStream(file);
            os.write(userName.getBytes());
            os.write(userPassword.getBytes());
            os.close();
        } catch (Exception e) {
            Log.e("============","enter catch");
            e.printStackTrace();
        }
    }

    /**
     * 判断是否为空
     *
     * @return true异常，false正常
     */

    public boolean nameIsNull() {
        if (null == et1.getText() || 0 == et1.getText().length()) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean passwordIsNull() {
        if (null == et2.getText() || 0 == et2.getText().length()) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 判断是否正确
     * @return true 正确 ，false 异常
     */
    public boolean isRight() {
        if ("admin".equals(et1.getText().toString())&&"qwerdf".equals(et2.getText().toString())) {

            return true;
        }
        return false;
    }


}
