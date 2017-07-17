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
import com.Syuan.azy170331.activitis.entity.GarbageInfo;
import com.Syuan.azy170331.activitis.entity.RunningAppInfo;
import com.Syuan.azy170331.activitis.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.RED;

/**
 * Created by sy on 2017/5/2.
 */

public class GarbageAdapter extends BaseAdapter {
    private Context context;
    private List<GarbageInfo> appInfoList;
    private LayoutInflater inflater;

    public GarbageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        appInfoList=new ArrayList<>();
    }

    public void add(List<GarbageInfo> list) {
        appInfoList = list;
    }

    public List<GarbageInfo> getList() {
        return appInfoList;
    }

    public void clear() {
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
        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.layout_garbage_lv, null);
            viewHold.garbage_adp_cb = (CheckBox) convertView.findViewById(R.id.garbage_adp_cb);
            viewHold.garbage_im_icon = (ImageView) convertView.findViewById(R.id.garbage_im_icon);
            viewHold.garbage_tv_msg = (TextView) convertView.findViewById(R.id.garbage_tv_msg);
            viewHold.garbage_tv_system = (TextView) convertView.findViewById(R.id.garbage_tv_system);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }

        final GarbageInfo info = appInfoList.get(position);

        viewHold.garbage_im_icon.setImageDrawable(info.getIcon());//应用图标
        viewHold.garbage_tv_system.setText(CommonUtil.getFileSize(info.getSize()));
        viewHold.garbage_tv_msg.setText(info.getName());
        viewHold.garbage_adp_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                info.setClear(isChecked);
            }
        });
        viewHold.garbage_adp_cb.setChecked(info.isClear());

        return convertView;
    }
    private class ViewHold {
        ImageView garbage_im_icon;
        TextView garbage_tv_msg;
        TextView garbage_tv_system;
        CheckBox garbage_adp_cb;
    }
}
