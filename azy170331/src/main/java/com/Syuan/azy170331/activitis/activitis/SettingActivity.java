package com.Syuan.azy170331.activitis.activitis;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.view.TitleBarView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TitleBarView tbv;
    private RelativeLayout rl_help, rl_share, rl_about, rl_suggestion, rl_update;
    private ToggleButton tb_manager, tb_start, tb_push;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById();
        init();
    }

    private void findViewById() {
        rl_about = (RelativeLayout) findViewById(R.id.rl_about_set);
        rl_help = (RelativeLayout) findViewById(R.id.rl_help_set);
        rl_share = (RelativeLayout) findViewById(R.id.rl_share_set);
        rl_suggestion = (RelativeLayout) findViewById(R.id.rl_suggestion_set);
        rl_update = (RelativeLayout) findViewById(R.id.rl_update_set);
        tb_manager = (ToggleButton) findViewById(R.id.tb_setting_notification_manager);
        tb_push = (ToggleButton) findViewById(R.id.tb_setting_push_notification);
        tb_start = (ToggleButton) findViewById(R.id.tb_setting_start);
        tbv = (TitleBarView) findViewById(R.id.tbv);
        tbv.initTitleBar(onLeftClickListener, R.mipmap.btn_homeasup_default, "设置");
        rl_help.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        tb_manager.setOnCheckedChangeListener(this);
    }

    private void init() {

    }

    public View.OnClickListener onLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_left:
                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    finish();

                    break;
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_help_set:
                Intent intent = new Intent(this, LeadActivity.class);
                intent.putExtra("isSetActivity", true);
                startActivity(intent);
                break;
            case R.id.rl_about_set:
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            case R.id.rl_share_set:
                break;
            case R.id.rl_suggestion_set:
                break;
            case R.id.rl_update_set:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tb_setting_notification_manager:
                //设置通知
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
                Intent intent =new Intent(this,MainActivity.class);
                PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
                builder.setContentTitle("通知")
                        .setContentText("设置界面的通知消息按钮")
                        .setWhen(System.currentTimeMillis())//通知时间
                        .setSmallIcon(R.mipmap.icon_app)//通知栏的小图标
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))//拉开通知栏的大图标
                        .setContentIntent(pi)//跳转到指定界面
                        .setAutoCancel(true)//点击通知的时候，自动取消
                        .build();
                manager.notify(1,builder.build());
                break;
            case R.id.tb_setting_push_notification:
                break;
            case R.id.tb_setting_start:
                break;
        }
    }
}
