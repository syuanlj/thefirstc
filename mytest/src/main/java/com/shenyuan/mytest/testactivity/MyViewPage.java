package com.shenyuan.mytest.testactivity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.shenyuan.mytest.R;
import com.shenyuan.mytest.fragments.Fragment1;
import com.shenyuan.mytest.fragments.Fragment2;
import com.shenyuan.mytest.fragments.Fragment3;
import com.shenyuan.mytest.fragments.Fragment4;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther sy on 2017/7/3 14:54.
 * @Email 893110793@qq.com
 */

public class MyViewPage extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private Button button1,button2,button3,button4;
    private View v1,v2,v3,v4;
    private LayoutInflater inflater;
    private List<Fragment> viewList;
    private ImageView cursor;
    private int bmpw = 0; // 游标宽度
    private int offset = 0;// // 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
//    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myviewpage);
        inflater=getLayoutInflater();
        initView();
        initData();

    }

    private void initCursorPos() {
        bmpw= BitmapFactory.decodeResource(getResources(),R.mipmap.corsur).getWidth();//获取图片宽度、

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW=dm.widthPixels;//获取分辨率宽度
        offset=(screenW/viewList.size()-bmpw)/2;//计算偏移量

        Matrix mx=new Matrix();
        mx.postTranslate(offset,0);
        cursor.setImageMatrix(mx);//设置动画初识位置

    }

    private void initView() {
        viewPager= (ViewPager) findViewById(R.id.view_pager);
        button1= (Button) findViewById(R.id.bt1);
        button2= (Button) findViewById(R.id.bt2);
        button3= (Button) findViewById(R.id.bt3);
        button4= (Button) findViewById(R.id.bt4);
        cursor= (ImageView) findViewById(R.id.iv_cursor_viewpager);

        v1=inflater.inflate(R.layout.page1,null);
        v2=inflater.inflate(R.layout.page2,null);
        v3=inflater.inflate(R.layout.page3,null);
        v4=inflater.inflate(R.layout.page4,null);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

    }

    private void initData() {
        viewList=new ArrayList<>();
        viewList.add(new Fragment1());
        viewList.add(new Fragment2());
        viewList.add(new Fragment3());
        viewList.add(new Fragment4());
//        adapter=new MyPagerAdapter(viewList);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),viewList);
        initCursorPos();

        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new MyPageChangeListener());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:
                viewPager.setCurrentItem(0,true);
                break;
            case R.id.bt2:
                viewPager.setCurrentItem(1,true);
                break;
            case R.id.bt3:
                viewPager.setCurrentItem(2,true);
                break;
            case R.id.bt4:
                viewPager.setCurrentItem(3,true);
                break;
        }
    }

    public class FragAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public FragAdapter(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            // TODO Auto-generated constructor stub
            mFragments=fragments;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return mFragments.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mFragments.size();
        }

    }

//    private class MyPagerAdapter extends PagerAdapter{
//        private List<View> list=new ArrayList<>();
//
//        public MyPagerAdapter(List<View> list) {
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view==object;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//
//            container.addView(list.get(position));
//            return list.get(position);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(list.get(position));
//        }
//    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter{

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
    //页面改变监听
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{
        int one = offset * 2 + bmpw;// 相邻 偏移量
        int two=one*2;//跨一个卡片
        int three=one*3;//跨两个卡片

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Animation animation=null;
            switch (position){
                case 0:
                    if (currIndex==1){
                        animation=new TranslateAnimation(one,0,0,0);
                    }else if (currIndex==2){
                        animation=new TranslateAnimation(two,0,0,0);
                    }else if (currIndex==3){
                        animation=new TranslateAnimation(three,0,0,0);
                    }
                    break;
                case 1:
                    if (currIndex==0){
                        animation=new TranslateAnimation(offset,one,0,0);
                    }else if (currIndex==2){
                        animation=new TranslateAnimation(two,one,0,0);
                    }else if (currIndex==3){
                        animation=new TranslateAnimation(three,one,0,0);
                    }
                    break;
                case 2:
                    if (currIndex==1){
                        animation=new TranslateAnimation(one,two,0,0);
                    }else if (currIndex==0){
                        animation=new TranslateAnimation(offset,two,0,0);
                    }else if (currIndex==3){
                        animation=new TranslateAnimation(three,two,0,0);
                    }
                    break;
                case 3:
                    if (currIndex==1){
                        animation=new TranslateAnimation(one,three,0,0);
                    }else if (currIndex==2){
                        animation=new TranslateAnimation(two,three,0,0);
                    }else if (currIndex==0){
                        animation=new TranslateAnimation(offset,three,0,0);
                    }
                    break;
            }
            currIndex=position;
            animation.setFillAfter(true);//图片停在动画结束的位置
            animation.setDuration(300);
            cursor.setAnimation(animation);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
