package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.File2Adaper;
import com.Syuan.azy170331.activitis.entity.FileInfo;
import com.Syuan.azy170331.activitis.manager.MySearchFileTask;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.utils.FileTypeUtil;
import com.Syuan.azy170331.activitis.utils.LogUtil;
import com.Syuan.azy170331.activitis.utils.ToastUtil;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

public class File2Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {
    private TitleBarView tbv;
    private TextView tv_number;
    private TextView tv_size;
    private ListView lv;
    private Button bt;
    private List<FileInfo> list;
    private File2Adaper adaper;
    private int position;
    private long delFileSize;
    private long delFileSize1;
    private long fileTypeSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file2);
        findViewById();
        init();

    }

    private void init() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        position=bundle.getInt("position");
        fileTypeSize=bundle.getLong("fileTypeSize");
        list= MySearchFileTask.getList().get(position);//获得文件集合
        tbv.initTitleBar(onLeftListener, R.mipmap.btn_homeasup_default,bundle.getString("title"));//获取标题，并设置标题栏
        tv_number.setText(list.size()+"");
        tv_size.setText(CommonUtil.getFileSize(fileTypeSize));
        adaper=new File2Adaper(this);
        lv.setAdapter(adaper);
        adaper.add(list);
    }

    private void findViewById() {
        tbv= (TitleBarView) findViewById(R.id.tbv_file2);
        tv_number= (TextView) findViewById(R.id.tv_file2_number);
        tv_size= (TextView) findViewById(R.id.tv_file2_size);
        lv= (ListView) findViewById(R.id.lv_file2);
        bt= (Button) findViewById(R.id.bt_file2_clean);
        bt.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        lv.setOnScrollListener(this);
    }
    private View.OnClickListener onLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            Bundle bundle=new Bundle();
            bundle.putInt("position",position);
            bundle.putLong("fileTypeSize",fileTypeSize);
            bundle.putLong("delFileSize",delFileSize1);
            intent.putExtras(bundle);
            setResult(2,intent);
            finish();
        }
    };

    @Override
    public void onClick(View v) {
        List<FileInfo> delList=new ArrayList<>();
        for (FileInfo info:adaper.getList()){
            if (info.isChecked()){
                delFileSize+=info.getFile().length();
                delList.add(info);//将需要删除的项加到删除队列
            }
        }
        for (FileInfo info:delList){
            info.getFile().delete();//删除文件
            adaper.getList().remove(info);//把删除的文件从当前list中移除
            MySearchFileTask.getList().get(position).remove(info);//从异步任务中的集合一处该文件
        }
        //当前文件大小发生变化
        fileTypeSize-=delFileSize;
        delFileSize1=delFileSize;
        delFileSize=0;
        tv_size.setText(CommonUtil.getFileSize(fileTypeSize));
        tv_number.setText(adaper.getList().size()+"");
        adaper.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       try {
           FileInfo info=adaper.getItem(position);//获得点击项
           Intent intent=new Intent(Intent.ACTION_VIEW);
           intent.setDataAndType(Uri.fromFile(info.getFile()), FileTypeUtil.getMIMEType(info.getFile()));
           startActivity(intent);
       }catch (Exception e){
           ToastUtil.shortshow(this,"此文件无法打开");
       }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            //快速滑动（手指离开屏幕）
            case SCROLL_STATE_FLING:
                LogUtil.e("快速滑动");
                adaper.setFling(true);
                break;

            //静止
            case SCROLL_STATE_IDLE:
                LogUtil.e("静止");
                adaper.setFling(false);
                break;

            //缓慢滑动（手指没有离开屏幕）
            case SCROLL_STATE_TOUCH_SCROLL:
                LogUtil.e("慢速滑动");
                adaper.setFling(false);
                break;
        }
        adaper.notifyDataSetChanged();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
