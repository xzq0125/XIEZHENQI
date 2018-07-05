package com.xiezhenqi.utils;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

import com.xiezhenqi.XZQApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Toast工具，(防止多次触发，一直弹toast)
 * Created by sean on 2016/8/8.
 */
@SuppressWarnings("all")
public class ToastUtils {

    @IntDef({LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    /**
     * Show the view or text notification for a short period of time.  This time
     * could be user-definable.  This is the default.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = 0;

    /**
     * Show the view or text notification for a long period of time.  This time
     * could be user-definable.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = 1;

    private static Context sContext = XZQApplication.getContext();

    /**
     * 默认位置的toast
     */
    private static Toast sDefaultToast;

    /**
     * 顶部位置的toast
     */
    private static Toast sTopToast;

    /**
     * 中间位置的toast
     */
    private static Toast sCenterToast;

    /**
     * 在默认位置toast
     *
     * @param text 文本
     */
    public static void show(CharSequence text) {
        defShow(text, Toast.LENGTH_SHORT);
    }

    /**
     * 在默认位置toast
     *
     * @param resId 文本资源ID
     */
    public static void show(@StringRes int resId) {
        show(sContext.getText(resId));
    }

    /**
     * 在默认位置长toast
     *
     * @param text 文本
     */
    public static void showLong(CharSequence text) {
        defShow(text, Toast.LENGTH_LONG);
    }

    /**
     * 在默认位置长toast
     *
     * @param resId 文本资源ID
     */
    public static void showLong(@StringRes int resId) {
        showLong(sContext.getText(resId));
    }

    /**
     * 在默认位置toast
     *
     * @param text     文本
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     */
    private static void defShow(CharSequence text, @Duration int duration) {
        if (sDefaultToast == null) {
            sDefaultToast = Toast.makeText(sContext, text, duration);
        } else {
            sDefaultToast.setText(text);
        }
        sDefaultToast.setDuration(duration);
        sDefaultToast.show();
    }

    /**
     * 在顶部位置toast
     *
     * @param text 文本
     */
    public static void showTop(CharSequence text) {
        topShow(text, Toast.LENGTH_SHORT);
    }

    /**
     * 在顶部位置toast
     *
     * @param resId 文本资源ID
     */
    public static void showTop(@StringRes int resId) {
        showTop(sContext.getText(resId));
    }

    /**
     * 在顶部位置长toast
     *
     * @param text 文本
     */
    public static void showTopLong(CharSequence text) {
        topShow(text, Toast.LENGTH_LONG);
    }

    /**
     * 在顶部位置长toast
     *
     * @param resId 文本资源ID
     */
    public static void showTopLong(@StringRes int resId) {
        showTopLong(sContext.getText(resId));
    }

    /**
     * 在顶部位置toast
     *
     * @param text     文本
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     */
    private static void topShow(CharSequence text, @Duration int duration) {
        if (sTopToast == null) {
            sTopToast = Toast.makeText(sContext, text, duration);
            sTopToast.setGravity(Gravity.TOP, 0, 0);
        } else {
            sTopToast.setText(text);
        }
        sTopToast.setDuration(duration);
        sTopToast.show();
    }

    /**
     * 在中间位置toast
     *
     * @param text 文本
     */
    public static void showCenter(CharSequence text) {
        centerShow(text, Toast.LENGTH_SHORT);
    }

    /**
     * 在中间位置toast
     *
     * @param resId 文本资源ID
     */
    public static void showCenter(@StringRes int resId) {
        showCenter(sContext.getText(resId));
    }

    /**
     * 在中间位置长toast
     *
     * @param text 文本
     */
    public static void showCenterLong(CharSequence text) {
        centerShow(text, Toast.LENGTH_LONG);
    }

    /**
     * 在中间位置长toast
     *
     * @param resId 文本资源ID
     */
    public static void showCenterLong(@StringRes int resId) {
        showCenterLong(sContext.getText(resId));
    }

    /**
     * 在中间位置toast
     *
     * @param text     文本
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     */
    private static void centerShow(CharSequence text, @Duration int duration) {
        if (sCenterToast == null) {
            sCenterToast = Toast.makeText(sContext, text, duration);
            sCenterToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sCenterToast.setText(text);
        }
        sCenterToast.setDuration(duration);
        sCenterToast.show();
    }

}
