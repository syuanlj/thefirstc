package com.syuan.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/2/21.
 */

public class SharedPreferencesUtil {
    /**
     *
     * @param context
     * @return
     */
    public static SharedPreferences getUserSP(Context context){
        SharedPreferences sp=context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sp;
    }

    /**
     *
     * @param context
     * @return
     */
    public static SharedPreferences.Editor getUserED(Context context){


        return getUserSP(context).edit();

    }

}
