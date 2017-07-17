package com.Syuan.azy170331.activitis.utils;

import android.content.Context;
import android.util.Log;


public class LogUtil {
    public static void e(String String) {
            Log.e("==========", "==========" + String);
    }

    public static void e(String str0, String str1) {
            Log.e("==========", "==========" + str0 + "=" + str1);
    }

    public static void e(Context context, String String) {
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + String);
    }

    public static void e(Context context, int Int) {
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + Int);
    }

    public static void e(Context context, float Float) {
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + Float);
    }

    public static void e(Context context, double Double) {
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + Double);
    }
}
