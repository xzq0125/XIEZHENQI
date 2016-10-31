package com.xiezhenqi.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * 剪切板工具
 * Created by sean on 2016/3/11.
 */
public class ClipboardUtils {

    /**
     * 设置文本到系统剪切板
     *
     * @param context Context
     * @param text    文本
     * @return
     */
    public static boolean setText2Clipboard(Context context, CharSequence text) {
        if (context == null || TextUtils.isEmpty(text)) return false;
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(null, text));
        return true;
    }
}
