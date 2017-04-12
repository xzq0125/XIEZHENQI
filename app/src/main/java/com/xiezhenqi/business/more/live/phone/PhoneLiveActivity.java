package com.xiezhenqi.business.more.live.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

public class PhoneLiveActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_live;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, PhoneLiveActivity.class);
        context.startActivity(starter);
    }
}
