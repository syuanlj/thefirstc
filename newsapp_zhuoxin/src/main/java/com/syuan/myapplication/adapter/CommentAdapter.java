package com.syuan.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.entity.CommentInfo;
import com.syuan.myapplication.entity.CommentInfo;
import com.syuan.myapplication.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/8.
 */

public class CommentAdapter extends BaseAdapter {

    private List<CommentInfo> list;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void clear(){
        list.clear();
    }


    public void setList(List<CommentInfo> list) {
        this.list = list;
    }

    public List<CommentInfo> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CommentInfo getItem(int position) {
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
            convertView=View.inflate(context, R.layout.adapter_comment,null);
            viewHold.iv= (ImageView) convertView.findViewById(R.id.comment_iv_icon);
            viewHold.tv_title= (TextView) convertView.findViewById(R.id.comment_tv_title);
            viewHold.tv_content= (TextView) convertView.findViewById(R.id.comment_tv_content);
            viewHold.tv_time= (TextView) convertView.findViewById(R.id.comment_tv_time);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        CommentInfo info=list.get(position);
        viewHold.tv_title.setText(info.getUid());
        viewHold.tv_content.setText(info.getContent());
        viewHold.tv_time.setText(info.getStamp());

        //调用加载图片的方法
        LoadImage.loadImage(context,info.getPortrait(),viewHold.iv);
        return convertView;
    }

    private class ViewHold {
        ImageView iv;
        TextView tv_title,tv_content,tv_time;
    }
}
