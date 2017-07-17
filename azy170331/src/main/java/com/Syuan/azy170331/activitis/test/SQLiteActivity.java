package com.Syuan.azy170331.activitis.test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.sqlactivity.MySQLiteHelp;

public class SQLiteActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_new_sqlite, bt_add_sqlite, bt_delete_sqlite, bt_change_sqlite, bt_search_sqlite;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
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
        MySQLiteHelp sqlhelp = new MySQLiteHelp(this);
        switch (v.getId()) {
            case R.id.bt_new_sqlite:
                sqlhelp.getWritableDatabase();//在数据库中创建表格
                break;
            case R.id.bt_add_sqlite:
                SQLiteDatabase db = sqlhelp.getWritableDatabase();
                ContentValues valus = new ContentValues();
                valus.put("name", "猫");
                valus.put("type", "宠物");
                valus.put("price", 500);
                valus.put("number", count++);
                db.insert("cup", null, valus);
                valus.clear();
                valus.put("name", "狗");
                valus.put("type", "宠物");
                valus.put("price", 1500);
                valus.put("number", count++);
                db.insert("cup", null, valus);
                valus.clear();
                valus.put("name", "藏獒");
                valus.put("type", "动物");
                valus.put("price", 50000);
                valus.put("number", count++);
                db.insert("cup", null, valus);
                valus.clear();
                db.close();
                break;
            case R.id.bt_delete_sqlite:
                SQLiteDatabase db1 = sqlhelp.getWritableDatabase();
                //表名，table名，具体的一或多项
                db1.delete("cup", "name=?", new String[]{"猫"});
                db1.close();
                break;
            case R.id.bt_change_sqlite:
                SQLiteDatabase db2 = sqlhelp.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("name", "狗熊");
                db2.update("cup", values2, "name=?", new String[]{"猫"});
                db2.close();
                break;
            case R.id.bt_search_sqlite:
                SQLiteDatabase db3 = sqlhelp.getWritableDatabase();
                Cursor cursor = db3.query("cup", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        int price = cursor.getInt(cursor.getColumnIndex("price"));
                        int number = cursor.getInt(cursor.getColumnIndex("number"));

                        System.out.println("SQLite name=" + name);
                        System.out.println("SQLite type=" + type);
                        System.out.println("SQLite price=" + price);
                        System.out.println("SQLite number=" + number);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                db3.close();
                break;
        }

    }
}
