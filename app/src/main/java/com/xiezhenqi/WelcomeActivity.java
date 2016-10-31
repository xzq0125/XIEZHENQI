package com.xiezhenqi;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.home.HomeActivity;


/**
 * 欢迎界面
 */
public class WelcomeActivity extends BaseActivity implements Runnable {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().postDelayed(this, 1500);
    }

    @Override
    public void run() {

        if (isFinishing())
            return;

        HomeActivity.start(this);

        finish();
    }
}
