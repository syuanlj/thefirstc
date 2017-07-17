package com.Syuan.azy170331.activitis.activitis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.ListViewAdapter;
import com.Syuan.azy170331.activitis.entity.GarbageInfo;
import com.Syuan.azy170331.activitis.entity.SoftManagerInfo;
import com.Syuan.azy170331.activitis.manager.AppManager;
import com.Syuan.azy170331.activitis.utils.LogUtil;
import com.Syuan.azy170331.activitis.utils.ToastUtil;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;


public class AllSoftwareActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ListView lv_soft;
    private ListViewAdapter listViewAdapter;
    private TitleBarView tbv;
    private ProgressBar pb_soft;
    private CheckBox cb_soft;
    private Button bt_soft;

    private List<SoftManagerInfo> allSoftList;//所有软件
    private List<SoftManagerInfo> sysSoftList;//系统软件
    private List<SoftManagerInfo> userSoftList;//用户软件
    private PackageManager pm;
    private int position;
    //声明一个广播：卸载软件后，用来接收软件已被卸载的通知
    private RemoveAppReceiver removeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_software);
        findViewById();
        init();
        addDataList();
    }

    private void findViewById() {
        tbv = (TitleBarView) findViewById(R.id.tbv);
        lv_soft = (ListView) findViewById(R.id.lv_soft);
        pb_soft = (ProgressBar) findViewById(R.id.pb_soft);
        cb_soft= (CheckBox) findViewById(R.id.cb_soft);
        bt_soft= (Button) findViewById(R.id.bt_soft);

        cb_soft.setOnCheckedChangeListener(this);
        bt_soft.setOnClickListener(this);
    }

    private void init() {
        allSoftList = new ArrayList<>();
        sysSoftList = new ArrayList<>();
        userSoftList = new ArrayList<>();
        pm = getPackageManager();
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        position = intent.getIntExtra("id", 0);
        tbv.initTitleBar(onLeftClickListener, R.mipmap.btn_homeasup_default, name);//设置标题栏
        //注册广播
        removeReceiver = new RemoveAppReceiver();//实例化广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(removeReceiver, filter);//注册广播

        lv_soft.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        lv_soft.setAdapter(listViewAdapter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listViewAdapter.notifyDataSetChanged();

            }
        });
    }

    private void addDataList() {

        lv_soft.setVisibility(View.INVISIBLE);
        pb_soft.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {//写线程是因为不知道有多少数据，线程可加快加载速度
            @Override
            public void run() {
//获得数据
                getSoftData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (position) {
                            case 0:
                                listViewAdapter.add(allSoftList);
                                break;
                            case 1:
                                listViewAdapter.add(sysSoftList);
                                break;
                            case 2:
                                listViewAdapter.add(userSoftList);
                                break;
                        }
                        lv_soft.setVisibility(View.VISIBLE);
                        pb_soft.setVisibility(View.INVISIBLE);
                        listViewAdapter.notifyDataSetChanged();
                    }

                });
            }
        }).start();
    }

    private void getSoftData() {
        //因为此处是重新获取数据，因此需要做清理
        listViewAdapter.clear();
        if (allSoftList != null) {
            allSoftList.clear();
        }
        if (sysSoftList != null) {
            sysSoftList.clear();
        }
        if (userSoftList != null) {
            userSoftList.clear();
        }
        String packageName;//安装包包名
        String lableName;//运行程序名称
        Drawable icon;//应用图标
        boolean isClear;//是否需要清除
        long firstInsetData;//安装时间
        //获取新数据
        List<PackageInfo> infolist=pm.getInstalledPackages(PackageManager.GET_ACTIVITIES|PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo info:infolist){
            packageName=info.packageName;
            lableName= (String) info.applicationInfo.loadLabel(pm);
            icon=info.applicationInfo.loadIcon(pm);
            firstInsetData=info.firstInstallTime;
            SoftManagerInfo softInfo=new SoftManagerInfo(packageName,lableName,icon,false,firstInsetData);
            allSoftList.add(softInfo);
            //按位&两边都为1时结果为1
            if ((info.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==1){//true：系统软件；false:用户软件
                sysSoftList.add(softInfo);
            }else {
                userSoftList.add(softInfo);
            }
        }
    }

    private View.OnClickListener onLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //全选或全不选
        for (SoftManagerInfo info:listViewAdapter.getList()){
            info.setClear(isChecked);
        }
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        cb_soft.setChecked(false);
        //卸载
        String packagename;
        for (SoftManagerInfo info:listViewAdapter.getList()){
            if (info.isClear()){
                info.setClear(false);
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DELETE);
                packagename=info.getPackageName();
                if (packagename.equals(getPackageName())){//判断是否是自身
                    ToastUtil.shortshow(this,"不支持卸载自身");
                    continue;
                }
                intent.setData(Uri.parse("package:"+packagename));
                startActivity(intent);
            }
        }
    }

    private class RemoveAppReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到广播（卸载通知）后，刷新界面
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){
                addDataList();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (removeReceiver != null) {
            unregisterReceiver(removeReceiver);//动态注册广播需要注销掉
        }
    }
}
