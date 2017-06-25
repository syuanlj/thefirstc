package com.syuan.videozx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.syuan.adapter.VideoAdapter;
import com.syuan.enity.VideoInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcv;
    private VideoAdapter adaper;
    private List<VideoInfo> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        toolbar= (Toolbar) findViewById(R.id.main_tbr);
        rcv= (RecyclerView) findViewById(R.id.rcv_main);

        list=new ArrayList<>();
        //获取视频信息
        getVideo();
        adaper=new VideoAdapter(this);

        setSupportActionBar(toolbar);
    }

    private void getVideo() {

    }
}
