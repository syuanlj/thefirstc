package com.syuan.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.syuan.myapplication.R;
import com.syuan.myapplication.activity.MainActivity;
import com.syuan.myapplication.manage.MyActivityManager;

/**
 * Created by sy on 2017/6/3.
 */

public class LeftMenuFragment extends Fragment implements View.OnClickListener {
    private TextView tv_news, tv_read, tv_local, tv_ties, tv_pics;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        initView(view);//初始化控件
        initEvent();
        return view;
    }

    private void initEvent() {
        tv_news.setOnClickListener(this);
        tv_read.setOnClickListener(this);
        tv_local.setOnClickListener(this);
        tv_ties.setOnClickListener(this);
        tv_pics.setOnClickListener(this);
    }

    private void initView(View view) {
        tv_news = (TextView) view.findViewById(R.id.left_menu_tv_news);
        tv_read = (TextView) view.findViewById(R.id.left_menu_tv_read);
        tv_local = (TextView) view.findViewById(R.id.left_menu_tv_local);
        tv_ties = (TextView) view.findViewById(R.id.left_menu_tv_ties);
        tv_pics = (TextView) view.findViewById(R.id.left_menu_tv_pics);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_menu_tv_news:
//                MainActivity a= (MainActivity) getActivity();
//                a.showNewsListFragment();
                MyActivityManager.getMainActivity().showNewsListFragment();
                break;
            case R.id.left_menu_tv_read:
                MyActivityManager.getMainActivity().showCommentFragment();
                Toast.makeText(getActivity(),"收藏夹",Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_menu_tv_local:

                Toast.makeText(getActivity(),"本地",Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_menu_tv_ties:
                Toast.makeText(getActivity(),"跟帖",Toast.LENGTH_SHORT).show();
                break;
            case R.id.left_menu_tv_pics:
                Toast.makeText(getActivity(),"图片",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
