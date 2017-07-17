package com.fuicuiedu.xc.mvp_20170627;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//一个列表的页面，页面底部有一个按钮，点击按钮，实现加载数据（有转圈圈效果），3s后铺上假数据


//分析：视图与业务

//视图（可见即为视图）
//listview 展示数据
//button 点击加载
//progressbar 加载动画

//视图逻辑（操作，也属于视图）
//progressbar 显示
//progressbar 隐藏
//setData 设置内容（改变ListView，刷新）

//业务：
//获取到数据并且通知视图改变。
public class MainActivity extends AppCompatActivity implements MainView{

    @BindView(R.id.main_lv)ListView mLv;
    @BindView(R.id.main_prb)ProgressBar mPrb;

    private List<String> mData;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mData = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);
        mLv.setAdapter(adapter);
    }

    @OnClick(R.id.main_btn)
    public void onClick(){
        //业务逻辑
        //点击加载数据
        MainPresenter mainPresenter = new MainPresenter(this);
        mainPresenter.loadData();
    }

    @Override
    public void showPrb() {
        mPrb.setVisibility(View.VISIBLE);
        mLv.setVisibility(View.GONE);
    }

    @Override
    public void hidePrb() {
        mPrb.setVisibility(View.GONE);
        mLv.setVisibility(View.VISIBLE);
    }

    @Override
    public void setData(List<String> datas) {
        adapter.addAll(datas);
        adapter.notifyDataSetChanged();
    }
}
