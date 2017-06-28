package com.syuan.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.syuan.enity.VideoInfo;
import com.syuan.utils.LoadImage;
import com.syuan.videozx.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/24.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private List<VideoInfo> list;
    private Context context;

    public VideoAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void clear() {
        list.clear();
    }

    public void add(List<VideoInfo> list1) {
        list = list1;
    }

    public List<VideoInfo> getList() {
        return list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //绑定视图
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    //设置视图内容
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (holder != null) {
            //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
            VideoInfo info = list.get(position );
//                holder.iv_pic.setImageResource(R.mipmap.ic_launcher);
            holder.tv_caption.setText(info.getCaption());
            holder.tv_like.setText("like:"+info.getLikes_count());
            holder.tv_comment.setText("comment:"+info.getComments_count());
            holder.tv_play.setText("play:"+info.getPlays_count());
            LoadImage.loadImage(context, info.getAvater(), holder.iv_avatar);
            LoadImage.loadImage(context, info.getCover_pic(), holder.iv_pic);
//            int width = holder.iv_pic.getWidth();
//            ViewGroup.LayoutParams params = holder.iv_pic.getLayoutParams();
//            //设置图片的相对于屏幕的宽高比
//            params.width = width;
//            params.height = (int) (200 + Math.random() * 400);
//            holder.iv_pic.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewhold优化
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_pic,iv_avatar;
        public TextView tv_caption,tv_play,tv_comment,tv_like;

        public MyViewHolder(View itemView) {
            super(itemView);

            iv_pic = (ImageView) itemView.findViewById(R.id.iv_item_pic);
            iv_avatar= (ImageView) itemView.findViewById(R.id.iv_item_avatar);
            tv_caption = (TextView) itemView.findViewById(R.id.tv_item_caption);
            tv_play= (TextView) itemView.findViewById(R.id.tv_play);
            tv_comment= (TextView) itemView.findViewById(R.id.tv_item_comment);
            tv_like= (TextView) itemView.findViewById(R.id.tv_item_like);

//            int width=iv_pic.getWidth();
//            ViewGroup.LayoutParams params=iv_pic.getLayoutParams();
//            //设置图片的相对于屏幕的宽高比
//            params.width = width;
//            params.height =  (int) (200 + Math.random() * 400) ;
//            iv_pic.setLayoutParams(params);
        }
    }
}
