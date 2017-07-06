package com.shenyuan.mytest.testactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shenyuan.mytest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/28.
 */

public class DataAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater inflater;

    public DataAdapter(Context context) {
        inflater=LayoutInflater.from(context);
        list=new ArrayList();
    }

    public void add(String i){
        list.add(i);
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

            convertView=inflater.inflate(R.layout.adapter_item,null);
            viewHold.textView= (TextView) convertView.findViewById(R.id.tv_item);

            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.textView.setText(list.get(position));
        return convertView;
    }

    class ViewHold{
        private TextView textView;
    }
}
