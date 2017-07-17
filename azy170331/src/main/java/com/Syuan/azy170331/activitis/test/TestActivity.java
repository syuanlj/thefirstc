package com.Syuan.azy170331.activitis.test;

import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.ListViewAdapter;
import com.Syuan.azy170331.activitis.manager.AppManager;
import com.Syuan.azy170331.activitis.utils.LogUtil;

import java.util.List;


public class TestActivity extends AppCompatActivity {

    private ListView lv;
    private ListViewAdapter listViewAdapter;
    private List<PackageInfo> list;
    private AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
    }

    private void init() {

        //实例化
        appManager=AppManager.getManager(this);
        //获取已安装的数据集
        list=appManager.getList();

        lv = (ListView) findViewById(R.id.lv);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            /**
             * 滑动状态监听
             * @param absListView  ListView
             * @param i 滑动状态
             */
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

                switch (i) {
                    //快速滑动（手指离开屏幕）
                    case SCROLL_STATE_FLING:
                        LogUtil.e("快速滑动");
                        listViewAdapter.setFling(true);
                        break;

                    //静止
                    case SCROLL_STATE_IDLE:
                        LogUtil.e("静止");
                        listViewAdapter.setFling(false);
                        break;

                    //缓慢滑动（手指没有离开屏幕）
                    case SCROLL_STATE_TOUCH_SCROLL:
                        LogUtil.e("慢速滑动");
                        listViewAdapter.setFling(false);
                        break;
                }
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        listViewAdapter = new ListViewAdapter(this);
        lv.setAdapter(listViewAdapter);
//        listViewAdapter.add(list);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listViewAdapter.notifyDataSetChanged();

            }
        });
    }
}
