package com.syuan.yitao_zx;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.syuan.commons.ActivityUtils;
import com.syuan.me.MeFragment;
import com.syuan.shop.ShopFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindViews({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    TextView[] textViews;
    @BindView(R.id.main_toobar)
    Toolbar toolbar;
    @BindView(R.id.main_title)
    TextView tv_title;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private boolean isExit=false;

    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityUtils=new ActivityUtils(this);
        ButterKnife.bind(this);
        //初始化数据
        init();
    }

    private void init() {
        viewPager.setAdapter(unLoginAdapter);
    }

    @Override
    public void onBackPressed() {
        if (!isExit){
            isExit=true;
            activityUtils.showToast("再点击一次退出");
            toolbar.postDelayed(new Runnable() {//计时两秒取消退出
                @Override
                public void run() {
                    isExit=false;
                }
            },2000);//2秒过后设置为false
        }else {
            finish();
        }
    }

    //todo 用户已经登录时的适配器

    //用户未登录时的适配器(ctrl + p 查看所需参数)
    private FragmentStatePagerAdapter unLoginAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                //市场
                case 0:
                    return new ShopFragment();
                //消息
                case 1:
                    return new UnLoginFragment();
                //通讯录
                case 2:
                    return new UnLoginFragment();
                //我的
                case 3:
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            //确定4个页面，写死为4
            return 4;
        }
    };

    //textView点击事件
    @OnClick({R.id.tv_shop, R.id.tv_message, R.id.tv_mail_list, R.id.tv_me})
    public void onClick(TextView textView){
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setSelected(false);
            textViews[i].setTag(i);
        }

        //设置选择效果
        textView.setSelected(true);
        //不要平滑效果，第二个参数设置为false
        viewPager.setCurrentItem((int)textView.getTag(),false);
        tv_title.setText(textViews[(int)textView.getTag()].getText());
    }


}
