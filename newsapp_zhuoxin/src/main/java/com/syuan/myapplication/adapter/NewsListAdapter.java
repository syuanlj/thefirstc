package com.syuan.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.entity.NewsListInfo;
import com.syuan.myapplication.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/8.
 */

public class NewsListAdapter extends BaseAdapter {

    private List<NewsListInfo> list;
    private Context context;

    public NewsListAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void clear(){
        list.clear();
    }


    public void setList(List<NewsListInfo> list) {
        this.list = list;
    }

    public List<NewsListInfo> getList() {
        return list;
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
            convertView=View.inflate(context, R.layout.adapter_newslist,null);
            viewHold.iv= (ImageView) convertView.findViewById(R.id.news_iv_icon);
            viewHold.tv_title= (TextView) convertView.findViewById(R.id.news_tv_title);
            viewHold.tv_content= (TextView) convertView.findViewById(R.id.news_tv_content);
            viewHold.tv_time= (TextView) convertView.findViewById(R.id.news_tv_time);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        NewsListInfo info=list.get(position);
        viewHold.tv_title.setText(info.getTitle());
        viewHold.tv_content.setText(info.getSummary());
        viewHold.tv_time.setText(info.getStamp());

        //调用加载图片的方法
        LoadImage.loadImage(context,info.getIcon(),viewHold.iv);
        return convertView;
    }

    private class ViewHold {
        ImageView iv;
        TextView tv_title,tv_content,tv_time;
    }
}
