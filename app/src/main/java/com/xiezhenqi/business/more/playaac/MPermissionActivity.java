package com.xiezhenqi.business.more.playaac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import butterknife.BindView;

public class MPermissionActivity extends BaseActivity {

    @BindView(android.R.id.title)
    TextView title;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText("动态权限");
    }
}
