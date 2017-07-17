package com.syuan.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syuan.myapplication.R;
import com.syuan.myapplication.adapter.CommentAdapter;
import com.syuan.myapplication.entity.CommentInfo;
import com.syuan.myapplication.https.HttpsRequest;
import com.syuan.myapplication.test.BaseActivity;
import com.syuan.myapplication.utils.SystemUtils;
import com.syuan.myapplication.view.TitleBarView;
import com.syuan.myapplication.view.slidingmenu.xlistview.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by sy on 2017/6/16.
 */

public class CommentActivity extends BaseActivity implements XListView.IXListViewListener{
    private TitleBarView tbv;
    private XListView xlv;
    private EditText et_send;
    private ImageView iv_send;
    private String token;
    private int nid;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        token = getSharedPreferences("user", MODE_PRIVATE).getString("token", null);
        nid = getIntent().getExtras().getInt("nid");
    }

    private void init() {
        tbv = (TitleBarView) findViewById(R.id.tbv_comment);
        xlv = (XListView) findViewById(R.id.xlv_comment);
        et_send = (EditText) findViewById(R.id.et_comment);
        iv_send= (ImageView) findViewById(R.id.iv_send_comment);

        tbv.initTitleBar(listener, R.mipmap.back, "评论");

        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.iv_send_comment:
                        sendComment();
                        break;
                }
            }
        });
        adapter = new CommentAdapter(this);
        xlv.setAdapter(adapter);
        xlv.setPullRefreshEnable(true);//刷新
        xlv.setPullLoadEnable(true);//加载更多
        xlv.setXListViewListener(this);
        requestComment(1, 0);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void sendComment() {
        if (token == null) {
            showToast("请登录");
            return;
        }
        String comment = et_send.getText().toString().trim();//获取评论内容
        if (comment == null) {
            showToast("无评论");
            return;
        }

        //将评论内容转换格式提交
        try {
            comment = URLEncoder.encode(comment, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String finalComment = comment;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imei = SystemUtils.getIMEI(CommentActivity.this);
                String result = HttpsRequest.sendComment(token, nid, imei, finalComment);
                if (result != null) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    msg.setData(bundle);
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result = msg.getData().getString("result");
            try {
                JSONObject jo = new JSONObject(result);
                switch (msg.what) {
                    case 1:
                        if (jo.getString("message").equals("OK")) {
                            adapter.clear();//清空适配器
                            requestComment(1, 0);//下载最新评论
                            et_send.setText("");//置空
                            showToast(jo.getString("explain"));
                        }
                        break;
                    case 2:
                        if (jo.getString("message").equals("OK")) {
                            JSONArray ja = jo.getJSONArray("data");
                            Gson gson = new Gson();
                            List<CommentInfo> commentList = gson.fromJson(ja.toString(), new TypeToken<List<CommentInfo>>() {
                            }.getType());
                            adapter.setList(commentList);
                            adapter.notifyDataSetChanged();
                        }
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    });

    @Override
    public void onRefresh() {
        //刷新评论
        adapter.clear();
        requestComment(1, 0);
        xlv.stopRefresh();
    }

    @Override
    public void onLoadMore() {
        //加载更多评论
        int cid = 0;
        if (adapter.getList().size() != 0) {
            cid = adapter.getItem(adapter.getCount() - 1).getCid();
        }
        requestComment(2, cid);
        xlv.stopLoadMore();
    }

    private void requestComment(final int dir, final int cid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpsRequest.requestComment(nid, dir, cid);
                if (result != null) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    msg.setData(bundle);
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }


}
