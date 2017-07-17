package com.Syuan.azy170331.activitis.entity;

/**
 * Created by sy on 2017/5/11.
 */

public class FileManagerInfo {
    private long fileSize;//该文件类型总大小
    private String type;
    private boolean isLoading;//判断显示进度条还是箭头

    public FileManagerInfo(long fileSize, String type, boolean isLoading) {
        this.fileSize = fileSize;
        this.type = type;
        this.isLoading = isLoading;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

}
