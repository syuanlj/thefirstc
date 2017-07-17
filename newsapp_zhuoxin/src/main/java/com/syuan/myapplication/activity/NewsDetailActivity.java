package com.syuan.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.syuan.myapplication.R;
import com.syuan.myapplication.db.MySqliteDataBase;
import com.syuan.myapplication.entity.NewsListInfo;
import com.syuan.myapplication.https.HttpUrl;
import com.syuan.myapplication.https.HttpsRequest;
import com.syuan.myapplication.view.TitleBarView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Response;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TitleBarView tbv;
    private WebView webView;
    private ProgressBar pb;
    private PopupWindow popWin;
    private View popView;
    private NewsListInfo info;
    private MySqliteDataBase mySqliteDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();

    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        tbv = (TitleBarView) findViewById(R.id.tbv_news_detail);
        webView = (WebView) findViewById(R.id.wv_news_detail);
        pb = (ProgressBar) findViewById(R.id.pb_news_detail);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        Intent intent = getIntent();
        info = (NewsListInfo) intent.getSerializableExtra("link");
        webView.loadUrl(info.getLink());
        tbv.initTitleBar(onClickListener, R.mipmap.back,
                info.getTitle(),
                onClickListener, R.mipmap.news_menu);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                pb.setProgress(newProgress);
                if (newProgress == 100) {
                    // 网页加载完成
                    pb.setVisibility(View.INVISIBLE);
                    webView.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_left:
                    finish();
//                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    break;
                case R.id.rl_right:
                    showPopuWindow();
                    break;
            }
        }
    };
    private TextView tv_savelocat, tv_comment, tv_share;

    private void showPopuWindow() {
        popWin = new PopupWindow(this);
        popView = View.inflate(this, R.layout.news_detail_choose_window, null);

        //设置宽高
        popWin.setWidth(webView.getWidth() / 3);//宽
        popWin.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//高
        popWin.setContentView(popView);//设置要显示的view
        popWin.setOutsideTouchable(true);//设置外部可以点击
        popWin.setBackgroundDrawable(getResources().getDrawable(R.color.trans));//设置透明背景
//        popWin.showAsDropDown(tbv);
        popWin.showAsDropDown(tbv, webView.getWidth() * 2 / 3, 0);
        initPopView();
        requestCommentNum();
    }

    private void requestCommentNum() {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.e("123123", info.toString());
                String result = HttpsRequest.requestCommentNum(info.getNid());
                if (result!=null){
                    Message msg=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("result",result);
                    msg.setData(bundle);
                    msg.what=1;
                    handler.sendMessage(msg);
                }

            }
        }).start();
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String result=msg.getData().getString("result");
                    try {
                        JSONObject obj = new JSONObject(result);
                        if (obj.getString("message").equals("OK")) {
                            tv_comment.setText("(" + obj.getInt("data") + ")评论");
                        } else {
                            tv_comment.setText("评论");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return false;
        }
    });

    private void initPopView() {
        tv_savelocat = (TextView) popView.findViewById(R.id.tv_savelocat);
        tv_comment = (TextView) popView.findViewById(R.id.tv_comment);
        tv_share = (TextView) popView.findViewById(R.id.tv_share);

        tv_savelocat.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        mySqliteDataBase = new MySqliteDataBase(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_savelocat:
                mySqliteDataBase.insertNews(info);
                popWin.dismiss();
                break;
            case R.id.tv_comment:
                Bundle bundle=new Bundle();
                bundle.putInt("nid",info.getNid());
                Intent intent=new Intent(this,CommentActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                popWin.dismiss();
                break;
            case R.id.tv_share:
                popWin.dismiss();
                showShare();
                break;
        }
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(info.getLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(info.getTitle());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(info.getIcon());//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(info.getLink());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(info.getLink());

        // 启动分享GUI
        oks.show(this);
    }
}
