package com.xiezhenqi.base.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.xiezhenqi.newmvp.SimpleLoadingActivity;
import com.xiezhenqi.permission.MPermission;
import com.xiezhenqi.permission.annotation.OnMPermissionDenied;
import com.xiezhenqi.permission.annotation.OnMPermissionGranted;
import com.xiezhenqi.permission.annotation.OnMPermissionGrantedCustomRequestCode;
import com.xiezhenqi.permission.util.PermissionRequestCode;
import com.xiezhenqi.permission.util.ResultUtil;
import com.xiezhenqi.utils.LogUtils;

import java.util.List;

/**
 * Android 6.0(Marshmallow-棉花糖) 以上动态权限基类
 * 危险权限需要动态授权
 * 也就是运行时提示用户授权
 * 只有用户给予授权后才能进行一些跟权限相关的操作
 * Created by Wesley on 2018/6/2.
 */
@SuppressWarnings("all")
public abstract class MPermissionActivity extends SimpleLoadingActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private String[] permissions = null;

    public void needPermission(final int requestCode, final String... permissions) {
        if (permissions == null || permissions.length == 0)
            return;
        this.permissions = permissions;
        MPermission.needPermission(this, requestCode, permissions);
    }

    public void needPermission(final int requestCode, final List<String> permissionsList) {
        if (permissionsList == null || permissionsList.isEmpty())
            return;
        needPermission(requestCode, permissionsList.toArray(new String[permissionsList.size()]));
    }

    //请求日历权限
    public void needCalendarPermission() {
        needPermission(PermissionRequestCode.CALENDAR, Manifest.permission.WRITE_CALENDAR);
    }

    //请求相机权限
    public void needCameraPermission() {
        needPermission(PermissionRequestCode.CAMERA, Manifest.permission.CAMERA);
    }

    //请求通讯录权限
    public void needContactsPermission() {
        needPermission(PermissionRequestCode.CONTACTS, Manifest.permission.WRITE_CONTACTS);
    }

    //请求定位权限
    public void needLocationPermission() {
        needPermission(PermissionRequestCode.LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    //请求麦克风权限
    public void needMicroPermission() {
        needPermission(PermissionRequestCode.MICRO, Manifest.permission.RECORD_AUDIO);
    }

    //请求电话权限
    public void needPhonePermission() {
        needPermission(PermissionRequestCode.PHONE, Manifest.permission.CALL_PHONE);
    }

    //请求传感器权限
    public void needSensorsPermission() {
        needPermission(PermissionRequestCode.SENSORS, Manifest.permission.BODY_SENSORS);
    }

    //请求短信权限
    public void needSmsPermission() {
        needPermission(PermissionRequestCode.SMS, Manifest.permission.SEND_SMS);
    }

    //请求存储权限
    public void needStoragePermission() {
        needPermission(PermissionRequestCode.STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    /**
     * 请求授权失败
     *
     * @param requestCode 请求码
     */
    @OnMPermissionDenied()
    protected void requestPermissionDenied(final int requestCode) {
        final String title = ResultUtil.getDeniedPermissionsTitle(this, permissions);
        final String message = ResultUtil.getDeniedPermissionsDefaultMessage(this, permissions);
        showPermissionAlertDialog(title, message);
        LogUtils.debug("XZQ", title + "\t授权拒绝，请求码 requestCode = " + requestCode);
    }

    /**
     * 请求授权成功
     *
     * @param requestCode 请求码
     */
    @OnMPermissionGranted({
            PermissionRequestCode.CALENDAR,
            PermissionRequestCode.CAMERA,
            PermissionRequestCode.CONTACTS,
            PermissionRequestCode.LOCATION,
            PermissionRequestCode.MICRO,
            PermissionRequestCode.PHONE,
            PermissionRequestCode.SENSORS,
            PermissionRequestCode.SMS,
            PermissionRequestCode.STORAGE
    })
    protected void requestPermissionSuccess(final int requestCode) {
        String msg = null;
        if (requestCode == PermissionRequestCode.CALENDAR) {
            msg = "日历权限申请成功";
            onCalendarPermissionGot();
        } else if (requestCode == PermissionRequestCode.CAMERA) {
            msg = "相机权限申请成功";
            onCameraPermissionGot();
        } else if (requestCode == PermissionRequestCode.CONTACTS) {
            msg = "通讯录权限申请成功";
            onContactsPermissionGot();
        } else if (requestCode == PermissionRequestCode.LOCATION) {
            msg = "定位权限申请成功";
            onLocationPermissionGot();
        } else if (requestCode == PermissionRequestCode.MICRO) {
            msg = "麦克风权限申请成功";
            onMicroPermissionGot();
        } else if (requestCode == PermissionRequestCode.PHONE) {
            msg = "电话权限申请成功";
            onPhonePermissionGot();
        } else if (requestCode == PermissionRequestCode.SENSORS) {
            msg = "身体传感器权限申请成功";
            onSensorsPermissionGot();
        } else if (requestCode == PermissionRequestCode.SMS) {
            msg = "短信权限申请成功";
            onSmsPermissionGot();
        } else if (requestCode == PermissionRequestCode.STORAGE) {
            msg = "存储权限申请成功";
            onSensorsPermissionGot();
        }
        LogUtils.debug("XZQ", msg + "\t请求码 requestCode = " + requestCode);
    }

    /**
     * 自定义请求码请求授权成功
     *
     * @param requestCode 请求码
     */
    @OnMPermissionGrantedCustomRequestCode()
    protected void requestPermissionGrantedCustom(final int requestCode) {
        LogUtils.debug("XZQ", "自定义请求码授权成功，请求码 requestCode = " + requestCode);
    }

    private AlertDialog mPermissionAlertDialog = null;

    protected void showPermissionAlertDialog(String title, String message) {
        if (mPermissionAlertDialog == null) {
            mPermissionAlertDialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPermissionAlertDialog.dismiss();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                            onPermissionDialogPositiveClick();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPermissionAlertDialog.dismiss();
                            onPermissionDialogNegativeClick();
                        }
                    })
                    .show();
        } else {
            mPermissionAlertDialog.setTitle(title);
            mPermissionAlertDialog.setMessage(message);
        }
        if (!mPermissionAlertDialog.isShowing())
            mPermissionAlertDialog.show();
    }

    protected void onCalendarPermissionGot() {
    }

    protected void onCameraPermissionGot() {
    }

    protected void onContactsPermissionGot() {
    }

    protected void onLocationPermissionGot() {
    }

    protected void onMicroPermissionGot() {
    }

    protected void onPhonePermissionGot() {
    }

    protected void onSensorsPermissionGot() {
    }

    protected void onSmsPermissionGot() {
    }

    protected void onStoragePermissionGot() {
    }

    protected void onPermissionDialogPositiveClick() {
    }

    protected void onPermissionDialogNegativeClick() {
    }

}
