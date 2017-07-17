package com.Syuan.azy170331.activitis.manager;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * 清理内存
 * Created by sy on 2017/4/11.
 */

public class RunningAppManager {
    private Context context;
    private static RunningAppManager manager;
    private ActivityManager activityManager;//管理后台内存

    public RunningAppManager(Context context) {
        this.context = context;
        //实例化ActivityManager
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

    }

    public static RunningAppManager getManager(Context context) {
        if (null == manager) {
            manager = new RunningAppManager(context);
        }
        return manager;
    }

    public void killAll(){
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for (int i=0;i<list.size();i++){
            ActivityManager.RunningAppProcessInfo info = list.get(i);
            if (info.importance>=ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE){
                activityManager.killBackgroundProcesses(info.processName);//删除（包名）
            }
        }
    }
}
