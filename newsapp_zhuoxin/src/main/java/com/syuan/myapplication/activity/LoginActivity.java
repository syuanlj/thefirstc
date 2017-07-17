package com.syuan.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.syuan.myapplication.R;
import com.syuan.myapplication.https.HttpsRequest;
import com.syuan.myapplication.test.BaseActivity;
import com.syuan.myapplication.utils.CommonUtil;
import com.syuan.myapplication.view.TitleBarView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name,et_password;
    private Button bt_login,bt_forget,bt_reg;
    private TitleBarView tvb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
    }

    private void findViewById() {
        et_name= (EditText) findViewById(R.id.login_et_name);
        et_password= (EditText) findViewById(R.id.login_et_password);
        bt_login= (Button) findViewById(R.id.login_bt);
        bt_forget= (Button) findViewById(R.id.login_bt_forget);
        bt_reg= (Button) findViewById(R.id.login_bt_reg);
        tvb= (TitleBarView) findViewById(R.id.login_title);

        bt_login.setOnClickListener(this);
        bt_forget.setOnClickListener(this);
        bt_reg.setOnClickListener(this);
        init();
    }
    private void init() {
        tvb.initTitleBar(onLeftClickListener, R.mipmap.back, getString(R.string.login_bt));
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
        switch (v.getId()){
            case R.id.login_bt:
                String name=et_name.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                login(name,password);//登录
                break;
            case R.id.login_bt_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
            case R.id.login_bt_reg:
                startActivityForResult(new Intent(this,RegisterActivity.class),1);//请求码为1
                break;
        }
    }

    //登录
    private void login(final String name, final String password) {

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
        if (!CommonUtil.verifyPassword(password)) {
            showToast("请输入正确格式的密码");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String str= HttpsRequest.Login(name,password);
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("data",str);
                msg.setData(bundle);
                msg.what=1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String str =msg.getData().getString("data");
                    if (str!=null){
                        try {
                            JSONObject jo=new JSONObject(str);
                            if (jo.getString("message").equals("OK")){
                                 jo=jo.getJSONObject("data");
                                showToast(jo.getString("explain"));
                                String token=jo.getString("token");
                                Intent intent=new Intent();
                                Bundle bundle=new Bundle();
                                bundle.putString("token",token);
                                intent.putExtras(bundle);
                                setResult(21,intent);
                                finish();
                            }else {
                                showToast("登录失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast("数据异常");
                        }
                    }else {
                        Log.e("login","异常，登录失败");
                    }
                    break;
            }
            return false;
        }
    });
    @Override                       //请求码，          返回码
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            switch (resultCode){
                case 2:
                    String name=data.getExtras().getString("name");
                    String password =data.getExtras().getString("password");
                    login(name,password);
                    break;
            }
        }
    }
}
