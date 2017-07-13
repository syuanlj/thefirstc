package com.shenyuan.mytest.testactivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.shenyuan.mytest.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Manifest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CameraAlbumActivity extends AppCompatActivity {

    @BindView(R.id.camera_bt)
    Button cameraBt;
    @BindView(R.id.camera_iv)
    ImageView cameraIv;

    private Unbinder bk;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    private MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_album);
        bk = ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }else {
            initMedioPlayer();
        }
    }

    private void initMedioPlayer() {
        File file=new File(Environment.getExternalStorageDirectory(),"music.mp3");
        try {
            mediaPlayer.setDataSource(file.getPath());
//            指定音频文件的路径
            mediaPlayer.prepare();
//            让mediaPlayer进入到准备状态
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case   1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initMedioPlayer();
                }else {
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @OnClick({R.id.camera_bt, R.id.camera_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.camera_bt:
//                创建一个File存放拍照保存下的照片
                File outputImage = new File(getExternalCacheDir() + "/output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(this,
                            "com.example.cameraalbumtest.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }
//                启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
//                    将拍的照片显示出来
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        cameraIv.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        bk.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.play_bt, R.id.pause_bt, R.id.stop_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play_bt:
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                break;

            case R.id.pause_bt:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case R.id.stop_bt:
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                break;
        }
    }
}
