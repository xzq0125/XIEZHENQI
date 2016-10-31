package com.xiezhenqi.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * 状态栏工具类
 * Created by Alex on 2016/7/14.
 */
public class StatusBarUtils {

    /**
     * 全屏
     *
     * @param activity Activity
     */
    @TargetApi(19)
    public static void setLayoutFullscreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity Activity
     * @param color    颜色
     */
    @TargetApi(21)
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        activity.getWindow().setStatusBarColor(color);
    }
}
