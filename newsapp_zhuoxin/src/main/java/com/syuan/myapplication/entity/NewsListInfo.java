package com.syuan.myapplication.entity;

import java.io.Serializable;

/**
 * Created by sy on 2017/6/8.
 */

/*
            {
	“status”:0,
	“message”:”OK”,
	“data”:[
                {
            “type”:1,
			“nid”:新闻编号,
			“stamp":新闻时间戳,
			“icon”:图标路径,
			“title”:,新闻标题
			“summary”:,新闻摘要
			“link”:新闻链接
		        },……
            ]
}
 */
public class NewsListInfo implements Serializable {
    private int nid;
    private String stamp;
    private String icon;
    private String title;
    private String summary;
    private String link;

    @Override
    public String toString() {
        return "NewsListInfo{" +
                "nid=" + nid +
                ", stamp='" + stamp + '\'' +
                ", icon='" + icon + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
