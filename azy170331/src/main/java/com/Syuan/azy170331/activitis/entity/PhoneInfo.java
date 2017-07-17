package com.Syuan.azy170331.activitis.entity;

import android.graphics.drawable.Drawable;

/**
 * 实体类
 * Created by sy on 2017/4/25.
 */

public class PhoneInfo {
    private Drawable drawable;
    private String info;
    private String msg;

    public PhoneInfo(Drawable drawable, String info, String msg) {
        this.drawable = drawable;
        this.info = info;
        this.msg = msg;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
