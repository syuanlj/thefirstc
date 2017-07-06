package com.shenyuan.mytest.testactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.shenyuan.mytest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EventBusActivity extends AppCompatActivity {

    @BindView(R.id.tv_event)
    TextView tvEvent;
    Unbinder n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        n=ButterKnife.bind(this);
//        tvEvent= (TextView) findViewById(R.id.tv_event);

        EventBus.getDefault().register(this);//注册
    }

    @OnClick(R.id.bt_event)
    public void onViewClicked() {
        startActivity(new Intent(this,EventSecondActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMsg(final Messge message){
        tvEvent.setText(message.getMsg());
        Toast.makeText(this,message.getMsg(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
        n.unbind();
    }
}
