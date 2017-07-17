package com.syuan.myapplication.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.activity.MainActivity;
import com.syuan.myapplication.activity.NewsDetailActivity;
import com.syuan.myapplication.adapter.NewsListAdapter;
import com.syuan.myapplication.db.MySqliteDataBase;
import com.syuan.myapplication.entity.NewsListInfo;

/**
 * Created by sy on 2017/6/12.
 */

public class Save extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    private ListView lv;
    private NewsListAdapter adapter;
    private PopupWindow popWin;
    private int position;
    private MySqliteDataBase myDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.save_fragment,container,false);
        lv= (ListView) view.findViewById(R.id.save_lv);
        adapter=new NewsListAdapter(getActivity());

        init();
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        return view;
    }

    private void init() {
        myDB=new MySqliteDataBase(getActivity());//注意：需要重新new出来
        adapter.setList(myDB.getNewsList());
        lv.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("link",adapter.getList().get((int) id));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        this.position=position;
        showPopuWindow();
        return true;
    }
    private void showPopuWindow() {
        popWin = new PopupWindow(getActivity());
        View popView = View.inflate(getActivity(), R.layout.popu_save, null);

        TextView tv_camera, tv_photos;
        tv_camera = (TextView) popView.findViewById(R.id.tv_camera);
        tv_photos = (TextView) popView.findViewById(R.id.tv_photos);
        tv_camera.setOnClickListener(this);
        tv_photos.setOnClickListener(this);
        //设置宽高
        popWin.setWidth(lv.getWidth() -100);//宽
        popWin.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//高
        popWin.setContentView(popView);//设置要显示的view
        popWin.setOutsideTouchable(true);//设置外部可以点击
        popWin.setFocusable(true);
        popWin.setBackgroundDrawable(getResources().getDrawable(R.color.trans));//设置透明背景
//        popWin.showAsDropDown(tbv);
        popWin.showAtLocation(lv, Gravity.BOTTOM,0,0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_camera:
                MySqliteDataBase.delItem((NewsListInfo) adapter.getItem(position));
                init();
                popWin.dismiss();
                break;
            case R.id.tv_photos:
                MySqliteDataBase.delAll();
                init();
                popWin.dismiss();
                break;
        }
    }
}
