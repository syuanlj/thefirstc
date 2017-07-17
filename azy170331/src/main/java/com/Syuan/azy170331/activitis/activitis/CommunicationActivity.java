package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.GridViewAdapter;
import com.Syuan.azy170331.activitis.entity.CommunicationInfo;
import com.Syuan.azy170331.activitis.sqlactivity.CommunicationSQLite;
import com.Syuan.azy170331.activitis.view.TitleBarView;


public class CommunicationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TitleBarView tbv;
    private GridViewAdapter adapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        findViewById();
        init();
    }

    private void init() {
        tbv.initTitleBar(onLeftListener, R.mipmap.btn_homeasup_default, "通信大全");
        adapter = new GridViewAdapter(this);
        //获得通讯大全里的数据（数据库中的数据）
        adapter.add(CommunicationSQLite.getData(this));
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(this);//gridview监听
    }

    private void findViewById() {
        tbv = (TitleBarView) findViewById(R.id.tbv);
        gridView = (GridView) findViewById(R.id.gridview);
    }

    private View.OnClickListener onLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CommunicationInfo info= (CommunicationInfo) adapter.getItem(position);//获得具体项
//        Bundle bundle=new Bundle();
//        bundle.putString("name",info.getSort());
//        bundle.putInt("idx",info.getPictrue());
        Intent intent=new Intent(this,CommunicationNumberActivity.class);
        intent.putExtra("name",info.getname());
        intent.putExtra("idx",info.getid());
        startActivity(intent);

    }
}
