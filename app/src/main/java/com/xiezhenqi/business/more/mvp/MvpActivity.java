package com.xiezhenqi.business.more.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.more.mvp.login.LoginActivity;

/**
 * MvpActivity
 * Created by Wesley on 2018/6/4.
 */
public class MvpActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

    }

    public void login(View view) {
        LoginActivity.start(this);
    }
}
