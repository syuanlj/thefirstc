package com.syuan.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.syuan.enity.VideoInfo;
import com.syuan.utils.LoadImage;
import com.syuan.videozx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/24.
 */

public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private List<VideoInfo> list;
    private Context context;
    private String url;

    public VideoAdapter(Context context) {
        this.context=context;
        list=new ArrayList<>();
    }

    private void add(List<VideoInfo> list,String url){
        this.list = list;
        this.url=url;
    }

    //绑定视图
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    //设置视图内容
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VideoInfo info=list.get(position);
        //请求视频封面,返回值类型为Bitmap
//        holder.iv_pic.setImageBitmap();
        LoadImage.loadImage(context,url,holder.iv_pic);
        holder.tv_caption.setText(info.getCaption());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewhold优化
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_pic;
        private TextView tv_caption;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_pic= (ImageView) itemView.findViewById(R.id.iv_item_pic);
            tv_caption= (TextView) itemView.findViewById(R.id.tv_item_caption);
        }
    }


}
