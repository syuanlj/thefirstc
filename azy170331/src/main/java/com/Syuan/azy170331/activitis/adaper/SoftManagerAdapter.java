package com.Syuan.azy170331.activitis.adaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.entity.CommunicationInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/5/4.
 */

public class SoftManagerAdapter extends BaseAdapter {
    private Context context;
    private List<String> infoList;

    public SoftManagerAdapter(Context context) {
        this.context = context;
        infoList=new ArrayList<>();
    }

    public void add(List<String> list){
        infoList=list;
    }
    @Override
    public int getCount() {
        if (infoList == null)
            return 0;
        else
            return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
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
            convertView= View.inflate(context, R.layout.softmanager_lv,null);
            viewHold.tv_name= (TextView) convertView.findViewById(R.id.softmanager_tv);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(infoList.get(position));
        return convertView;
    }

    private class ViewHold {
        TextView tv_name;
    }
}
