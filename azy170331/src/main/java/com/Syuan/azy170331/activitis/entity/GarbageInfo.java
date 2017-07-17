package com.Syuan.azy170331.activitis.entity;

import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * Created by sy on 2017/5/5.
 */

public class GarbageInfo {
    private String name;//应用名
    private String apkname;//应用包名
    private int id;//id
    private String path;//路径
    private File file;
    private long size;//应用垃圾大小
    private Drawable icon;//应用图标
    private boolean isClear;//是否需要清除

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
