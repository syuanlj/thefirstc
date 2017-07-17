package com.fuicuiedu.xc.mvp_20170627.MyMvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fuicuiedu.xc.mvp_20170627.R;


//需要实现视图接口，需要拿到业务类

public abstract class MyMvpActivity<P extends MyMvpPresenter, V extends MyMvpView>
        extends AppCompatActivity implements MyMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mvp);
    }

    //为什么要创建业务类？
//    因为执行业务的话，必须要有业务类。
    private P p;

    public abstract P createPresenter();

    //经常要用到业务类
    public P getPrensenter() {
        return p;
    }
}
