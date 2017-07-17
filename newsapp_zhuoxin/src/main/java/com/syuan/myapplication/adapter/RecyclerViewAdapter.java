package com.syuan.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.entity.NewsTypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/6/7.
 *
 * 适配器需要继承RecyclerView.Adapter
 * 还要传递一个泛型，一般是自己写一个内部类
 * 内部类要继承RecyclerView.ViewHolder
 * 然后重新抽象方法
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHold>{
    private LayoutInflater inflater;
    private List<NewsTypeInfo> list;
    private ItemClickListener clickListener;
    public int selectPosition=0;
    public RecyclerViewAdapter(Context context) {
        inflater=LayoutInflater.from(context);
        list=new ArrayList<>();
    }

    public void add(NewsTypeInfo info){
        list.add(info);
    }

    public NewsTypeInfo getItem(int selectPosition){
        return list.get(selectPosition);
    }
    //创建重复使用的ViewHolder对象
    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_rc,parent,false);
        MyViewHold myViewHold=new MyViewHold(view);
        myViewHold.textView= (TextView) view.findViewById(R.id.tv);
        return myViewHold;
    }

    //在绑定ViewHolder之后，给具体控件添加数据
    @Override
    public void onBindViewHolder(MyViewHold holder, final int position) {
        holder.textView.setText(list.get(position).getSubgroup());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null){
                    clickListener.setOnItemListener(list.get(position),position);
                }
            }
        });
        if (selectPosition==position){
            holder.textView.setTextColor(Color.RED);
        }else {
            holder.textView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHold extends RecyclerView.ViewHolder {
        public MyViewHold(View itemView) {
            super(itemView);
        }
        TextView textView;
    }

    public  interface ItemClickListener{
        void setOnItemListener(NewsTypeInfo newsTypeInfo,int position);
    }
    public void itemListener(ItemClickListener itemClickListener){
        clickListener=itemClickListener;
    }
}
