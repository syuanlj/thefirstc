package com.Syuan.azy170331.activitis.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by sy on 2017/5/2.
 */

public class RunningAppInfo {
    private String packageName;//安装包包名
    private String lableName;//运行程序名称
    private Drawable icon;//应用图标
    private long size;//应用大小
    private boolean isClear;//是否需要清除
    private boolean isSystem;//是否是系统应用

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }
}
