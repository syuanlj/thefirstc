package com.Syuan.weixinface.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.Syuan.weixinface.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//注册页面，有输入用户名，密码，验证密码，点击注册按钮，显示三秒动画，后吐司注册成功
//
//view
//三个edittext，一个butten，中间有一个progressbar
//
//model
//progressbar的显示和隐藏
//吐司
//
//presenter
//点击注册后，延迟三秒模拟网络请求，吐司注册成功
public class RegisterActivity extends AppCompatActivity implements RegisterModel {
    @BindView(R.id.prob)
    ProgressBar prob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

   @OnClick(R.id.bt_register)
   public void onClick(){
       Toast.makeText(this,"===",Toast.LENGTH_SHORT).show();
       new RegisterPresenter(this).register();
   }

    @Override
    public void showProb() {
        prob.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProb() {
        prob.setVisibility(View.INVISIBLE);
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
