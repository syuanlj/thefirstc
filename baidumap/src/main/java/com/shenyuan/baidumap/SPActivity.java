package com.shenyuan.baidumap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SPActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name_et,psw_et;
    Button bt;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        name_et= (EditText) findViewById(R.id.et_name);
        psw_et= (EditText) findViewById(R.id.et_psw);
        bt= (Button) findViewById(R.id.bt_sp);
        sp=getSharedPreferences("user", Context.MODE_PRIVATE);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor=sp.edit();
        String name=name_et.getText().toString();
        String password=psw_et.getText().toString();
        editor.putString("name",name);
        editor.putString("password",password);
        editor.apply();
        startActivity(new Intent(this,GetSPActivity.class));
    }
}
