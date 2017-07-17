package com.Syuan.azy170331.activitis.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by sy on 2017/5/10.
 */

public class SoftManagerInfo {
    private String packageName;//安装包包名
    private String lableName;//运行程序名称
    private Drawable icon;//应用图标
    private boolean isClear;//是否需要清除
    private long firstInsetData;//安装时间

    public SoftManagerInfo(String packageName, String lableName, Drawable icon, boolean isClear, long firstInsetData) {
        this.packageName = packageName;
        this.lableName = lableName;
        this.icon = icon;
        this.isClear = isClear;
        this.firstInsetData = firstInsetData;
    }

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

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public long getFirstInsetData() {
        return firstInsetData;
    }

    public void setFirstInsetData(long firstInsetData) {
        this.firstInsetData = firstInsetData;
    }
}
