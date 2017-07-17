package com.syuan.myapplication.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sy on 2017/6/6.
 */

public class UserInfo implements Serializable{
    private String uid;
    private String portrait;
    private int integration;
    private int comnum;
    private List<LoginLog> loginlog;

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", portrait='" + portrait + '\'' +
                ", integration=" + integration +
                ", comnum=" + comnum +
                ", loginlog=" + loginlog +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public int getComnum() {
        return comnum;
    }

    public void setComnum(int comnum) {
        this.comnum = comnum;
    }

    public List<LoginLog> getLoginlog() {
        return loginlog;
    }

    public void setLoginlog(List<LoginLog> loginlog) {
        this.loginlog = loginlog;
    }

    public static class LoginLog implements Serializable {
        private String time;
        private String adress;
        private int device;

        @Override
        public String toString() {
            return "LoginLog{" +
                    "time='" + time + '\'' +
                    ", adress='" + adress + '\'' +
                    ", device=" + device +
                    '}';
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        public int getDevice() {
            return device;
        }

        public void setDevice(int device) {
            this.device = device;
        }
    }
}
