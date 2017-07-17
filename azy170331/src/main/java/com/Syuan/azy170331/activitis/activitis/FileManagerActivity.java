package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.FileManagerAdapter;
import com.Syuan.azy170331.activitis.entity.FileManagerInfo;
import com.Syuan.azy170331.activitis.manager.MySearchFileTask;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.io.File;
import java.util.List;

public class FileManagerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, MySearchFileTask.FileSizeListener {
    private TitleBarView tbv;
    private TextView tv_file_manager;
    private ListView lv_file_manager;
    private FileManagerAdapter adapter;
    private MySearchFileTask mySearchFileTask;
    private long allSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        findViewById();
        init();
        getFileData();//获得数据
    }

    private void findViewById() {
        tbv = (TitleBarView) findViewById(R.id.tbv);
        tv_file_manager = (TextView) findViewById(R.id.tv_filemanager);
        lv_file_manager = (ListView) findViewById(R.id.lv_filemanager);
        tbv.initTitleBar(onLeftListener, R.mipmap.btn_homeasup_default, "文件管理");
        lv_file_manager.setOnItemClickListener(this);
    }

    private void init() {
        adapter = new FileManagerAdapter(this);
        adapter.add(new FileManagerInfo(0, "文件", true));
        adapter.add(new FileManagerInfo(0, "音频", true));
        adapter.add(new FileManagerInfo(0, "视频", true));
        adapter.add(new FileManagerInfo(0, "图片", true));
        adapter.add(new FileManagerInfo(0, "压缩包", true));
        adapter.add(new FileManagerInfo(0, "文安装包", true));
        adapter.add(new FileManagerInfo(0, "其他", true));
        lv_file_manager.setAdapter(adapter);
    }

    private View.OnClickListener onLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };



    //获得相关数据
    private void getFileData() {
        mySearchFileTask=new MySearchFileTask();
        File file= Environment.getExternalStorageDirectory();
        if (file.exists()){
            mySearchFileTask.execute(file);
        }
        mySearchFileTask.setFileSizeListener(this);
    }

    @Override
    public void searching(int position, long fileSize, long allSize) {
        this.allSize=allSize;
        FileManagerInfo fileManagerInfo=adapter.getItem(position);
        fileManagerInfo.setFileSize(fileSize);
        tv_file_manager.setText(CommonUtil.getFileSize(allSize));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finishSearch() {
        //搜索结束，将进度条隐藏，将箭头图片显示
        List<FileManagerInfo> list=adapter.getList();
        for (FileManagerInfo info:list){
            info.setLoading(false);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileManagerInfo info=adapter.getItem(position);//获得点击对象
        Intent intent=new Intent(this,File2Activity.class);
        Bundle bundle=new Bundle();
        bundle.putString("title",info.getType());
        bundle.putLong("fileTypeSize",info.getFileSize());
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode==2){
                Bundle bundle=data.getExtras();
                int position=bundle.getInt("position");
                long fileTypeSize=bundle.getLong("fileTypeSize");
                long delFileSize=bundle.getLong("delFileSize");
                //获得对象实体类
                FileManagerInfo info=adapter.getItem(position);
                info.setFileSize(fileTypeSize);//设置当前类型大小
                allSize-=delFileSize;
                tv_file_manager.setText(CommonUtil.getFileSize(allSize));//总大小
                adapter.notifyDataSetChanged();
            }
        }
    }
}
