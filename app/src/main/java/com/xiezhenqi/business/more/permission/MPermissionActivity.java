package com.xiezhenqi.business.more.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.LogUtils;

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

    @Override
    public void onClick(View v) {
        int index = ((ViewGroup) v.getParent()).indexOfChild(v);
        LogUtils.debug("XZQ", "requestCode = " + index);
        needPermission(index, (String) v.getTag());
    }

    public void onCamera(View view) {
        needCameraPermission();
    }

    public void onLocation(View view) {
        needLocationPermission();
    }

    public void onStorage(View view) {
        needStoragePermission();
    }

    public void onCameraStorage(View view) {
        needPermission(11, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void onCustom(View view) {
        needPermission(12, Manifest.permission.READ_SMS);
    }

}
