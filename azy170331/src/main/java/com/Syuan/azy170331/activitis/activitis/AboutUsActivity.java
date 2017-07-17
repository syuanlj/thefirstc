package com.Syuan.azy170331.activitis.activitis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.view.TitleBarView;

public class AboutUsActivity extends AppCompatActivity {
    TitleBarView tvb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        tvb = (TitleBarView) findViewById(R.id.tvb);
        tvb.initTitleBar(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                finish();
            }
        }, R.mipmap.btn_homeasup_default, "关于我们");
    }
}
