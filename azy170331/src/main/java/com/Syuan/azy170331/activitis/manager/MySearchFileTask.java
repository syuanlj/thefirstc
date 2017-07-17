package com.Syuan.azy170331.activitis.manager;

import android.os.AsyncTask;

import com.Syuan.azy170331.activitis.entity.FileInfo;
import com.Syuan.azy170331.activitis.utils.CommonUtil;
import com.Syuan.azy170331.activitis.utils.FileTypeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sy on 2017/5/11.
 */
//                              传入的参数；中途使用的参数；执行完成返回的
public class MySearchFileTask extends AsyncTask<File, File, String> {
    //不同类型的文件所对应的item位置
    public static final int TYPE_TXT = 0;
    public static final int TYPE_AUDIO = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_ZIP = 4;
    public static final int TYPE_APK = 5;
    public static final int TYPE_OTHER = 6;

    //不同类型文件的文件大小
    public static long TYPE_TXT_SIZE = 0;
    public static long TYPE_AUDIO_SIZE = 0;
    public static long TYPE_VIDEO_SIZE = 0;
    public static long TYPE_IMAGE_SIZE = 0;
    public static long TYPE_ZIP_SIZE = 0;
    public static long TYPE_APK_SIZE = 0;
    public static long TYPE_OTHER_SIZE = 0;
    public static long TYPE_ALL_SIZE = 0;

    //当前文件所在的位置
    private static int curPosition;
    //当前文件的文件大小
    private static long curSize;

    //确定一个集合里面的元素分别是不同类型文件的集合，存放着不同类型文件。
    private static List<List<FileInfo>> lists;

    public static List<List<FileInfo>> getList() {
        //判断该集合是否为空,防止被多次初始化
        if (lists == null) {
            lists = new ArrayList<>();//实例化外层list
            for (int i = 0; i < 7; i++) {
                lists.add(new ArrayList<FileInfo>());//添加二级界面数据集
            }
        }
        return lists;
    }

    //任务执行前的初始化操作和准备
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        TYPE_TXT_SIZE = 0;
        TYPE_AUDIO_SIZE = 0;
        TYPE_VIDEO_SIZE = 0;
        TYPE_IMAGE_SIZE = 0;
        TYPE_ZIP_SIZE = 0;
        TYPE_APK_SIZE = 0;
        TYPE_OTHER_SIZE = 0;
        TYPE_ALL_SIZE = 0;
        curSize = 0;

        //清除数据
        for (int i = 0; i < 7; i++) {
            getList().get(i).clear();//逐个清除二级界面
        }
    }

    //执行耗时操作——（不能进行UI操作）——任务执行中（publishProgress（））会执行onProgressUpdate（）方法
    @Override
    protected String doInBackground(File... params) {
        //搜索文件
        File file = params[0];//获得传递进来的File对象
        searchFile(file);
        return "ok";
    }

    /**
     * 搜索文件
     *
     * @param file
     */
    private void searchFile(File file) {
        //判断是文件还是文件夹
        if (file.isDirectory()) {
            File[] file1 = file.listFiles();
            for (File file2 : file1) {
                searchFile(file2);
            }
        } else {
            publishProgress(file);//odInBackground()没有结束才有作用;需要传数据集file
        }
    }

    //中途处理反馈的结果——需要doInBackground（）调用publishProgress（）方法
    //可以中途进行UI操作
    @Override
    protected void onProgressUpdate(File... values) {
        File file = values[0];
        FileInfo info = new FileInfo();
        info.setFile(file);
        info.setFileSize(file.length());//获得文件大小
        info.setFileName(file.getName());//文件名
        info.setLastTime(CommonUtil.getStrTime(file.lastModified()));//得到文件最后操作时间
        info.setChecked(false);//设置是否选中
        String[] strings = FileTypeUtil.getFileIconAndTypeName(file);//strings[0]是图片，strings[1]是文件类型
        info.setIconId(strings[0]);//获得文件图标
        String fileType = strings[1];//获得文件类型
        switch (fileType) {
            case FileTypeUtil.TYPE_APK:
                curPosition = TYPE_APK;//当前位置=list下标
                //计算文件大小
                TYPE_APK_SIZE += file.length();
                curSize = TYPE_APK_SIZE;
                break;
            case FileTypeUtil.TYPE_AUDIO:
                curPosition = TYPE_AUDIO;
                //计算文件大小
                TYPE_AUDIO_SIZE += file.length();
                curSize = TYPE_AUDIO_SIZE;
                break;
            case FileTypeUtil.TYPE_VIDEO:
                curPosition = TYPE_VIDEO;
                //计算文件大小
                TYPE_VIDEO_SIZE += file.length();
                curSize = TYPE_VIDEO_SIZE;
                break;
            case FileTypeUtil.TYPE_TXT:
                curPosition = TYPE_TXT;
                //计算文件大小
                TYPE_TXT_SIZE += file.length();
                curSize = TYPE_TXT_SIZE;
                break;
            case FileTypeUtil.TYPE_IMAGE:
                curPosition = TYPE_IMAGE;
                //计算文件大小
                TYPE_IMAGE_SIZE += file.length();
                curSize = TYPE_IMAGE_SIZE;
                break;
            case FileTypeUtil.TYPE_ZIP:
                curPosition = TYPE_ZIP;
                //计算文件大小
                TYPE_ZIP_SIZE += file.length();
                curSize = TYPE_ZIP_SIZE;
                break;
            default:
                curPosition = TYPE_OTHER;
                //计算文件大小
                TYPE_OTHER_SIZE += file.length();
                curSize = TYPE_OTHER_SIZE;
                break;
        }
        //所有文件的总大小
        TYPE_ALL_SIZE = TYPE_APK_SIZE + TYPE_AUDIO_SIZE + TYPE_IMAGE_SIZE + TYPE_OTHER_SIZE + TYPE_TXT_SIZE
                + TYPE_VIDEO_SIZE + TYPE_ZIP_SIZE;
        if (fileSizeListener != null) {
            fileSizeListener.searching(curPosition, curSize, TYPE_ALL_SIZE);
        }

        //将不同类型的实体类对象放入对应类型的集合
        getList().get(curPosition).add(info);
    }

    //后台任务执行结束，并通过return返回时，执行此方法，——return返回值作为这里的参数使用
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (fileSizeListener != null) {
            fileSizeListener.finishSearch();
        }
    }

    //取消当前任务
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (fileSizeListener != null) {
            fileSizeListener.finishSearch();
        }
    }

    private FileSizeListener fileSizeListener;

    public interface FileSizeListener {
        /**
         * 搜索过程中
         *
         * @param position 当前类型的位置
         * @param fileSize 当前类型文件的大小
         * @param allSize  所有类型文件的总大小
         */
        void searching(int position, long fileSize, long allSize);

        /**
         * 搜索结果
         */
        void finishSearch();
    }

    /**
     * 实现接口
     *
     * @param listener
     */
    public void setFileSizeListener(FileSizeListener listener) {
        fileSizeListener = listener;
    }
}
