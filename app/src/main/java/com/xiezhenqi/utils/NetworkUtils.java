package com.xiezhenqi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 网络工具类
 * Created by sean on 2016/8/8.
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * 网络类型
     */
    public enum NetworkType {
        NONE, UNKNOWN, WIFI, _2G, _3G, _4G
    }

    /**
     * 网络是否已连接
     *
     * @param context Context
     * @return 是否已连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        } else {
            Log.e(TAG, "Can't get ConnectivityManager");
        }
        return false;
    }

    /**
     * 检查WIFI已连接
     *
     * @param context Context
     * @return 是否已连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
            return ni != null && ni.isConnected()
                    && ni.getType() == ConnectivityManager.TYPE_WIFI;
        } else {
            Log.e(TAG, "Can't get ConnectivityManager");
        }
        return false;
    }

    /**
     * 检查手机网络已连接
     *
     * @param context Context
     * @return 是否已连接
     */
    public static boolean isMobileConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
            return ni != null && ni.isConnected()
                    && ni.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            Log.e(TAG, "Can't get ConnectivityManager");
        }
        return false;
    }

    /**
     * 获取手机连接的网络类型(2G,3G,4G)
     *
     * @param context Context
     * @return 手机网络类型
     */
    public static NetworkType getMobileNetworkType(Context context) {
        TelephonyManager tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = tm.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS: // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE: // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA: // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_1xRTT:// ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_IDEN: // ~25 kbps
                return NetworkType._2G;
            case TelephonyManager.NETWORK_TYPE_UMTS: // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0: // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A: // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA: // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSUPA: // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA: // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B: // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_EHRPD: // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP: // ~ 10-20 Mbps
                return NetworkType._3G;
            case TelephonyManager.NETWORK_TYPE_LTE: // ~ 10+ Mbps
                return NetworkType._4G;
            default:
                return NetworkType.UNKNOWN;
        }
    }

    /**
     * 获取网络类型
     *
     * @param context Context
     * @return 网络类型
     */
    public static NetworkType getNetworkType(Context context) {
        if (isConnected(context)) {
            if (isWifiConnected(context)) {
                return NetworkType.WIFI;
            } else if (isMobileConnected(context)) {
                return getMobileNetworkType(context);
            } else {
                return NetworkType.UNKNOWN;
            }
        } else {
            return NetworkType.NONE;
        }
    }

    /**
     * 获取移动网络运营商名称
     * <p>如中国联通、中国移动、中国电信
     *
     * @param context Context
     * @return 网络运营商名称
     */
    @Nullable
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 移动终端类型
     */
    public enum PhoneType {
        NONE, GSM, CDMA, SIP
    }

    /**
     * 获取移动终端类型
     * <pre>
     * PHONE_TYPE_NONE  : 0 手机制式未知
     * PHONE_TYPE_GSM   : 1 手机制式为GSM，移动和联通
     * PHONE_TYPE_CDMA  : 2 手机制式为CDMA，电信
     * PHONE_TYPE_SIP   : 3 SIP
     *  <pre/>
     * @param context Context
     * @return 移动终端类型
     */
    public static PhoneType getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (tm.getPhoneType()) {
            default:
            case TelephonyManager.PHONE_TYPE_NONE:
                return PhoneType.NONE;
            case TelephonyManager.PHONE_TYPE_GSM:
                return PhoneType.GSM;
            case TelephonyManager.PHONE_TYPE_CDMA:
                return PhoneType.CDMA;
            case TelephonyManager.PHONE_TYPE_SIP:
                return PhoneType.SIP;
        }
    }
}
