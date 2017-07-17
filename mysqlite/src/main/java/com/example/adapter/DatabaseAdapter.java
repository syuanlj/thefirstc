package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enity.DatabaseInfo;
import com.example.mysqlite.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/19.
 */

public class DatabaseAdapter extends BaseAdapter {
    private List<DatabaseInfo> list;
    private LayoutInflater inflater;

    public DatabaseAdapter(Context context) {
        list=new ArrayList<>();
        inflater=LayoutInflater.from(context);
    }

    public void add(List<DatabaseInfo> list1){
        list=list1;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoider viewHoider;
        if (convertView==null){
            viewHoider=new ViewHoider();
            convertView=inflater.inflate(R.layout.adapter_database,null);
            viewHoider.a= (TextView) convertView.findViewById(R.id.a_adapter);
            viewHoider.b= (TextView) convertView.findViewById(R.id.b_adapter);
            viewHoider.c= (TextView) convertView.findViewById(R.id.c_adapter);
            viewHoider.d= (TextView) convertView.findViewById(R.id.d_adapter);
            convertView.setTag(viewHoider);
        }else {
            viewHoider= (ViewHoider) convertView.getTag();
        }
        DatabaseInfo info=list.get(position);
        viewHoider.a.setText(info.getA()+"");
        viewHoider.b.setText(info.getB()+"");
        viewHoider.c.setText(info.getC()+"");
        viewHoider.d.setText(info.getD()+"");
        return convertView;
    }
    private class ViewHoider{
        TextView a,b,c,d;
    }
}
