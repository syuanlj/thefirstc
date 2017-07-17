package com.Syuan.azy170331.activitis.activitis;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.SpeedUpAdapter;
import com.Syuan.azy170331.activitis.entity.RunningAppInfo;
import com.Syuan.azy170331.activitis.manager.MemoryManager;
import com.Syuan.azy170331.activitis.manager.PhoneManager;
import com.Syuan.azy170331.activitis.manager.RunningAppManager;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import jaredrummler.android.processes.ProcessManager;
import jaredrummler.android.processes.models.AndroidAppProcess;

public class SpeedUpActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private TitleBarView tbv;
    private TextView speedup_tv_name, speedup_tv_number, speedup_tv_ram;
    private ProgressBar speedup_pb_ram;
    private Button speedup_bt_clear;
    private ToggleButton speedup_tbt;
    private ListView speed_up_lv;
    private CheckBox speedup_cb;
    private PhoneManager manager;
    private ActivityManager am;
    private PackageManager pm;
    private List<RunningAppInfo> systemList;
    private List<RunningAppInfo> userList;
    private SpeedUpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_up);
        findViewById();
        inData();
        init();
        RunningAppData();
        speedup_tbt.setChecked(true);
    }

    private void RunningAppData() {
        //判断手机版本
        if (Build.VERSION.SDK_INT < 21) {
            forBelow21();//SDK版本低于21（安卓5.0）
        } else {
            forOver21();//SDK版本不低于21
        }
    }

    private void forOver21() {
        //当前正在进行的进程
        List<AndroidAppProcess> appProcesses = ProcessManager.getRunningAppProcesses();//不同于5.0以下版本
        AndroidAppProcess runningAppProcessInfo;
        for (int i = 0; i < appProcesses.size(); i++) {
            runningAppProcessInfo = appProcesses.get(i);
            RunningAppInfo runningAppInfo = new RunningAppInfo();//实例化实体类
            //包名
            String packageName = runningAppProcessInfo.name;
            runningAppInfo.setPackageName(packageName);
            //应用大小
            Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
            int size = memoryInfo[0].getTotalPrivateDirty();
            runningAppInfo.setSize(size);
            try {
                //获得应用程序
                ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA
                        | PackageManager.GET_SHARED_LIBRARY_FILES
                        | PackageManager.MATCH_UNINSTALLED_PACKAGES);
                //应用名
                String lableName = (String) applicationInfo.loadLabel(pm);
                runningAppInfo.setLableName(lableName);
                //图标
                Drawable icon = applicationInfo.loadIcon(pm);
                runningAppInfo.setIcon(icon);

                //判断当前进程是系统进程还是用户进程
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {//系统进程
                    runningAppInfo.setSystem(true);
                    systemList.add(runningAppInfo);
                } else {//用户进程
                    runningAppInfo.setSystem(false);
                    userList.add(runningAppInfo);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void forBelow21() {
        //当前正在进行的进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = am.getRunningAppProcesses();
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo;
        for (int i = 0; i < runningAppProcessInfoList.size(); i++) {
            runningAppProcessInfo = runningAppProcessInfoList.get(i);
            RunningAppInfo runningAppInfo = new RunningAppInfo();//实例化实体类
            //包名
            String packageName = runningAppProcessInfo.processName;
            runningAppInfo.setPackageName(packageName);
            //应用大小
            Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
            int size = memoryInfo[0].getTotalPrivateDirty();
            runningAppInfo.setSize(size);
            try {
                //获得应用程序
                ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA
                        | PackageManager.GET_SHARED_LIBRARY_FILES
                        | PackageManager.MATCH_UNINSTALLED_PACKAGES);
                //应用名
                String lableName = (String) applicationInfo.loadLabel(pm);
                runningAppInfo.setLableName(lableName);
                //图标
                Drawable icon = applicationInfo.loadIcon(pm);
                runningAppInfo.setIcon(icon);

                //判断当前进程是系统进程还是用户进程
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {//系统进程
                    runningAppInfo.setSystem(true);
                    systemList.add(runningAppInfo);
                } else {//用户进程
                    runningAppInfo.setSystem(false);
                    userList.add(runningAppInfo);
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            //应用图标
        }
    }

    private void init() {
        am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        pm = getPackageManager();
        systemList = new ArrayList<>();
        userList = new ArrayList<>();
        adapter = new SpeedUpAdapter(this);
        speedup_tbt.setOnCheckedChangeListener(this);
        speedup_cb.setOnCheckedChangeListener(this);
        speedup_bt_clear.setOnClickListener(getOnClickListener);
    }

    private void findViewById() {
        tbv = (TitleBarView) findViewById(R.id.tbv);
        speedup_tv_name = (TextView) findViewById(R.id.speedup_tv_name);
        speedup_tv_number = (TextView) findViewById(R.id.speedup_tv_number);
        speedup_tv_ram = (TextView) findViewById(R.id.speedup_tv_ram);
        speedup_pb_ram = (ProgressBar) findViewById(R.id.speedup_pb_ram);
        speedup_bt_clear = (Button) findViewById(R.id.speedup_bt_clear);
        speedup_tbt = (ToggleButton) findViewById(R.id.speedup_tbt);
        speed_up_lv = (ListView) findViewById(R.id.speed_up_lv);
        speedup_cb = (CheckBox) findViewById(R.id.speedup_cb);
        tbv.initTitleBar(onLeftListener, R.mipmap.btn_homeasup_default, "手机加速");
        manager = PhoneManager.getPhoneManage(this);
    }

    private void inData() {
        String name = manager.getPhoneName1();//设备品牌
        String version = manager.getPhoneSystemVersion();//设备系统版本号
        String model = manager.getPhoneModelName(); //设备型号名称

        speedup_tv_name.setText(name);
        speedup_tv_number.setText(model + " Android" + version);

        long all = MemoryManager.getPhoneTotalRamMemory();//设备总运行内存
        long free = MemoryManager.getPhoneFreeRamMemory(this);//设备当前空闲运行内存
        long ram = all - free;

        speedup_tv_ram.setText("已用内存：" + CommonUtil.getFileSize(ram) + "/" + CommonUtil.getFileSize(all));
        speedup_pb_ram.setProgress((int) (ram * 100.0 / all));
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
            case R.id.speedup_tbt:
                //将全选框改为false
                speedup_cb.setChecked(false);
                //将item的复选框改为false
                List<RunningAppInfo> list=adapter.getList();//获取当前数据项
                if (list!=null)
                for (int i=0;i<list.size();i++){
                    list.get(i).setClear(false);
                }
                if (isChecked) {
                    adapter.add(systemList);
                } else {
                    adapter.add(userList);
                }
                speed_up_lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.speedup_cb:
                //获得数据源
                if (speedup_tbt.isChecked()) {
                    //当前的选中事件
                        if (isChecked) {
                            for (RunningAppInfo appInfo1 : systemList)//数组
                                appInfo1.setClear(true);
                        } else {
                            for (RunningAppInfo appInfo1 : systemList)
                                appInfo1.setClear(false);
                        }

                } else {
                        if (isChecked) {
                            for (RunningAppInfo appInfo2 : userList)
                                appInfo2.setClear(true);
                        } else {
                            for (RunningAppInfo appInfo2 : userList)
                                appInfo2.setClear(false);
                        }

                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
private View.OnClickListener getOnClickListener=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speedup_bt_clear:

                List<RunningAppInfo> list = adapter.getList();
                if (list != null) {
                    if (speedup_cb.isChecked()){
                        RunningAppManager.getManager(SpeedUpActivity.this).killAll();
                    }else {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).isClear())
                                am.killBackgroundProcesses(list.get(i).getPackageName());//清理进程
                        }
                    }

                    speedup_cb.setChecked(false);//将全选框改为false
                    adapter.clear();//清理旧数据
                    userList.clear();
                    systemList.clear();
                    RunningAppData();//获取新数据
                    //判断是系统进程还是用户进程
                    speedup_tbt.setChecked(speedup_tbt.isChecked());
//                    if (speedup_tbt.isChecked()) {
//                        adapter.add(systemList);
//                    } else {
//                        adapter.add(userList);
//                    }
                }
                speed_up_lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                break;
        }
    }
};


}
