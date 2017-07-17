package com.syuan.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syuan.myapplication.R;
import com.syuan.myapplication.activity.LoginActivity;
import com.syuan.myapplication.activity.NewsDetailActivity;
import com.syuan.myapplication.activity.UserActivity;
import com.syuan.myapplication.adapter.NewsListAdapter;
import com.syuan.myapplication.adapter.RecyclerViewAdapter;
import com.syuan.myapplication.entity.NewsListInfo;
import com.syuan.myapplication.entity.NewsTypeInfo;
import com.syuan.myapplication.entity.UserInfo;
import com.syuan.myapplication.https.HttpsRequest;
import com.syuan.myapplication.utils.LoadImage;
import com.syuan.myapplication.view.slidingmenu.xlistview.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by sy on 2017/6/6.
 */

public class NewsListFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener, AdapterView.OnItemClickListener, XListView.IXListViewListener {

    private RecyclerView rcv;
    private XListView xListView;
    private RecyclerViewAdapter adapter;
    private NewsListAdapter newsListAdapter;
    private int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.news_list_fragment,container,false);
        initView(view);
        initEvent();
        requestNewsType();//请求新闻标题
        return view;
    }

    private void requestNewsType() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result=HttpsRequest.RequestNewsType();
                if (result==null){
                    return;
                }
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("data",result);
                msg.setData(bundle);
                msg.what=1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result=msg.getData().getString("data");
            Log.e("result",result.toString());
            try {
                JSONObject jo=new JSONObject(result);

                if (jo.getString("message").equals("OK")){
                    JSONArray ja=jo.getJSONArray("data");
                    switch (msg.what){
                        case 1:
                            for (int i=0;i<ja.length();i++){
                                jo=ja.getJSONObject(i);
                                JSONArray jaSub=jo.getJSONArray("subgrp");
                                for (int j=0;j<jaSub.length();j++){
                                    jo=jaSub.getJSONObject(j);
                                    NewsTypeInfo info=new NewsTypeInfo(jo.getString("subgroup"),jo.getInt("subid"));
                                    adapter.add(info);
                                }
                            }
                            position=adapter.getItem(0).getSubid();
                            requestNewsList(position,1,0);
                            adapter.notifyDataSetChanged();
                            break;
                        case 2:
                            Gson gson=new Gson();
                            List<NewsListInfo> newsList=gson.fromJson(ja.toString(),new TypeToken<List<NewsListInfo>>() {
                            }.getType());
                            newsListAdapter.setList(newsList);
                            newsListAdapter.notifyDataSetChanged();

                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    });

    private void initEvent() {
        rcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL,false));
    }

    private void initView(View view) {
        rcv= (RecyclerView) view.findViewById(R.id.rcv_newsList);
        xListView= (XListView) view.findViewById(R.id.xListView);
        adapter=new RecyclerViewAdapter(getActivity());
        newsListAdapter=new NewsListAdapter(getActivity());
        xListView.setAdapter(newsListAdapter);
        rcv.setAdapter(adapter);
        adapter.itemListener(this);
        xListView.setOnItemClickListener(this);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
    }


    @Override//标题的点击事件
    public void setOnItemListener(NewsTypeInfo newsTypeInfo, int position) {
        adapter.selectPosition=position;
        adapter.notifyDataSetChanged();
        //请求该类别新闻列表
        requestNewsList(newsTypeInfo.getSubid(),1,0);
    }

    private void requestNewsList (final int subid, final int dir, final int nid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result=HttpsRequest.requestNewsList(subid,dir,nid);
                if(result == null){
                    return;
                }
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("data",result);
                msg.setData(bundle);
                msg.what=2;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("link",newsListAdapter.getList().get((int) id));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        newsListAdapter.clear();
        requestNewsList(position,1,0);
        xListView.stopRefresh();//停止刷新
    }

    @Override
    public void onLoadMore() {
        if (adapter.getItemCount() >0) {
            int newId = adapter.getItem(adapter.getItemCount() - 1).getSubid();
            requestNewsList(newId, 2, 0);
            xListView.stopLoadMore();
        }
    }
}
