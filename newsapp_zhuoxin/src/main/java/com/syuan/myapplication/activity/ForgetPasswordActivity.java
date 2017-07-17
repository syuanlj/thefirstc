package com.syuan.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.syuan.myapplication.R;
import com.syuan.myapplication.https.HttpsRequest;
import com.syuan.myapplication.test.BaseActivity;
import com.syuan.myapplication.utils.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_email;
    private Button bt_yes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
    }

    private void init() {
        et_email= (EditText) findViewById(R.id.forget_email);
        bt_yes= (Button) findViewById(R.id.forget_bt);

        bt_yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_bt:
                String email=et_email.getText().toString().trim();
                forgetPassword(email);
                break;
        }
    }

    private void forgetPassword(final String email) {
        if (email.length() == 0) {
            showToast("邮箱不能为空");
            return;
        }
        if (!CommonUtil.verifyEmail(email)) {
            showToast("请输入正确格式的邮箱");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result= HttpsRequest.ForgetPassword(email);
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                Message msg = new Message();
                msg.setData(bundle);
                msg.what = 211;
                handler.sendMessage(msg);
            }
        }).start();
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 211:
                    String str = msg.getData().getString("result");
                    if (str != null) {
                        try {
                            //获得最外层{}里的数据
                            JSONObject jo = new JSONObject(str);
                            if (jo.getInt("status")==0) {
                                jo=jo.getJSONObject("data");
                                showToast(jo.getString("explain"));

                            } else {
                                showToast(jo.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("数据请求失败，请检查网络");
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
