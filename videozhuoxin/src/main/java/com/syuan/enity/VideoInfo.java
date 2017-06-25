package com.syuan.enity;

/**
 * Created by sy on 2017/6/24.
 */

/*
page=1  // 代表页数, 1为首页.

[
{
    "id": 595116562,
    "url": "http://www.meipai.com/media/595116562",
    "cover_pic": "http://mvimg2.meitudata.com/57ff0880bff607539.jpg!thumb320",
    "screen_name": "QM工作室",
    "caption": "贵圈的那些捆绑炒作",
    "avatar": "http://mvavatar2.meitudata.com/565719faef3018299.jpg",
    "plays_count": 5317,
    "comments_count": 4,
    "likes_count": 36
},
....

]
 */

public class VideoInfo {
    private int page;//第几页
    private String id;//视频id
    private String url;//视频链接
    private String cover_pic;//视频封面
    private String screen_name;//视频作者昵称
    private String caption;//视频描述
    private String avater;//视频作者头像
    private int plays_count;//播放数
    private int comments_count;//评论数
    private int likes_count;//点赞数


    @Override
    public String toString() {
        return "VideoInfo{" +
                "page=" + page +
                ", id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", caption='" + caption + '\'' +
                ", avater='" + avater + '\'' +
                ", plays_count=" + plays_count +
                ", comments_count=" + comments_count +
                ", likes_count=" + likes_count +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public int getPlays_count() {
        return plays_count;
    }

    public void setPlays_count(int plays_count) {
        this.plays_count = plays_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }
}
