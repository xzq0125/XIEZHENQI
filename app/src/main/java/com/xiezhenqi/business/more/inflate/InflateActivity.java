package com.xiezhenqi.business.more.inflate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.LogUtils;

import butterknife.BindView;

public class InflateActivity extends BaseActivity {

    @BindView(R.id.root)
    ViewGroup vgRoot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inflate;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(this);

        inflater.inflate(R.layout.layout_inflate, vgRoot, true);

        for (int i = 0; i < vgRoot.getChildCount(); i++) {
            LogUtils.debug("XZQ", "vgRoot.getChildAt" + i + " = " + vgRoot.getChildAt(i));
        }


    }

}








