package com.syuan.videozx;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.syuan.adapter.VideoAdapter;
import com.syuan.enity.VideoInfo;
import com.syuan.https.HttpUrl;
import com.syuan.https.HttpsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rcv;
    private VideoAdapter adapter;
    private List<VideoInfo> list;
    private VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initData(HttpUrl.JAKE_URL);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.main_tbr);
        rcv = (RecyclerView) findViewById(R.id.rcv_main);
        videoView= (VideoView) findViewById(R.id.vv_test);
        rcv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        list = new ArrayList<>();//实例化集合
        adapter = new VideoAdapter(this);//实例化适配器
        rcv.setAdapter(adapter);//绑定适配器

        setSupportActionBar(toolbar);//设置支持toolbar

        SpacesItemDecoration spacesItemDecoration=new SpacesItemDecoration(10);//设置recyclerviewItem之间的间隙
        rcv.addItemDecoration(spacesItemDecoration);

        //设置为垂直布局，这是默认的

        //设置头布局,脚布局

        setHeaderView();
        setFooterView(rcv);

    }

    private void initData(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpsRequest.MyHttpsRquest(url);
//                Log.e("result", result);
                if (result == null) {
                    return;
                }
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("data", result);
                msg.setData(bundle);
                msg.what = 2;

                handler.sendMessage(msg);
            }
        }).start();

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result = msg.getData().getString("data");
            try {
                JSONArray ja = new JSONArray(result);
                switch (msg.what) {
                    case 2:
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            VideoInfo info = new VideoInfo(jo.getString("id"), jo.getString("url"), jo.getString("cover_pic"), jo.getString("screen_name"), jo.getString("caption"), jo.getString("avatar"), jo.getInt("plays_count"), jo.getInt("comments_count"), jo.getInt("likes_count"));
                            list.add(info);
                        }
                        adapter.add(list);

                        adapter.notifyDataSetChanged();
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            return false;
        }
    });

    //设置头布局
    private void setHeaderView() {
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse("http://mvvideo11.meitudata.com/594624d04791c9826.mp4"));
        videoView.requestFocus();
    }

    //设置脚布局
    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.footer_layout, view, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fun:
                Toast.makeText(this,"搞笑",Toast.LENGTH_SHORT).show();
                toolbar.setTitle("搞笑");
                adapter.clear();
                initData(HttpUrl.JAKE_URL);
                break;
            case R.id.action_stars:
                Toast.makeText(this,"明星",Toast.LENGTH_SHORT).show();
                toolbar.setTitle("明星");
                adapter.clear();
                initData(HttpUrl.STAR_URL);
                break;
            case R.id.action_travel:
                Toast.makeText(this,"宠物",Toast.LENGTH_SHORT).show();
                toolbar.setTitle("宠物");
                adapter.clear();
                initData(HttpUrl.PET_URL);
                break;
        }
        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }



    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;

            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }
}
