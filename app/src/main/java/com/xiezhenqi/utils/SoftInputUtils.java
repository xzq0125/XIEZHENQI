package com.xiezhenqi.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入法工具类
 * Created by sean on 2016/8/8.
 */
public class SoftInputUtils {

    /**
     * 打开输入法
     *
     * @param context   Context
     * @param focusView 拥有焦点的View
     */
    public static void show(Context context, View focusView) {
        if (context == null) {
            return;
        }
        if (focusView != null) {
            focusView.requestFocus();
            InputMethodManager imm = ((InputMethodManager) (context
                    .getSystemService(Context.INPUT_METHOD_SERVICE)));
            imm.showSoftInput(focusView, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 关闭输入法
     *
     * @param activity Activity
     */
    public static void hide(Activity activity) {
        if (activity == null) {
            return;
        }
        hide(activity, activity.getCurrentFocus());
    }

    /**
     * 关闭输入法
     *
     * @param context   Context
     * @param focusView 拥有焦点的View
     */
    public static void hide(Context context, View focusView) {
        if (context == null) {
            return;
        }
        if (focusView != null) {
            InputMethodManager imm = ((InputMethodManager) (context
                    .getSystemService(Context.INPUT_METHOD_SERVICE)));
            imm.hideSoftInputFromWindow(focusView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 是否已开启输入法
     *
     * @param context Context
     * @return 是否已开启输入法
     */
    public static boolean isShowing(Activity context) {
        return context != null && context.getWindow().getAttributes()
                .softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }

}
