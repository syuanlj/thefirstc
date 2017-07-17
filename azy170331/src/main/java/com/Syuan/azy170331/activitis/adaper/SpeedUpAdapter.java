package com.Syuan.azy170331.activitis.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.entity.RunningAppInfo;
import com.Syuan.azy170331.activitis.utils.CommonUtil;

import java.util.List;
import java.util.ListResourceBundle;

import static android.graphics.Color.RED;

/**
 * Created by sy on 2017/5/2.
 */

public class SpeedUpAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private List<RunningAppInfo> appInfoList;
    private LayoutInflater inflater;

    public SpeedUpAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    public void add(List<RunningAppInfo> list){
        appInfoList=list;
    }

    public List<RunningAppInfo> getList(){
        return appInfoList;
    }

    public void clear(){
        appInfoList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return appInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfoList.get(position);
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
            convertView=inflater.inflate(R.layout.layout_speedup_lv,null);
            viewHold.speedup_adp_cb= (CheckBox) convertView.findViewById(R.id.speedup_adp_cb);
            viewHold.speedup_im_icon= (ImageView) convertView.findViewById(R.id.speedup_im_icon);
            viewHold.speedup_tv_info= (TextView) convertView.findViewById(R.id.speedup_tv_info);
            viewHold.speedup_tv_msg= (TextView) convertView.findViewById(R.id.speedup_tv_msg);
            viewHold.speedup_tv_system= (TextView) convertView.findViewById(R.id.speedup_tv_system);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }

        RunningAppInfo info= appInfoList.get(position);
        if (info.isSystem()){
            viewHold.speedup_tv_system.setText("系统进程");
            viewHold.speedup_tv_system.setTextColor(RED);
        }else {
            viewHold.speedup_tv_system.setText("用户进程");
        }

        viewHold.speedup_im_icon.setImageDrawable(info.getIcon());//应用图标
        viewHold.speedup_tv_msg.setText(CommonUtil.getFileSize(info.getSize()));
        viewHold.speedup_tv_info.setText(info.getLableName());
        viewHold.speedup_adp_cb.setTag(position);
        viewHold.speedup_adp_cb.setChecked(info.isClear());
        viewHold.speedup_adp_cb.setOnCheckedChangeListener(this);
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position= (int) buttonView.getTag();
        RunningAppInfo appInfo=appInfoList.get(position);
        appInfo.setClear(isChecked);
        notifyDataSetChanged();
    }

    private class ViewHold {
        ImageView speedup_im_icon;
        TextView speedup_tv_info;
        TextView speedup_tv_msg;
        TextView speedup_tv_system;
        CheckBox speedup_adp_cb;
    }
}
