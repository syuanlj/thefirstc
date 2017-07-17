package com.syuan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syuan.butterknife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：sy
 * 邮箱：893110793@qq.com
 */

public class MyFragment extends Fragment{

    @BindView(R.id.main_tv1)
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,container,false);
        //Butterknife的绑定！！！
        ButterKnife.bind(this,view);
        return view;
    }
}
