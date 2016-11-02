package com.xiezhenqi.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具，(防止多次触发，一直弹toast)
 * Created by sean on 2016/8/8.
 */
public class ToastUtils {

    /**
     * 默认位置的toast
     */
    private static Toast mDefaultToast;

    /**
     * 顶部位置的toast
     */
    private static Toast mTopToast;

    /**
     * 中间位置的toast
     */
    private static Toast mCenterToast;

    /**
     * 在默认位置toast
     *
     * @param context Context
     * @param text    文本
     */
    public static void showToast(Context context, CharSequence text) {
        if (mDefaultToast == null) {
            mDefaultToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mDefaultToast.setText(text);
        }
        mDefaultToast.show();
    }

    /**
     * 在默认位置toast
     *
     * @param context Context
     * @param resId   文本资源ID
     */
    public static void showToast(Context context, @StringRes int resId) {
        if (mDefaultToast == null) {
            mDefaultToast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
        } else {
            mDefaultToast.setText(resId);
        }
        mDefaultToast.show();
    }

    /**
     * 在屏幕顶部toast
     *
     * @param context Context
     * @param text    文本
     */
    public static void showToastAtTop(Context context, CharSequence text) {
        if (mTopToast == null) {
            mTopToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            mTopToast.setGravity(Gravity.TOP, 0, 0);
        } else {
            mTopToast.setText(text);
        }
        mTopToast.show();
    }

    /**
     * 在屏幕顶部toast
     *
     * @param context Context
     * @param resId   文本资源ID
     */
    public static void showToastAtTop(Context context, @StringRes int resId) {
        if (mTopToast == null) {
            mTopToast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
            mTopToast.setGravity(Gravity.TOP, 0, 0);
        } else {
            mTopToast.setText(resId);
        }
        mTopToast.show();
    }

    /**
     * 在屏幕中间toast
     *
     * @param context Context
     * @param text    文本
     */
    public static void showToastAtCenter(Context context, CharSequence text) {
        if (mCenterToast == null) {
            mCenterToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            mCenterToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mCenterToast.setText(text);
        }
        mCenterToast.show();
    }

    /**
     * 在屏幕中间toast
     *
     * @param context Context
     * @param resId   文本资源ID
     */
    public static void showToastAtCenter(Context context, @StringRes int resId) {
        if (mCenterToast == null) {
            mCenterToast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
            mCenterToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mCenterToast.setText(resId);
        }
        mCenterToast.show();
    }

}
