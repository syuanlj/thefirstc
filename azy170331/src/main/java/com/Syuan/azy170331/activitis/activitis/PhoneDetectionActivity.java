package com.Syuan.azy170331.activitis.activitis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.PhoneMsgAdapter;
import com.Syuan.azy170331.activitis.entity.PhoneInfo;
import com.Syuan.azy170331.activitis.manager.MemoryManager;
import com.Syuan.azy170331.activitis.manager.PhoneManager;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

public class PhoneDetectionActivity extends AppCompatActivity {
    private TitleBarView tbv;
    private View phone_v;
    private ProgressBar pb_progressBar;
    private TextView phone_tv;
    private int maxBattery,currentBattery,current100;
    private double temperatureBattery;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout ll;
    private ListView lv;
    private PhoneMsgAdapter adapter;
    private List<PhoneInfo> infoList;
    private String info,msg;
    private Drawable drawable;
    private PhoneManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_detection);
        init();


    }

    private void init() {
        tbv= (TitleBarView) findViewById(R.id.tbv);
        phone_v=findViewById(R.id.phone_v);
        pb_progressBar= (ProgressBar) findViewById(R.id.pb_progressbar);
        phone_tv= (TextView) findViewById(R.id.phone_tv);
        ll= (LinearLayout) findViewById(R.id.ll);
        lv= (ListView) findViewById(R.id.lv);
        tbv.initTitleBar(onLeftListener,R.mipmap.btn_homeasup_default,"手机检测");//设置titleBar
        //注册电池电量广播接收器（放在空间findView后面）
        broadcastReceiver=new BatteryBroadcastReceiver();//实例化电池广播接收器
        IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);//筛选
        registerReceiver(broadcastReceiver,filter);//动态注册广播接收器
        ll.setOnClickListener(onLinearLayout);

        infoList=new ArrayList<>();
        adapter=new PhoneMsgAdapter(this,infoList);
        lv.setAdapter(adapter);
        manager=PhoneManager.getPhoneManage(this);

        info="设备名称"+manager.getPhoneName1();
        msg="系统版本"+manager.getPhoneSystemVersion();
        drawable=getResources().getDrawable(R.mipmap.setting_info_icon_root);
        PhoneInfo phoneInfo=new PhoneInfo(drawable,info,msg);
        infoList.add(phoneInfo);

        info="全部运行内存"+ CommonUtil.getFileSize(MemoryManager.getPhoneTotalRamMemory());
        msg="剩余运行内存"+CommonUtil.getFileSize(MemoryManager.getPhoneFreeRamMemory(this));
        drawable=getResources().getDrawable(R.mipmap.setting_info_icon_space);
        PhoneInfo phoneInfo1=new PhoneInfo(drawable,info,msg);
        infoList.add(phoneInfo1);

        info="cpu名称"+manager.getPhoneCpuName();
        msg="cpu数量："+manager.getPhoneCpuNumber();
        drawable=getResources().getDrawable(R.mipmap.setting_info_icon_cpu);
        PhoneInfo phoneInfo2=new PhoneInfo(drawable,info,msg);
        infoList.add(phoneInfo2);

        info="手机分辨率"+manager.getResolution();
        msg="相机分辨率："+manager.getMaxPhotoSize();
        drawable=getResources().getDrawable(R.mipmap.setting_info_icon_camera);
        PhoneInfo phoneInfo3=new PhoneInfo(drawable,info,msg);
        infoList.add(phoneInfo3);

        info="基带版本"+manager.getPhoneSystemBasebandVersion();
        msg="是否ROOT："+manager.isRoot();
        drawable=getResources().getDrawable(R.mipmap.setting_info_icon_root);
        PhoneInfo phoneInfo4=new PhoneInfo(drawable,info,msg);
        infoList.add(phoneInfo4);

    }

    private View.OnClickListener onLinearLayout=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll:
                    showBatteryMessage();
                    break;
            }
        }
    };
    private View.OnClickListener onLeftListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };

    class BatteryBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
                Bundle bundle=intent.getExtras();
                maxBattery=bundle.getInt(BatteryManager.EXTRA_SCALE);//电池总量
                currentBattery=bundle.getInt(BatteryManager.EXTRA_LEVEL);//当前电量
                temperatureBattery=bundle.getInt(BatteryManager.EXTRA_TEMPERATURE)/10;//电池温度
                pb_progressBar.setMax(maxBattery);
                pb_progressBar.setProgress(currentBattery);
                current100=currentBattery*100/maxBattery;
                phone_tv.setText(current100+"%");
                if (current100==100){
                    phone_v.setBackgroundColor(Color.rgb(255,193,0));//#FFC100
                }
            }
        }
    }

    /**
     * 电池信息对话框
     */
    private void showBatteryMessage(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);//实例化对话框对象
        CharSequence[] setItems={"电池总量："+maxBattery,
                "当前电量："+currentBattery,"电池温度："+temperatureBattery+"℃"};
        builder.setTitle("电池状态信息");
        builder.setItems(setItems,null);
        builder.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);//注销动态注册的广播接收器
    }
}
