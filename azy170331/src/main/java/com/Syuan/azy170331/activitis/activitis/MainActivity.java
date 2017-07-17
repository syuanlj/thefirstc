package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.manager.RunningAppManager;
import com.Syuan.azy170331.activitis.myInterface.ScoreInterface;
import com.Syuan.azy170331.activitis.manager.MemoryManager;
import com.Syuan.azy170331.activitis.view.ClearView;
import com.Syuan.azy170331.activitis.view.TitleBarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ScoreInterface {
    private Button bt_speed_up;
    private ClearView cv;
    private ImageView iv;
    private TextView tv;
    private RunningAppManager manager;
    private TitleBarView tvb;
    private TextView tv_rocket;
    private TextView tv_softmgr;
    private TextView tv_phonemgr;
    private TextView tv_telmgr;
    private TextView tv_filemgr;
    private TextView tv_sdclean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        init();
    }

    public void findViewById() {
        bt_speed_up = (Button) findViewById(R.id.bt_speed_up);
        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);
        cv = (ClearView) findViewById(R.id.cv);
        tvb = (TitleBarView) findViewById(R.id.tvb);
        tv_rocket = (TextView) findViewById(R.id.tv_rocket);
        tv_softmgr = (TextView) findViewById(R.id.tv_softmgr);
        tv_phonemgr = (TextView) findViewById(R.id.tv_phonemgr);
        tv_telmgr = (TextView) findViewById(R.id.tv_telmgr);
        tv_filemgr = (TextView) findViewById(R.id.tv_filemgr);
        tv_sdclean = (TextView) findViewById(R.id.tv_sdclean);
        bt_speed_up.setOnClickListener(myOnClickListener);
        iv.setOnClickListener(myOnClickListener);
        cv.setScoreInterface(this);
        manager = RunningAppManager.getManager(this);
        tv_rocket.setOnClickListener(this);
        tv_softmgr.setOnClickListener(this);
        tv_phonemgr.setOnClickListener(this);
        tv_telmgr.setOnClickListener(this);
        tv_filemgr.setOnClickListener(this);
        tv_sdclean.setOnClickListener(this);
    }


    private void init() {
        tvb.initTitleBar(onLeftClickListener, R.mipmap.azy, "手机助手", onRightClickListener, R.mipmap.ic_child_configs);

    }

    public View.OnClickListener onLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_left:
                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
//                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    break;
            }

        }
    };
    public View.OnClickListener onRightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_right:
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
//                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    break;
            }
        }
    };

    public View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            manager.killAll();
            long freeRamMemory = MemoryManager.getPhoneFreeRamMemory(MainActivity.this);
            long totalRamMemory = MemoryManager.getPhoneTotalRamMemory();
            long usedRamMemory = totalRamMemory - freeRamMemory;
            int persent = (int) (usedRamMemory * 100 / totalRamMemory);

            switch (v.getId()) {
                case R.id.bt_speed_up:
                    cv.setAngle(persent * 360 / 100);
                    break;
                case R.id.iv:
                    cv.setAngle(persent * 360 / 100);
                    break;

            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //初始化显示内存占用百分比
        long freeRamMemory = MemoryManager.getPhoneFreeRamMemory(MainActivity.this);
        long totalRamMemory = MemoryManager.getPhoneTotalRamMemory();
        long usedRamMemory = totalRamMemory - freeRamMemory;
        int persent = (int) (usedRamMemory * 100 / totalRamMemory);
        cv.setAngle(persent * 360 / 100);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_rocket:
                startActivity(new Intent(MainActivity.this, SpeedUpActivity.class));
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_softmgr:
                startActivity(new Intent(MainActivity.this, SoftManagerActivity.class));
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_phonemgr:
                startActivity(new Intent(MainActivity.this, PhoneDetectionActivity.class));
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_telmgr:
                startActivity(new Intent(MainActivity.this, CommunicationActivity.class));
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_filemgr:
                startActivity(new Intent(MainActivity.this, FileManagerActivity.class));
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.tv_sdclean:
                startActivity(new Intent(MainActivity.this, GarbageCollectionActivity.class));
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
        }
    }

    @Override
    public void getScore(final float score) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText((int) score + "");
            }
        });
    }
}
