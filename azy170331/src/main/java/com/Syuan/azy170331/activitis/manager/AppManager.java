package com.Syuan.azy170331.activitis.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;


/**
 * Created by sy on 2017/4/19.
 */

public class AppManager {
    private static AppManager manager;
    private Context context;
    private PackageManager pm;//包管理

    public AppManager(Context context) {
        this.context=context;
        pm=context.getPackageManager();
    }

    public static AppManager getManager(Context context){
        if (manager==null)
        manager=new AppManager(context);
        return manager;
    }

    /**
     * 获取所有安装的软件
     * @return 获取所有安装的软件的数据集
     */
    public List<PackageInfo> getList(){
        List<PackageInfo> list=pm.getInstalledPackages(0);
        return list;

    }
}
