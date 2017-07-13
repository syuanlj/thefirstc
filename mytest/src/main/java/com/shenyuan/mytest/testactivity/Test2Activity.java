package com.shenyuan.mytest.testactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shenyuan.mytest.R;

public class Test2Activity extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        button= (Button) findViewById(R.id.bt_test2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
//                Bundle bundle=new Bundle();
//                bundle.putString("s","测试2");
//                intent.putExtras(bundle);
                intent.putExtra("s","测试2");
                setResult(2,intent);
                finish();
            }
        });
    }
}
