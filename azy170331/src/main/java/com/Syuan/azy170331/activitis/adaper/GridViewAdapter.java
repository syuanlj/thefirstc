package com.Syuan.azy170331.activitis.adaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.entity.CommunicationInfo;
import com.Syuan.azy170331.activitis.sqlactivity.CommunicationSQLite;
import com.Syuan.azy170331.activitis.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/5/3.
 */

public class GridViewAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<CommunicationInfo> infoList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        infoList = new ArrayList<>();

    }

    public void add(List<CommunicationInfo> list){
        infoList = list;
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

        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.grid_view_item, null);
            viewHold.imageView = (ImageView) convertView.findViewById(R.id.image_item);
            viewHold.textView = (TextView) convertView.findViewById(R.id.text_item);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.textView.setText(infoList.get(position).getname());
        switch (position % 3) {
            case 0:
                viewHold.imageView.setBackgroundResource(R.drawable.notification_information_progress_red);
                break;
            case 1:
                viewHold.imageView.setBackgroundResource(R.drawable.notification_information_progress_yellow);
                break;
            case 2:
                viewHold.imageView.setBackgroundResource(R.drawable.notification_information_progress_green);
                break;
        }
//        viewHold.imageView.setOnClickListener(this);
        return convertView;
    }


    @Override

    public void onClick(View v) {
        ToastUtil.shortshow(context, "跳转监听");
//        context.startActivity(new Intent(context,OrderingNumberActivity.class));
    }

    private class ViewHold {
        ImageView imageView;
        TextView textView;
    }
}
