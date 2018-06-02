package com.xiezhenqi.permission.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;
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
        return String.format(Locale.getDefault(), "请在\"设置-应用-本地网-权限管理\"中开启%1$s权限，开启后，%2$s", args);
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

    public static String getDeniedPermissionsTitle(Activity activity, String... permissions) {
        List<String> deniedPermissions = MPermission.getDeniedPermissions(activity, permissions);
        if (deniedPermissions == null || deniedPermissions.isEmpty())
            return null;
        StringBuilder builder = new StringBuilder();
        for (String value : deniedPermissions) {
            final String name = transformName(value)[0];
            builder.append(name).append("、");
        }
        return getTitle(builder.deleteCharAt(builder.length() - 1).toString());
    }

    public static String getDeniedPermissionsDefaultMessage(Activity activity, String... permissions) {
        return getDeniedPermissionsCustomMessage(activity, "需要的", "才能正常使用", permissions);
    }

    public static String getDeniedPermissionsCustomMessage(Activity activity, String name, String description, String... permissions) {
        List<String> deniedPermissions = MPermission.getDeniedPermissions(activity, permissions);
        if (deniedPermissions == null || deniedPermissions.isEmpty())
            return null;
        if (deniedPermissions.size() == 1) {
            String[] str = transformName(deniedPermissions.get(0));
            return getMessage(str[0], str[1]);
        } else {
            return getMessage(name, description);
        }
    }

    private static final String CALENDAR_NAME = "日历";
    private static final String CONTACTS_NAME = "通讯录";
    private static final String MICRO_NAME = "麦克风";
    private static final String PHONE_NAME = "电话";
    private static final String SENSORS_NAME = "身体传感器";
    private static final String SMS_NAME = "短信";

    /**
     * 根据权限转换相应的名称
     *
     * @param permission 某个权限
     * @return 名称
     */
    private static String[] transformName(String permission) {

        @StringRes int nameResId = 0;
        @StringRes int descriptionResId = 0;

        //日历
        if (TextUtils.equals(Manifest.permission.READ_CALENDAR, permission)
                || TextUtils.equals(Manifest.permission.WRITE_CALENDAR, permission)) {
            nameResId = R.string.permission_name_calendar;
            descriptionResId = R.string.permission_description_calendar;
        }

        //相机
        if (TextUtils.equals(Manifest.permission.CAMERA, permission)) {
            nameResId = R.string.permission_name_camera;
            descriptionResId = R.string.permission_description_camera;
        }

        //通讯录
        if (TextUtils.equals(Manifest.permission.READ_CONTACTS, permission)
                || TextUtils.equals(Manifest.permission.WRITE_CONTACTS, permission)
                || TextUtils.equals(Manifest.permission.GET_ACCOUNTS, permission)) {
            nameResId = R.string.permission_name_contacts;
            descriptionResId = R.string.permission_description_contacts;
        }

        //定位
        if (TextUtils.equals(Manifest.permission.ACCESS_FINE_LOCATION, permission)
                || TextUtils.equals(Manifest.permission.ACCESS_COARSE_LOCATION, permission)) {
            nameResId = R.string.permission_name_location;
            descriptionResId = R.string.permission_description_location;
        }

        //麦克风
        if (TextUtils.equals(Manifest.permission.RECORD_AUDIO, permission)) {
            nameResId = R.string.permission_name_micro;
            descriptionResId = R.string.permission_description_micro;
        }

        //电话
        if (TextUtils.equals(Manifest.permission.READ_PHONE_STATE, permission)
                || TextUtils.equals(Manifest.permission.READ_PHONE_NUMBERS, permission)//这个权限直接回调失败，没有弹出授权对话框
                || TextUtils.equals(Manifest.permission.CALL_PHONE, permission)
                || TextUtils.equals(Manifest.permission.ANSWER_PHONE_CALLS, permission)//这个权限直接回调失败，没有弹出授权对话框
                || TextUtils.equals(Manifest.permission.READ_CALL_LOG, permission)
                || TextUtils.equals(Manifest.permission.WRITE_CALL_LOG, permission)
                || TextUtils.equals(Manifest.permission.ADD_VOICEMAIL, permission)//不可申请的权限
                || TextUtils.equals(Manifest.permission.USE_SIP, permission)
                || TextUtils.equals(Manifest.permission.PROCESS_OUTGOING_CALLS, permission)) {
            nameResId = R.string.permission_name_phone;
            descriptionResId = R.string.permission_description_phone;
        }

        //身体传感器
        if (TextUtils.equals(Manifest.permission.BODY_SENSORS, permission)) {
            nameResId = R.string.permission_name_sensors;
            descriptionResId = R.string.permission_description_sensors;
        }

        //短信
        if (TextUtils.equals(Manifest.permission.SEND_SMS, permission)
                || TextUtils.equals(Manifest.permission.RECEIVE_SMS, permission)
                || TextUtils.equals(Manifest.permission.READ_SMS, permission)
                || TextUtils.equals(Manifest.permission.RECEIVE_WAP_PUSH, permission)
                || TextUtils.equals(Manifest.permission.RECEIVE_MMS, permission)) {
            nameResId = R.string.permission_name_sms;
            descriptionResId = R.string.permission_description_sms;
        }

        //存储
        if (TextUtils.equals(Manifest.permission.READ_EXTERNAL_STORAGE, permission)
                || TextUtils.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE, permission)) {
            nameResId = R.string.permission_name_storage;
            descriptionResId = R.string.permission_description_storage;
        }

        final Context context = XZQApplication.getContext();
        final String name = context.getString(nameResId);
        final String description = context.getString(descriptionResId);

        return new String[]{name, description};
    }

    private static final String[] permissionName = new String[10];

    static {
        for (int i = 0; i < 10; i++) {
            permissionName[i] = android.Manifest.permission.READ_CALENDAR;
        }
    }

}
