package com.syuan.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.entity.UserInfo;
import com.syuan.myapplication.test.BaseActivity;

import java.util.List;

/**
 * Created by sy on 2017/6/6.
 */

public class UserLoginLogAdapter extends BaseAdapter {
    private List<UserInfo.LoginLog> logList;
    private LayoutInflater inflater;

    public UserLoginLogAdapter(Context context,List<UserInfo.LoginLog> logList) {
        this.logList = logList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return logList.size();
    }

    @Override
    public Object getItem(int position) {
        return logList.get(position);
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
            convertView=inflater.inflate(R.layout.adapter_user,null);
            viewHold.tv_time= (TextView) convertView.findViewById(R.id.tv_user_time);
            viewHold.tv_site= (TextView) convertView.findViewById(R.id.tv_user_site);
            viewHold.tv_way= (TextView) convertView.findViewById(R.id.tv_user_way);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        UserInfo.LoginLog loginLog=logList.get(position);
        viewHold.tv_time.setText(loginLog.getTime().split(" ")[0]);
        viewHold.tv_site.setText(loginLog.getAdress());
        viewHold.tv_way.setText((loginLog.getDevice()==1)?"电脑":"手机");
        return convertView;
    }


    private class ViewHold {
        TextView tv_time,tv_site,tv_way;
    }
}
