package com.Syuan.azy170331.activitis.adaper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.Syuan.azy170331.R;
import com.Syuan.azy170331.activitis.entity.SoftManagerInfo;
import com.Syuan.azy170331.activitis.utils.BitmapUtil;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by sy on 2017/4/14.
 */

public class ListViewAdapter extends BaseAdapter {

    private List<SoftManagerInfo> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private boolean isFling = false;//是否滑动
    private BitmapUtil.SizeMessage sizeMessage;

    public ListViewAdapter(Context context) {
        this.context = context;
        sizeMessage = new BitmapUtil.SizeMessage(context, false, 20, 20);//设置图片大小
        //创建一个数据集list
        list = new ArrayList<>();
        //实例化LayoutInflater
        layoutInflater = LayoutInflater.from(context);

    }

    public void setFling(boolean isFling) {
        this.isFling = isFling;
    }

    public boolean getFling() {
        return isFling;
    }

    /**
     * 添加一个数据
     *
     * @param list
     */
    public void add(List<SoftManagerInfo> list) {
        this.list = list;
    }

    //返回当前数据
    public List<SoftManagerInfo> getList(){
        return list;
    }

    //清理当前数据
    public void clear(){
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final viewHold viewHold;
        if (view == null) {
            viewHold = new viewHold();
            //载入一个布局
            view = layoutInflater.inflate(R.layout.activity_list_view, null);
            //找到布局中相应的控件
            viewHold.tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
            viewHold.tv_package_name = (TextView) view.findViewById(R.id.tv_package_name);
            viewHold.tv_version = (TextView) view.findViewById(R.id.tv_version);
            viewHold.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewHold.iv_img = (ImageView) view.findViewById(R.id.iv_img);
            viewHold.cb_soft= (CheckBox) view.findViewById(R.id.checkbox_soft);
            view.setTag(viewHold);
        } else {
            viewHold = (viewHold) view.getTag();
        }

        final SoftManagerInfo info = list.get(position);

        //设置对应的数据


//        String appName = info.getLableName();
        viewHold.tv_app_name.setText(info.getLableName());//应用名

//        String packageName = info.getPackageName();
        viewHold.tv_package_name.setText(info.getPackageName());   //包名

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//格式化日期
//        String date = format.format(new Date(info.getFirstInsetData()));
        viewHold.tv_version.setText( CommonUtil.getStrTime(info.getFirstInsetData()));//日期
//        //版本号
//        String version=info.versionName;
        viewHold.tv_version.setText("");

        viewHold.iv_img.setImageDrawable(info.getIcon());//图片

        viewHold.cb_soft.setChecked(info.isClear());
        //设置监听
        viewHold.cb_soft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                info.setClear(isChecked);
            }
        });
        return view;

    }

    class viewHold {
        TextView tv_app_name;
        TextView tv_package_name;
        TextView tv_version;
        TextView tv_date;
        ImageView iv_img;
        CheckBox cb_soft;
    }
}
