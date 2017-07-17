package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.SoftManagerAdapter;
import com.Syuan.azy170331.activitis.manager.MemoryManager;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.utils.LogUtil;
import com.Syuan.azy170331.activitis.utils.ToastUtil;
import com.Syuan.azy170331.activitis.view.SoftmanagerDrawCircle;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

public class SoftManagerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TitleBarView tbv;
    private ListView lv_softmanager;
    private SoftManagerAdapter adapter;
    private ProgressBar pb_in,pb_out;
    private TextView tv_in,tv_out;
    private List<String> list;
    private SoftmanagerDrawCircle circle;
    private long inSize=0;//内置存储
    private long outSize=0;//外置存储
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_manager);
        findViewById();
        init();
    }

    private void findViewById() {
        tbv= (TitleBarView) findViewById(R.id.tbv);
        lv_softmanager= (ListView) findViewById(R.id.lv_softmanager);
        tbv.initTitleBar(onLeftListener,R.mipmap.btn_homeasup_default,"软件管理");
        circle= (SoftmanagerDrawCircle) findViewById(R.id.circle_softmanager);
        pb_in= (ProgressBar) findViewById(R.id.pb_in_softmanager);
        pb_out= (ProgressBar) findViewById(R.id.pb_out_softmanager);
        tv_in= (TextView) findViewById(R.id.tv_in_softmanager);
        tv_out= (TextView) findViewById(R.id.tv_out_softmanager);
        lv_softmanager.setOnItemClickListener(this);
    }

    private void init() {
        outSize=MemoryManager.getPhoneOutSDCardSize(this);//获得外置存储
        inSize= MemoryManager.getPhoneSelfSDCardSize();//获得内置存储
        circle.setAngle(360-360*outSize/inSize,360*outSize/inSize);//传人角度画圆
        pb_in.setProgress(1000000-(int) (1000000.0*MemoryManager.getPhoneSelfSDCardFreeSize()/inSize));
        tv_in.setText("可用："+CommonUtil.getFileSize(MemoryManager.getPhoneSelfSDCardFreeSize())+"/"+CommonUtil.getFileSize(inSize));
        if (outSize!=0){
            pb_out.setProgress((int) (100*(1-MemoryManager.getPhoneOutSDCardFreeSize(this)/outSize)));
            tv_out.setText("可用："+CommonUtil.getFileSize(outSize-MemoryManager.getPhoneOutSDCardFreeSize(this))+"/"+CommonUtil.getFileSize(outSize));
        }else {
            tv_out.setText("可用：0B/0B");
            pb_out.setProgress(100);
            ToastUtil.shortshow(this,"本机无外置内存");
        }
        adapter=new SoftManagerAdapter(this);
        list=new ArrayList<String>();
        list.add("所有软件");
        list.add("系统软件");
        list.add("用户软件");
        adapter.add(list);
        lv_softmanager.setAdapter(adapter);
    }
    private View.OnClickListener onLeftListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

//            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,AllSoftwareActivity.class);
        intent.putExtra("name",list.get(position));
        intent.putExtra("id",position);
        startActivity(intent);
    }
}
