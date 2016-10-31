package com.xiezhenqi.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.List;

/**
 * AppUtils
 * Created by Tse on 2016/8/6.
 */
public class AppUtils {

    /**
     * 获取最大运存
     *
     * @param context Context
     * @return 最大运存
     */
    public static int getLargeMemoryClass(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        if (CompatPlus.isLargeHeap(context)) {
            memoryClass = CompatPlus.getLargeMemoryClass(am);
        }
        return memoryClass;
    }

    /**
     * 程序是否在前台运行
     *
     * @return 是否在前台运行
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the device
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager
                    .RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 程序是否可调试
     *
     * @param context Context
     * @return 程序是否可调试
     */
    public static boolean isDebuggable(Context context) {
        return (context.getApplicationInfo().flags &=
                ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
