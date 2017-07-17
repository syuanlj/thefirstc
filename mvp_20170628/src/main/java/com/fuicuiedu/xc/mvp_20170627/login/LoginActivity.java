package com.fuicuiedu.xc.mvp_20170627.login;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fuicuiedu.xc.mvp_20170627.R;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//实现一个登陆等功能，登陆页面，两个输入框，一个按钮。点击按钮显示加载动画，三秒后提示登录成功


public class LoginActivity extends MvpActivity<LoginView,LoginPresenter>
    implements LoginView{

    @BindView(R.id.login_prb)ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    // 创建业务类
    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        LoginPresenter loginPresenter = new LoginPresenter();
        return loginPresenter;
    }

    @OnClick(R.id.login_btn)
    public void onClick(){
        //拿到业务类，执行业务
        getPresenter().login();
    }

    //  #########################   视图逻辑相关  ################
    @Override
    public void showPrb() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePrb() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
