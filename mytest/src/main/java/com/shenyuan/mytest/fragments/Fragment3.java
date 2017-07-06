package com.shenyuan.mytest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenyuan.mytest.R;

/**
 * @auther sy on 2017/7/3 17:49.
 * @Email 893110793@qq.com
 */

public class Fragment3 extends Fragment {@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.page3,container,false);

    return view;
}
}
