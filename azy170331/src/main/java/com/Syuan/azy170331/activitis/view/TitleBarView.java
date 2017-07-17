package com.Syuan.azy170331.activitis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Syuan.azy170331.R;

public class TitleBarView extends RelativeLayout {
    private RelativeLayout rl_left, rl_center, rl_right;
    private ImageView iv_left, iv_right;
    private TextView tv_center;

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.activity_title_bar, this);
        if (isInEditMode()){
            return;
        }
        init();

    }


    private void init() {
        rl_left = (RelativeLayout) findViewById(R.id.rl_left);
        rl_center = (RelativeLayout) findViewById(R.id.rl_center);
        rl_right = (RelativeLayout) findViewById(R.id.rl_right);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_center = (TextView) findViewById(R.id.tv_center);
    }

    public void initTitleBar(OnClickListener letfListener, int leftResID, String centerText, OnClickListener rightListener, int rightResID) {
        setLeftOnClicklistener(letfListener);
        setLeftImageView(leftResID);
        setCenterText(centerText);
        setRightOnClicklistener(rightListener);
        setRightImageView(rightResID);
    }

    public void initTitleBar(OnClickListener letfListener, int leftResID, String centerText) {
        setLeftOnClicklistener(letfListener);
        setLeftImageView(leftResID);
        setCenterText(centerText);

        rl_right.setVisibility(View.INVISIBLE);

    }

    public void initTitleBar(String centerText,OnClickListener rightListener, int rightResID) {
        rl_left.setVisibility(View.INVISIBLE);
        setCenterText(centerText);
        setRightOnClicklistener(rightListener);
        setRightImageView(rightResID);
    }


    public void initTitleBar(String centerText) {
        rl_left.setVisibility(View.INVISIBLE);
        setCenterText(centerText);
        rl_right.setVisibility(View.INVISIBLE);
    }


    /**
     * 设置左边图像
     * @param resID 图像ID
     */
    private void setLeftImageView(int resID) {
        iv_left.setImageResource(resID);
    }

    /**
     * 设置右边图像
     * @param resID 图像ID
     */
    private void setRightImageView(int resID) {
        iv_right.setImageResource(resID);
    }

    /**
     * 设置标题内容
     * @param centerText 标题内容
     */
    private void setCenterText(String centerText) {
        tv_center.setText(centerText);
    }

    /**
     * 左边设置点击监听
     * @param listener 监听对象
     */
    private void setLeftOnClicklistener(OnClickListener listener) {
        rl_left.setOnClickListener(listener);
    }

    /**
     * 右边设置点击监听
     * @param listener 监听对象
     */
    private void setRightOnClicklistener(OnClickListener listener) {
        rl_right.setOnClickListener(listener);
    }


}
