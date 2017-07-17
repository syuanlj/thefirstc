package com.syuan.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.adapter.UserLoginLogAdapter;
import com.syuan.myapplication.entity.UserInfo;
import com.syuan.myapplication.utils.LoadImage;
import com.syuan.myapplication.view.TitleBarView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_pic;
    private TextView tv_name, tv_integral, tv_ties, tv_cover;
    private ListView listView;
    private Button bt_out;
    private UserInfo info;
    private UserLoginLogAdapter adapter;
    private PopupWindow popupWindow;
    private TitleBarView tvb;
    private Bitmap bitmap=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
    }

    private void init() {
        iv_pic = (ImageView) findViewById(R.id.user_iv_pic);
        tv_name = (TextView) findViewById(R.id.user_tv_name);
        tv_integral = (TextView) findViewById(R.id.user_tv_integral);
        tv_ties = (TextView) findViewById(R.id.user_tv_ties_number);
        tv_cover = (TextView) findViewById(R.id.user_tv_cover);
        listView = (ListView) findViewById(R.id.user_lv);
        bt_out = (Button) findViewById(R.id.user_bt_out);
        tvb= (TitleBarView) findViewById(R.id.user_title);

        iv_pic.setOnClickListener(this);
        bt_out.setOnClickListener(this);
        info = (UserInfo) getIntent().getSerializableExtra("userInfo");

        tv_name.setText(info.getUid());
        tv_integral.setText("积分：" + info.getIntegration());
        LoadImage.loadImage(this, info.getPortrait(), iv_pic);

        adapter = new UserLoginLogAdapter(this, info.getLoginlog());
        listView.setAdapter(adapter);
        tvb.initTitleBar(onLeftClickListener, R.mipmap.back, getString(R.string.user));

    }


    public View.OnClickListener onLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_left:
                    setResult(4);
                    finish();
//                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_iv_pic:
                showChooseWindow();
                break;
            case R.id.user_bt_out:
                getSharedPreferences("user", MODE_PRIVATE).edit().clear().commit();//清空
                setResult(3);
                finish();
                break;
            case R.id.tv_camera:
                popupWindow.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机
                startActivityForResult(intent, 13);
                break;
            case R.id.tv_photos:
                popupWindow.dismiss();
                selectPhoto();
                break;
        }
    }

    private void selectPhoto() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//打开的图片格式
        intent.putExtra("crop","true");//确认裁剪图片
        intent.putExtra("aspectx",1);//设置宽高比例
        intent.putExtra("aspecty",1);
        intent.putExtra("outputx",88);//设置宽高值
        intent.putExtra("outputy",88);
        intent.putExtra("return_data",true);//返回裁剪后的图片
        startActivityForResult(intent,13);
    }

    private void showChooseWindow() {
        //加载一个布局
        View view = LayoutInflater.from(this).inflate(R.layout.user_choose_window, null);
        TextView tv_camera, tv_photos;
        tv_camera = (TextView) view.findViewById(R.id.tv_camera);
        tv_photos = (TextView) view.findViewById(R.id.tv_photos);
        tv_camera.setOnClickListener(this);
        tv_photos.setOnClickListener(this);

        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(view);//加载布局

        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置高
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置宽

        popupWindow.setOutsideTouchable(true);//可外部点击
        popupWindow.setFocusable(true);//获得焦点

        popupWindow.showAtLocation(listView, Gravity.BOTTOM, 0, 0);//展示位置

        tv_cover.setVisibility(View.VISIBLE);//展示隐藏的背景
        tv_cover.setAlpha(0.5f);//设置透明度

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv_cover.setVisibility(View.INVISIBLE);//隐藏背景
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                iv_pic.setImageBitmap(bitmap);
                String url =info.getPortrait();
                //保存到本地的缓存目录
                File file = this.getCacheDir();
                if (!file.exists()) {
                    file.mkdir();
                }
                int a = url.lastIndexOf("/");
                String fileName = url.substring(a + 1);
                //输出流
                try {
                    OutputStream outputStream = new FileOutputStream(new File(file, fileName));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 30, outputStream);//压缩图片到某位置，100表示不压缩
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
