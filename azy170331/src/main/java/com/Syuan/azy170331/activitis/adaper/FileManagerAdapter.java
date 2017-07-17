package com.Syuan.azy170331.activitis.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.entity.FileInfo;
import com.Syuan.azy170331.activitis.entity.FileManagerInfo;
import com.Syuan.azy170331.activitis.entity.RunningAppInfo;
import com.Syuan.azy170331.activitis.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.RED;

/**
 * Created by sy on 2017/5/2.
 */

public class FileManagerAdapter extends BaseAdapter {
    private Context context;
    private List<FileManagerInfo> list;
    private LayoutInflater inflater;

    public FileManagerAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        list= new ArrayList<>();
    }

    public void add(List<FileManagerInfo> list){
        this.list=list;
    }

    public void add(FileManagerInfo fileManagerInfo){
        list.add(fileManagerInfo);
    }
    public List<FileManagerInfo> getList(){
        return list;
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FileManagerInfo getItem(int position) {
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
            convertView=inflater.inflate(R.layout.layout_file_manager_lv,null);
            viewHold.file_manager_tv0= (TextView) convertView.findViewById(R.id.file_manager_tv0);
            viewHold.file_manager_tv1= (TextView) convertView.findViewById(R.id.file_manager_tv1);
            viewHold.fileamnager_pb= (ProgressBar) convertView.findViewById(R.id.file_manager_pb);
            viewHold.file_manager_iv= (ImageView) convertView.findViewById(R.id.file_manager_iv);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }

        FileManagerInfo info= list.get(position);
        viewHold.file_manager_tv0.setText(info.getType());
        viewHold.file_manager_tv1.setText(CommonUtil.getFileSize(info.getFileSize()));
        if (info.isLoading()){
            viewHold.file_manager_iv.setVisibility(View.INVISIBLE);
            viewHold.fileamnager_pb.setVisibility(View.VISIBLE);
        }else {
            viewHold.file_manager_iv.setVisibility(View.VISIBLE);
            viewHold.fileamnager_pb.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private class ViewHold {
        TextView file_manager_tv0;
        TextView file_manager_tv1;
        ProgressBar fileamnager_pb;
        ImageView file_manager_iv;
    }
}
