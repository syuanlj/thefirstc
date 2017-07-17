package com.fuicuiedu.xc.mvp_20170627.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fuicuiedu.xc.mvp_20170627.MyMvp.MyMvpActivity;
import com.fuicuiedu.xc.mvp_20170627.R;

public class DemoActivity extends MyMvpActivity<DemoPresenter,DemoView>
    implements DemoView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    @Override
    public DemoPresenter createPresenter() {
        DemoPresenter demoPresenter = new DemoPresenter();
        return demoPresenter;
    }
}
