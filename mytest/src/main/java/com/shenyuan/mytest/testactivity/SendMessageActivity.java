package com.shenyuan.mytest.testactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.shenyuan.mytest.R;
import com.shenyuan.mytest.adapter.TestListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class SendMessageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView textView;
    ListView listView;
    TestListViewAdapter adapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        listView= (ListView) findViewById(R.id.lv_test);
        list=new ArrayList<>();
        list.add("测试1");
        adapter=new TestListViewAdapter(this);
        adapter.setList(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,Test2Activity.class);
        startActivityForResult(intent,1);//requestCode
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
            switch (resultCode){
                case 2:
                    String str=data.getStringExtra("s");
                    adapter.clear();
                    list.add(str);
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
