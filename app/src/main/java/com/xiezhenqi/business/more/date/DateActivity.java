package com.xiezhenqi.business.more.date;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DateActivity extends BaseActivity {

    @Bind(android.R.id.title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_date;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
    }
}
