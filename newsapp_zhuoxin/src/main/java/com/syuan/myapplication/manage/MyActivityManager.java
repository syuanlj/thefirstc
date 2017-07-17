package com.syuan.myapplication.manage;

import com.syuan.myapplication.activity.MainActivity;

/**
 * Created by sy on 2017/6/4.
 */

public class MyActivityManager {
    private static MainActivity mainActivity=null;

    public static MainActivity getMainActivity() {
        if (mainActivity==null){
            mainActivity=new MainActivity();
        }
        return mainActivity;
    }

    public static void setMainActivity(MainActivity activity) {
        if (mainActivity==null)
        MyActivityManager.mainActivity = activity;
    }
}
