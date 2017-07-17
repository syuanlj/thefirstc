package com.Syuan.azy170331.activitis.test;

import android.os.Handler;
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

public class Test2Activity extends AppCompatActivity implements View.OnClickListener, ScoreInterface {
    private Button bt_speed_up;
    private ClearView cv;
    private ImageView iv;
    private TextView tv;
    private RunningAppManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        init();
    }

    private void init() {
        bt_speed_up = (Button) findViewById(R.id.bt_speed_up);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        cv = (ClearView) findViewById(R.id.cv);
        bt_speed_up.setOnClickListener(this);
        iv.setOnClickListener(this);
        cv.setScoreInterface(this);
        manager = RunningAppManager.getManager(this);
        //初始化显示内存占用百分比
        long freeRamMemory = MemoryManager.getPhoneFreeRamMemory(this);
        long totalRamMemory = MemoryManager.getPhoneTotalRamMemory();
        long usedRamMemory=totalRamMemory-freeRamMemory;
        int persent=(int)(usedRamMemory*100/totalRamMemory);
        cv.setAngle(persent*360/100);

    }
    private static class MyHandler extends Handler{

    }

    @Override
    public void onClick(View view) {
        manager.killAll();
        long freeRamMemory = MemoryManager.getPhoneFreeRamMemory(this);
        long totalRamMemory = MemoryManager.getPhoneTotalRamMemory();
        long usedRamMemory=totalRamMemory-freeRamMemory;
        int persent=(int)(usedRamMemory*100/totalRamMemory);
        cv.setAngle(persent*360/100);

//        switch (view.getId()) {
//            case R.id.bt_speed_up:
//                cv.setAngle(233);
//                break;
//            case R.id.iv:
//                cv.setAngle(233);
//                break;
//        }


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
