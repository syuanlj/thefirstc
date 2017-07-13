package com.shenyuan.mytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shenyuan.mytest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther sy on 2017/7/12 10:38.
 * @Email 893110793@qq.com
 */

public class TestListViewAdapter extends BaseAdapter {
    List<String> list;
    LayoutInflater inflater;
    Context context;

    public TestListViewAdapter(Context context) {
        inflater=LayoutInflater.from(context);

        this.context=context;
        list=new ArrayList<>();
    }

    public void setList(List<String> list1){
        list=list1;
    }
    public void clear(){
        list.clear();
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
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.adpater_test,null);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.tv_test_adapter);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(list.get(0));
//        Toast.makeText(context,"===tusi",Toast.LENGTH_SHORT).show();

        return convertView;
    }

    private class ViewHolder{
        TextView textView;
    }
}
