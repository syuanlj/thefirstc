package com.syuan.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.syuan.videozx.R;

public class VideoTestActivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);
        videoView= (VideoView) findViewById(R.id.vv_test);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse("http://mvvideo11.meitudata.com/594624d04791c9826.mp4"));
        videoView.requestFocus();
    }
}
