package com.syuan.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.syuan.myapplication.R;
import com.syuan.myapplication.fragment.LeftMenuFragment;
import com.syuan.myapplication.fragment.RightMenuFragment;
import com.syuan.myapplication.https.HttpsRequest;
import com.syuan.myapplication.test.BaseActivity;
import com.syuan.myapplication.utils.CommonUtil;
import com.syuan.myapplication.view.TitleBarView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sy on 2017/6/5.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText reg_email, reg_name, reg_password;
    private Button reg_bt;
    private CheckBox reg_cb;
    private TitleBarView tvb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_register);
        findViewById();
    }

    private void findViewById() {
        reg_email = (EditText) findViewById(R.id.reg_et_email);
        reg_name = (EditText) findViewById(R.id.reg_et_name);
        reg_password = (EditText) findViewById(R.id.reg_et_password);
        reg_bt = (Button) findViewById(R.id.reg_bt);
        reg_cb = (CheckBox) findViewById(R.id.reg_cb);
        tvb= (TitleBarView) findViewById(R.id.reg_title);

        reg_bt.setOnClickListener(this);
        reg_cb.setChecked(false);
        init();
    }
    private void init() {
        tvb.initTitleBar(onLeftClickListener, R.mipmap.back,getString(R.string.reg_bt));
    }

    public View.OnClickListener onLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_left:
                    finish();
//                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    break;
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_bt:
                String email = reg_email.getText().toString().trim();
                String name = reg_name.getText().toString().trim();
                String password = reg_password.getText().toString().trim();
                register(email, name, password);

                break;
        }
    }

    /**
     * 注册
     * @param email 邮箱
     * @param name 用户名
     * @param password 密码
     */
    private void register(final String email, final String name, final String password) {


        if (email.length() == 0) {
            showToast("邮箱不能为空");
            return;
        }
        if (name.length() == 0) {
            showToast("昵称不能为空");
            return;
        }
        if (password.length() == 0) {
            showToast("密码不能为空");
            return;
        }
        if (password.length() < 6 || password.length() > 16) {
            showToast("密码位数在6到16之间");
            return;
        }

        if (!CommonUtil.verifyEmail(email)) {
            showToast("请输入正确格式的邮箱");
            return;
        }
        if (!CommonUtil.verifyPassword(password)) {
            showToast("请输入正确格式的密码");
            return;
        }
        if (!reg_cb.isChecked()) {
            showToast("没有同意协议条款");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpsRequest.Register(email, name, password);
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                bundle.putString("name", name);
                bundle.putString("password", password);
                Message msg = new Message();
                msg.setData(bundle);
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String str = msg.getData().getString("result");
                    if (str != null) {
                        try {
                            //获得最外层{}里的数据
                            JSONObject jo = new JSONObject(str);
                            if (jo.getString("message").equals("OK")) {
                                //获得data中的数据
                                jo = new JSONObject(jo.getString("data"));
                                //吐司最终结果
                                showToast(jo.getString("explain"));
                                if (jo.getInt("result") == 0) {
                                    //注册成功，返回登录界面
                                    String name = msg.getData().getString("name");
                                    String password = msg.getData().getString("password");
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name", name);
                                    bundle.putString("password", password);
                                    intent.putExtras(bundle);
                                    setResult(2, intent);//返回码为2
                                    finish();
                                }

                            } else {
                                showToast(jo.getString("explain"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToast("连接失败，请重试");
                    }
                    break;
            }
            return false;
        }
    });

}
