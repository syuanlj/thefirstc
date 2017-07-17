package com.syuan.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.syuan.myapplication.R;

public class LogoActivity extends AppCompatActivity {
    ImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        init();
    }
    private void init() {
        iv_logo= (ImageView) findViewById(R.id.iv_logo);
        Animation anim= AnimationUtils.loadAnimation(this,R.anim.apha);
        iv_logo.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(LogoActivity.this,MainActivity.class));
//                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }
}
