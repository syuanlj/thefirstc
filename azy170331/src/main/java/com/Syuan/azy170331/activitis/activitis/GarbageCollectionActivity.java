package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.GarbageAdapter;
import com.Syuan.azy170331.activitis.entity.GarbageInfo;
import com.Syuan.azy170331.activitis.entity.RunningAppInfo;
import com.Syuan.azy170331.activitis.manager.RunningAppManager;
import com.Syuan.azy170331.activitis.sqlactivity.CopySqlDB;
import com.Syuan.azy170331.activitis.sqlactivity.GarbageSQLite;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.utils.ToastUtil;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class GarbageCollectionActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, GarbageSQLite.FileSizeListener {
    private TitleBarView tbv;
    private TextView tv_garbage;
    private Button bt_garbage;
    private CheckBox cb_garbage;
    private ListView lv_garbage;
    private ProgressBar pb_garbage;
    private GarbageAdapter adapter;
    private List<GarbageInfo> list;
    private GarbageSQLite lite;
    private long curSize;//总垃圾大小

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage_collection);
        findViewById();
        init();
        getData();
    }

    private void getData() {
        curSize=0;
        tv_garbage.setText(curSize+".00B");
        lv_garbage.setVisibility(View.INVISIBLE);
        pb_garbage.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter.add(GarbageSQLite.getData(GarbageCollectionActivity.this));//防止数据量过大放入线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv_garbage.setVisibility(View.VISIBLE);
                        pb_garbage.setVisibility(View.INVISIBLE);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }

    private void findViewById() {
        tbv = (TitleBarView) findViewById(R.id.tbv_garbage);
        tv_garbage = (TextView) findViewById(R.id.tv_garbage);
        bt_garbage = (Button) findViewById(R.id.bt_garbage);
        cb_garbage = (CheckBox) findViewById(R.id.cb_garbage);
        lv_garbage = (ListView) findViewById(R.id.lv_garbage);
        pb_garbage= (ProgressBar) findViewById(R.id.pb_garbage);
        cb_garbage.setOnCheckedChangeListener(this);
        bt_garbage.setOnClickListener(this);
        tbv.initTitleBar(onLeftListener, R.mipmap.btn_homeasup_default, "垃圾清理");
    }

    private void init() {
        lite=new GarbageSQLite();
        list = new ArrayList<>();
        adapter = new GarbageAdapter(this);
        lv_garbage.setAdapter(adapter);
        lite.setListener(this);
    }

    private View.OnClickListener onLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_garbage:
                List<GarbageInfo> list1=adapter.getList();
                for (GarbageInfo info : list1){
                    info.setClear(isChecked);
                }
                adapter.add(list1);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_garbage:
                List<GarbageInfo> list2 = adapter.getList();
                for (GarbageInfo info:list2){
                    if (info.isClear()){
                        GarbageSQLite.delet(info.getFile());
                    }
                }
                cb_garbage.setChecked(false);
                adapter.clear();
                getData();
                break;
        }
    }

    @Override
    public void sizeListener(long fileSize) {
        curSize+=fileSize;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_garbage.setText(CommonUtil.getFileSize(curSize));
            }
        });

    }
}
