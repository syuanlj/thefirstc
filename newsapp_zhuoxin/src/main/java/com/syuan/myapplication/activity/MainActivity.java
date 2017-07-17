package com.syuan.myapplication.activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.fragment.LeftMenuFragment;
import com.syuan.myapplication.fragment.NewsListFragment;
import com.syuan.myapplication.fragment.RightMenuFragment;
import com.syuan.myapplication.fragment.Save;
import com.syuan.myapplication.manage.MyActivityManager;
import com.syuan.myapplication.view.TitleBarView;
import com.syuan.myapplication.view.slidingmenu.SlidingMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl;
    private SlidingMenu sm;
    private TitleBarView tbv;
    private static String main = "资讯";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyActivityManager.setMainActivity(this);
        initView();
        initSlidingMenu();
        showNewsListFragment();//默认展示新闻界面
    }

    public void initSlidingMenu() {
        sm = new SlidingMenu(this);
        sm.setMode(SlidingMenu.LEFT_RIGHT);//设置左右滑动模式
        sm.setBehindOffset(200);//设置偏移剩余距离
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//触发模式
        sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//设置Activity（滑动内容）
        sm.setMenu(R.layout.menu_left);//设置左边的布局
        sm.setSecondaryMenu(R.layout.menu_right);//设置右边的布局

        Fragment leftMenuFragment = new LeftMenuFragment();
        Fragment rightMenuFragment = new RightMenuFragment();
        getFragmentManager().beginTransaction().replace(R.id.ll_menu_left, leftMenuFragment).commit();
        getFragmentManager().beginTransaction().replace(R.id.ll_menu_right, rightMenuFragment).commit();
    }


    public void showNewsListFragment() {
        main = "资讯";
        tbv.initTitleBar(this, R.mipmap.ic_title_home_default
                , main
                , this, R.mipmap.ic_title_share_default);
        if (sm.isMenuShowing()) {
            sm.showContent();
        }
        //替换fragment
        getFragmentManager().beginTransaction().replace(R.id.main_rl, new NewsListFragment()).commit();
    }

    public void showCommentFragment() {
        main = "收藏";
        tbv.initTitleBar(this, R.mipmap.ic_title_home_default
                , main
                , this, R.mipmap.ic_title_share_default);
        if (sm.isMenuShowing()) {
            sm.showContent();
        }
        getFragmentManager().beginTransaction().replace(R.id.main_rl, new Save()).commit();
    }

    private void initView() {
        rl = (RelativeLayout) findViewById(R.id.main_rl);
        tbv = (TitleBarView) findViewById(R.id.main_title);
        tbv.initTitleBar(this, R.mipmap.ic_title_home_default
                , main
                , this, R.mipmap.ic_title_share_default);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_right:
                sm.showSecondaryMenu();
                break;
            case R.id.rl_left:
                sm.showMenu();
                break;
        }
    }
}
