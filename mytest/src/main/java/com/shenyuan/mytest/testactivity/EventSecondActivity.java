package com.shenyuan.mytest.testactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shenyuan.mytest.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_second);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.bt_event_second)
    public void onViewClicked() {
//        startActivity(new Intent(this,EventBusActivity.class));
        EventBus.getDefault().post(new Messge("hello!"));
        finish();

    }

}
