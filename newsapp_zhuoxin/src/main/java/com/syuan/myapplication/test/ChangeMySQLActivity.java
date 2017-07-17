package com.syuan.myapplication.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.syuan.myapplication.R;

public class ChangeMySQLActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_new_sqlite, bt_add_sqlite, bt_delete_sqlite, bt_change_sqlite, bt_search_sqlite;
    private int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_my_sql);
        findViewById();
        init();
    }
    private void init() {
        bt_new_sqlite.setOnClickListener(this);
        bt_add_sqlite.setOnClickListener(this);
        bt_delete_sqlite.setOnClickListener(this);
        bt_change_sqlite.setOnClickListener(this);
        bt_search_sqlite.setOnClickListener(this);
    }
    private void findViewById() {
        bt_new_sqlite = (Button) findViewById(R.id.bt_new_sqlite);
        bt_add_sqlite = (Button) findViewById(R.id.bt_add_sqlite);
        bt_delete_sqlite = (Button) findViewById(R.id.bt_delete_sqlite);
        bt_change_sqlite = (Button) findViewById(R.id.bt_change_sqlite);
        bt_search_sqlite = (Button) findViewById(R.id.bt_search_sqlite);


    }
    @Override
    public void onClick(View v) {
        MySQLiteTest mySQLiteTest=MySQLiteTest.getMySQLiteTest(this);
        switch (v.getId()){
            case R.id.bt_new_sqlite:
                mySQLiteTest.getWritableDatabase();//在数据库中创建表格
                break;
            case R.id.bt_add_sqlite:
                SQLiteDatabase db = mySQLiteTest.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("a", count++);
                values.put("b", "false");
                values.put("c", "miasd");
                db.insert("abc", null, values);
                values.clear();
                db.close();
                break;
            case R.id.bt_delete_sqlite:
                SQLiteDatabase db1 = mySQLiteTest.getWritableDatabase();
                //表名，table名，具体的一或多项
                db1.delete("abc","a=?",new String[]{"2"});
                db1.close();
                break;
            case R.id.bt_change_sqlite:
                SQLiteDatabase db2 = mySQLiteTest.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("b", "true");
                db2.update("abc",values2,"b=?",new String[]{"false"});
                db2.close();
                break;
            case R.id.bt_search_sqlite:
                SQLiteDatabase db3 = mySQLiteTest.getWritableDatabase();
                Cursor cursor = db3.query("abc", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        int a = cursor.getInt(cursor.getColumnIndex("a"));
                        String b = cursor.getString(cursor.getColumnIndex("b"));
                        String c = cursor.getString(cursor.getColumnIndex("c"));

                        System.out.println("SQLite a=" + a);
                        System.out.println("SQLite b=" + b);
                        System.out.println("SQLite c=" + c);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db3.close();
                break;
        }
    }
}
