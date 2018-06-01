package com.xiezhenqi.permission.util;

import android.Manifest;
import android.app.Activity;

import com.xiezhenqi.permission.MPermission;

import java.util.List;
import java.util.Locale;


/**
 * 权限提示工具
 * Created by Wesley on 2018/5/17.
 */

public class RequestUtil {

    /**
     * 获取授权标题
     *
     * @param args 参数
     * @return 授权标题
     */
    public static String getTitle(Object... args) {
        return String.format(Locale.getDefault(), "%1$s权限申请", args);
    }

    /**
     * 获取授权描述
     *
     * @param args 参数
     * @return 授权描述
     */
    public static String getMessage(Object... args) {
        return String.format(Locale.getDefault(), "请在\"设置-应用-本地网-权限管理\"中开启%1$s权限，%2$s", args);
    }

    //相机
    private static final String CAMERA_NAME = "相机";
    private static final String CAMERA_MSG = "开启后，你可以使用拍照、录像、扫一扫等功能。";

    public static String getCameraTitle() {
        return getTitle(CAMERA_NAME);
    }

    public static String getCameraMessage() {
        return getMessage(CAMERA_NAME, CAMERA_MSG);
    }

    //存储
    private static final String STORAGE_NAME = "存储";
    private static final String STORAGE_MSG = "开启后，本地网可以访问你的文件。";

    public static String getStorageTitle() {
        return getTitle(STORAGE_NAME);
    }

    public static String getStorageMessage() {
        return getMessage(STORAGE_NAME, STORAGE_MSG);
    }

    //定位
    private static final String LOCATION_NAME = "定位";
    private static final String LOCATION_MSG = "开启后，可以定位城市。";

    public static String getLocationTitle() {
        return getTitle(LOCATION_NAME);
    }

    public static String getLocationMessage() {
        return getMessage(LOCATION_NAME, LOCATION_MSG);
    }

    //录制
    private static final String RECORD_AUDIO_NAME = "麦克风";
    private static final String RECORD_AUDIO_MSG = "开启后，你可以进行音频录制。";

    public static String getRecordTitle() {
        return getTitle(RECORD_AUDIO_NAME);
    }

    public static String getRecordMessage() {
        return getMessage(RECORD_AUDIO_NAME, RECORD_AUDIO_MSG);
    }

    /**
     * 获取视频录制授权标题
     * 因为视频录制同时申请多个权限
     * 把用户拒绝的权限统计出来作为标题显示
     *
     * @param activity    Activity
     * @param permissions 视频录制所需权限
     * @return 已拒绝的视频录制授权标题集合
     */
    public static String getVideoTitle(Activity activity, String... permissions) {
        List<String> deniedPermissions = MPermission.getDeniedPermissions(activity, permissions);
        if (deniedPermissions == null)
            return null;
        StringBuilder builder = new StringBuilder();
        if (deniedPermissions.contains(Manifest.permission.CAMERA)) {
            builder.append(CAMERA_NAME).append("、");
        }
        if (deniedPermissions.contains(Manifest.permission.RECORD_AUDIO)) {
            builder.append(RECORD_AUDIO_NAME).append("、");
        }
        if (deniedPermissions.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            builder.append(STORAGE_NAME).append("、");
        }
        return getTitle(getName(builder));
    }

    public static String getVideoMessage() {
        return getMessage("视频录制相关", "开启后，你可以进行视频录制。");
    }

    /**
     * 获取拍照授权标题
     * 因为拍照同时申请多个权限
     * 把用户拒绝的权限统计出来作为标题显示
     *
     * @param activity    Activity
     * @param permissions 拍照所需权限
     * @return 已拒绝的拍照授权标题集合
     */
    public static String getTakePhotoTitle(Activity activity, String... permissions) {
        List<String> deniedPermissions = MPermission.getDeniedPermissions(activity, permissions);
        if (deniedPermissions == null)
            return null;
        StringBuilder builder = new StringBuilder();
        if (deniedPermissions.contains(Manifest.permission.CAMERA)) {
            builder.append(CAMERA_NAME).append("、");
        }
        if (deniedPermissions.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            builder.append(STORAGE_NAME);
        }
        return getTitle(getName(builder));
    }

    private static String getName(StringBuilder builder) {
        String str = builder.toString();
        return str.endsWith("、") ?
                builder.deleteCharAt(builder.length() - 1).toString()
                : str;
    }

    public static String getTakePhotoMessage() {
        return getMessage("拍照相关", "开启后，你可以进行拍照。");
    }

    public static String getDeniedPermissionsName(Activity activity, String... permissions) {

        return getTitle(LOCATION_NAME);
    }


    private static final String[] permissionName  = new String[10];

    static {
        for (int i = 0; i < 10; i++) {
            permissionName[i] = android.Manifest.permission.READ_CALENDAR;
        }
    }

}
