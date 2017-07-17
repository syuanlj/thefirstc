package com.syuan.myapplication.entity;

/**
 * Created by sy on 2017/6/7.
 */

public class NewsTypeInfo {
    private String subgroup;
    private int subid;

    public NewsTypeInfo(String subgroup, int subid) {
        this.subgroup = subgroup;
        this.subid = subid;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }
}
