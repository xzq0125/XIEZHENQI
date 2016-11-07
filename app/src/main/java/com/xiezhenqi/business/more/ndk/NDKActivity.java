package com.xiezhenqi.business.more.ndk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NDKActivity extends BaseActivity {

    @Bind(android.R.id.text1)
    TextView tvText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ndk;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }
}
