package com.xiezhenqi.business.more.largedialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import butterknife.OnClick;

public class LargeDialogActivity extends BaseActivity {

    private LargeFontDialog largeFontDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_large_dialog;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn)
    public void onClick() {
        if (largeFontDialog == null)
            largeFontDialog = new LargeFontDialog(this);
        largeFontDialog.show();
    }
}
