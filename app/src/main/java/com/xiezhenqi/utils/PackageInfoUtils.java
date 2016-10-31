package com.xiezhenqi.utils;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 版本工具类
 *
 * @author Alex
 */
public class PackageInfoUtils {

    /**
     * 获取版本号
     *
     * @param context Context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本名
     *
     * @param context Context
     * @return 版本名
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 比对是否为新版本
     *
     * @param context Context
     * @param version 比对版本号
     * @return 是否为新版本
     */
    public static boolean isNewVersion(Context context, String version) {
        String versionName = getVersionName(context);
        if (version == null || versionName == null)
            return false;
        String regEx = "[^0-9.]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(version);
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）
        version =  m.replaceAll("").trim();
        String[] strIns = versionName.split("\\.");
        String[] strNew = version.split("\\.");
        try {
            int versionCodeIns = Integer.parseInt(strIns[strIns.length - 1]);
            int versionCodeNew = Integer.parseInt(strNew[strNew.length - 1]);
            if (versionCodeNew > versionCodeIns) {
                return true;
            } else if (versionCodeNew == versionCodeIns) {
                int length = strIns.length > strNew.length ? strNew.length - 1 : strIns.length - 1;
                int ins;
                int nes;
                for (int i = 0; i < length; i++) {
                    ins = Integer.parseInt(strIns[i]);
                    nes = Integer.parseInt(strNew[i]);
                    if (nes > ins) {
                        return true;
                    } else if (nes < ins)
                        return false;
                }
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
