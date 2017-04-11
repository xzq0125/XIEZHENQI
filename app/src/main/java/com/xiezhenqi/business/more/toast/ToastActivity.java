package com.xiezhenqi.business.more.toast;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;

import es.dmoral.toasty.Toasty;

public class ToastActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toast;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onErrorClick(View view) {
        Toasty.error(this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
    }

    public void onSuccessClick(View view) {
        Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
    }

    public void onInfoClick(View view) {
        Toasty.info(this, "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
    }

    public void onWarningClick(View view) {
        Toasty.warning(this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
    }

    public void onNormalClick(View view) {
        Toasty.normal(this, "Normal toast w/o icon").show();
    }

    public void onCustomClick(View view) {
        Toasty.custom(this, "I'm a custom Toast", R.mipmap.ic_launcher, Color.RED, Color.GREEN, 1000, true, true).show();
    }
}
