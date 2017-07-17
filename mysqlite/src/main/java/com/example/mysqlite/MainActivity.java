package com.example.mysqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.DatabaseAdapter;
import com.example.db.ControlMyDatabase;
import com.example.enity.DatabaseInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView insert, delete, change;
    private ListView lv;
    private List<DatabaseInfo> list;
    private DatabaseAdapter adapter;
    private ControlMyDatabase control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
    }

    private void findViewById() {
        insert= (TextView) findViewById(R.id.insert);
        delete= (TextView) findViewById(R.id.delete);
        change= (TextView) findViewById(R.id.change);
        lv= (ListView) findViewById(R.id.list);

        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        change.setOnClickListener(this);

        list=new ArrayList<>();
        control=new ControlMyDatabase(this);
        adapter=new DatabaseAdapter(this);

        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert:
                control.insertData();
                break;
            case R.id.delete:
                DatabaseInfo info=new DatabaseInfo(1,1,1,1);
                control.delData(info);
                break;
            case R.id.change:
                DatabaseInfo info1=new DatabaseInfo(1,1,1,1);
                control.changeData(info1);
                break;
        }
        list=control.getDataList();
        adapter.add(list);
        adapter.notifyDataSetChanged();
    }
}
