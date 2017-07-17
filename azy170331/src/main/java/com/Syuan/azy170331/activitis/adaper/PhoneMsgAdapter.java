package com.Syuan.azy170331.activitis.adaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.entity.PhoneInfo;

import java.util.List;

/**
 * Created by sy on 2017/4/25.
 */

public class PhoneMsgAdapter extends BaseAdapter {
    List<PhoneInfo> list;
    Context context;

    public PhoneMsgAdapter(Context context,List<PhoneInfo> list) {
        this.list = list;
        this.context=context;
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
        ViewHold viewHold;
        if (convertView==null){
            viewHold=new ViewHold();
            convertView=View.inflate(context, R.layout.layout_phone_msg,null);
            viewHold.im_icon= (ImageView) convertView.findViewById(R.id.im_icon);
            viewHold.tv_info= (TextView) convertView.findViewById(R.id.tv_info);
            viewHold.tv_msg= (TextView) convertView.findViewById(R.id.tv_msg);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }

        //传具体的参数
        viewHold.tv_msg.setText(list.get(position).getMsg());
        viewHold.tv_info.setText(list.get(position).getInfo());
        viewHold.im_icon.setImageDrawable(list.get(position).getDrawable());

        switch (position%3){
            case 0:
                viewHold.im_icon.setBackgroundResource(R.drawable.notification_information_progress_green);
                break;
            case 1:
                viewHold.im_icon.setBackgroundResource(R.drawable.notification_information_progress_yellow);
                break;
            case 2:
                viewHold.im_icon.setBackgroundResource(R.drawable.notification_information_progress_red);
                break;
        }
        return convertView;
    }

    private class ViewHold{
        ImageView im_icon;
        TextView tv_info;
        TextView tv_msg;
    }
}
