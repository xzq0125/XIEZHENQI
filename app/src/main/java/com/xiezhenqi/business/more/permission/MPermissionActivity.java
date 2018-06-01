package com.xiezhenqi.business.more.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.permission.MPermission;
import com.xiezhenqi.permission.annotation.OnMPermissionDenied;
import com.xiezhenqi.permission.annotation.OnMPermissionGranted;
import com.xiezhenqi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MPermissionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.llyt)
    LinearLayout linearLayout;
    private static List<String> strings = new ArrayList<>();

    static {
        strings.add("READ_CALENDAR");
        strings.add("WRITE_CALENDAR");

        strings.add("CAMERA");

        strings.add("READ_CONTACTS");
        strings.add("WRITE_CONTACTS");
        strings.add("GET_ACCOUNTS");

        strings.add("ACCESS_FINE_LOCATION");
        strings.add("ACCESS_COARSE_LOCATION");

        strings.add("RECORD_AUDIO");

        strings.add("READ_PHONE_STATE");
        strings.add("READ_PHONE_NUMBERS");
        strings.add("CALL_PHONE");
        strings.add("ANSWER_PHONE_CALLS");
        strings.add("READ_CALL_LOG");
        strings.add("WRITE_CALL_LOG");
        strings.add("ADD_VOICEMAIL");
        strings.add("USE_SIP");
        strings.add("PROCESS_OUTGOING_CALLS");

        strings.add("BODY_SENSORS");

        strings.add("SEND_SMS");
        strings.add("RECEIVE_SMS");
        strings.add("READ_SMS");
        strings.add("RECEIVE_WAP_PUSH");
        strings.add("RECEIVE_MMS");

        strings.add("READ_EXTERNAL_STORAGE");
        strings.add("WRITE_EXTERNAL_STORAGE");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText("动态权限");
        for (int i = 0; i < strings.size(); i++) {
            Button button = new Button(this);
            button.setText(strings.get(i));
            button.setOnClickListener(this);
            button.setTag("android.permission." + strings.get(i));
            linearLayout.addView(button);
        }
    }

    private static String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA};


    @OnMPermissionDenied(requestCode = 11)
    public void requestLocationDenied() {
        ToastUtils.showToast("获取权限失败");
        //showPermissionAlertDialog(RequestUtil.getDeniedPermissionsName(this, permission), RequestUtil.getLocationMessage());
    }

    @OnMPermissionGranted(requestCode = 11)
    public void requestLocationSuccess() {
        ToastUtils.showToast("定位权限成功");
    }

    private AlertDialog mPermissionAlertDialog;

    public boolean showPermissionAlertDialog(String title, String message) {
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
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPermissionAlertDialog.dismiss();
                        }
                    })
                    .show();
        } else {
            mPermissionAlertDialog.setTitle(title);
            mPermissionAlertDialog.setMessage(message);
        }
        if (!mPermissionAlertDialog.isShowing())
            mPermissionAlertDialog.show();
        return true;
    }

    @Override
    public void onClick(View v) {
        MPermission.needPermission(this,
                11,
                (String) v.getTag());
    }
}
