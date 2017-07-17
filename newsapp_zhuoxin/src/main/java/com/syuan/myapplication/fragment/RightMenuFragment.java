package com.syuan.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.syuan.myapplication.R;
import com.syuan.myapplication.activity.LoginActivity;
import com.syuan.myapplication.activity.UserActivity;
import com.syuan.myapplication.entity.UserInfo;
import com.syuan.myapplication.https.HttpsRequest;
import com.syuan.myapplication.utils.LoadImage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sy on 2017/6/6.
 */

public class RightMenuFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView iv_login,share,weibo,weixin,qq;
    private TextView tv_login,tv_update;
    private String token;//手机令牌
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private UserInfo userInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    view=inflater.inflate(R.layout.fragment_right_menu,container,false);
        initView(view);
        initEvent();
        sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor=sp.edit();
        token=sp.getString("token",null);
        requestUser(token);//使用token请求用户数据
        return view;
    }

    private void initEvent() {
        iv_login.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        share.setOnClickListener(this);
        weibo.setOnClickListener(this);
        weixin.setOnClickListener(this);
        qq.setOnClickListener(this);
    }

    private void initView(View view) {
        iv_login= (ImageView) view.findViewById(R.id.right_menu_iv_login);
        tv_login= (TextView) view.findViewById(R.id.right_menu_tv_login);
        tv_update= (TextView) view.findViewById(R.id.right_menu_tv_update);
        share= (ImageView) view.findViewById(R.id.share);
        weibo= (ImageView) view.findViewById(R.id.weibo);
        weixin= (ImageView) view.findViewById(R.id.weixin);
        qq= (ImageView) view.findViewById(R.id.qq);
    }

    /**
     * 请求用户名和头像
     * @param token 手机令牌
     */
    private void requestUser(final String token) {
        if (token==null){
            Toast.makeText(getActivity(),"token为空",Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result=HttpsRequest.RequestUser(token);
                if (result==null){
//                    Toast.makeText(getActivity(),"result为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                Message msg=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("data",result);
                msg.setData(bundle);
                msg.what=123;
                handler.sendMessage(msg);
            }
        }).start();
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 123:
                    String result=msg.getData().getString("data");
                    try {
                        JSONObject jo=new JSONObject(result);
                        if (jo.getString("message").equals("OK")){
                            jo=jo.getJSONObject("data");
                            Gson gson=new Gson();
                            userInfo=gson.fromJson(jo.toString(),UserInfo.class);//?
                            //展示登录后的界面
                            showLoginView(userInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return false;
        }
    });

    /**
     * 展示什么样的图片
     * @param userInfo 实体类
     */
    private void showLoginView(UserInfo userInfo) {
        if (userInfo==null){
            Toast.makeText(getActivity(),"userInfo为空",Toast.LENGTH_SHORT).show();
            iv_login.setImageResource(R.mipmap.biz_pc_main_info_profile_avatar_bg_dark);
            tv_login.setText("登录名");
        }else {
            tv_login.setText(userInfo.getUid());
            //下载并设置头像
            String url=userInfo.getPortrait();//图片地址
            LoadImage.loadImage(getActivity(),url,iv_login);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_menu_iv_login:
            case R.id.right_menu_tv_login:
                if (userInfo==null)
                startActivityForResult(new Intent(getActivity(), LoginActivity.class),11);
                else{
                    Intent intent=new Intent(getActivity(), UserActivity.class);
                    intent.putExtra("userInfo",userInfo);
                    startActivityForResult(intent,11);
                }
                break;
            case R.id.right_menu_tv_update:
                break;
            case R.id.share:
                Toast.makeText(getActivity(),"朋友圈",Toast.LENGTH_SHORT).show();
                break;
            case R.id.weibo:
                Toast.makeText(getActivity(),"微博",Toast.LENGTH_SHORT).show();
                break;
            case R.id.weixin:
                Toast.makeText(getActivity(),"微信",Toast.LENGTH_SHORT).show();
                break;
            case R.id.qq:
                Toast.makeText(getActivity(),"QQ",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==11){
            switch (resultCode){
                case 21:
                    //获得登录界面返回的token
                    token=data.getExtras().getString("token");
                    //首先将token保存                提交
                    editor.putString("token",token).commit();

                    //同token请求用户名和头像
                    requestUser(token);//使用token请求用户数据
                    break;
                case 3:
                    showLoginView(null);
                    userInfo=null;
                    break;
                case 4:
                    showLoginView(userInfo);
                    break;

            }
        }
    }
}
