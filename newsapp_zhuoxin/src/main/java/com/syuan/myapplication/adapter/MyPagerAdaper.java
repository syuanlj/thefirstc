package com.syuan.myapplication.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author Syuan
 * Created by sy on 2017/4/1.
 */

public class MyPagerAdaper extends PagerAdapter {
    List<View> list;

    public MyPagerAdaper() {
        list = new ArrayList<>();
    }

    /**
     * 动态方法添加数据
     */
    public void addView(View view) {
        list.add(view);
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 删除当前list中的数据
     * @param container 容器
     * @param position 当前list的物理地址
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));//消除元素
    }

    /**
     * 向当前list存放数据
     * @param container 容器
     * @param position 当前list的物理地址
     * @return 返回存入的数据
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = list.get(position);
        container.addView(v);
        return v;
    }
}
