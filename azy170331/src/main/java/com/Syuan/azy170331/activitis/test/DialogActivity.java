package com.Syuan.azy170331.activitis.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;//向下兼容的架包
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.utils.ToastUtil;

public class DialogActivity extends Activity implements View.OnClickListener {

    private TextView tv_alert;
    private TextView tv_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById();
    }

    private void findViewById() {
        tv_alert= (TextView) findViewById(R.id.test_tv_dialog_alert);
        tv_progress= (TextView) findViewById(R.id.test_tv_dialog_progress);

        tv_alert.setOnClickListener(this);
        tv_progress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_tv_dialog_alert:
                //创建对话框
                AlertDialog.Builder alert=new AlertDialog.Builder(this);//通过AlertDialog.Builder实例化Alert对话框
                alert.setTitle("通知");//标题
                alert.setMessage("内容");
                alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.shortshow(DialogActivity.this,"确定");
                    }
                });//确定事件
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.shortshow(DialogActivity.this,"取消");
                    }
                });//取消事件
                alert.show();//展示对话框
                break;
            case R.id.test_tv_dialog_progress:
                ProgressDialog progressDia=new ProgressDialog(this);
                progressDia.setTitle("标题");
                progressDia.setMessage("loading");
//                progressDia.setCancelable(true);

                progressDia.show();
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                progressDia.dismiss();
                break;
        }
    }
}
