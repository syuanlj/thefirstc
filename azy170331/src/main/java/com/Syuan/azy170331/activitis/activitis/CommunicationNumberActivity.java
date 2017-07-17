package com.Syuan.azy170331.activitis.activitis;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.adaper.CommunicationNumberAdapter;
import com.Syuan.azy170331.activitis.entity.CommunicationInfo;
import com.Syuan.azy170331.activitis.view.TitleBarView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommunicationNumberActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private TitleBarView tbv;
    private ListView lv;
    private CommunicationNumberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_number);
        findViewById();
        init();
    }

    private void init() {
        //得到传过来的intent对象
        Intent intent = getIntent();
        //得到并获得传值
//        Bundle bundle=intent.getExtras();
        String name = intent.getStringExtra("name");
        int id = intent.getIntExtra("idx", 0);
        tbv.initTitleBar(onLeftListener, R.mipmap.btn_homeasup_default, name);
        lv.setOnItemClickListener(this);
        //实例化，并关联适配器
        adapter = new CommunicationNumberAdapter(this);
        adapter.add(getData(id));
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void findViewById() {
        tbv = (TitleBarView) findViewById(R.id.tbv_operate);
        lv = (ListView) findViewById(R.id.lv_operate);
    }

    private View.OnClickListener onLeftListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
            finish();
        }
    };

    private List<CommunicationInfo> getData(int id) {
        List<CommunicationInfo> list = new ArrayList<>();
        String path = Environment.getDataDirectory() + "/data/" + this.getPackageName() + "/databases/commonnum.db";
        //打开指定路径下的数据库
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path, null);
        //查询数据库，得到指定数据
        Cursor cursor = db.query("table" + id, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CommunicationInfo info = new CommunicationInfo();
                info.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                info.setname(cursor.getString(cursor.getColumnIndex("name")));
                list.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final CommunicationInfo info = (CommunicationInfo) adapter.getItem(position);
        //得到一个AlertDialog静态内部类的Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //给Builder对象设置基本属性
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("警告")
                .setMessage("是否要拨打" + info.getname() + "电话？\n\nTel:" + info.getNumber())
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //声明一个Intent对象，并给一个拨打电话的action
                        Intent intent1 = new Intent(Intent.ACTION_CALL);
                        intent1.setData(Uri.parse("tel://" + info.getNumber()));
                        if (ActivityCompat.checkSelfPermission(CommunicationNumberActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent1);
                    }
                }).show();
    }


}
