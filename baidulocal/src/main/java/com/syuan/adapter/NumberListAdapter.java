package com.syuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syuan.baidulocal.R;
import com.syuan.enity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/21.
 */

public class NumberListAdapter extends BaseAdapter{
    private List<Person> list;
    private LayoutInflater inflater;

    public NumberListAdapter(Context context) {
        inflater=LayoutInflater.from(context);
        list=new ArrayList<>();
    }
    public void add(List<Person> list){
        this.list=list;
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
        ViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ViewHold();
            convertView=inflater.inflate(R.layout.adapter_number,null);
            viewHold.name= (TextView) convertView.findViewById(R.id.name_adapter);
            viewHold.number= (TextView) convertView.findViewById(R.id.number_adapter);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        Person info=list.get(position);
        viewHold.name.setText(info.getName());
        viewHold.number.setText(info.getNumber());
        return convertView;
    }
    private class ViewHold{
        TextView name,number;
    }
}
