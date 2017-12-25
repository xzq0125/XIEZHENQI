package com.xiezhenqi.business.more.largedialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;


/**
 * LargeFontDialog
 * Created by sean on 2017/2/17.
 */

class LargeFontDialog extends Dialog implements View.OnClickListener {

    private TextView tvContent;

    LargeFontDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        setContentView(R.layout.dlg_large_font);
        tvContent = (TextView) findViewById(R.id.dlf_tv_content);
        tvContent.setOnClickListener(this);
        findViewById(R.id.dlf_outside).setOnClickListener(this);
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }

    public void setContent(CharSequence content) {
        tvContent.setText(content);
    }

    public void setContent(@StringRes int resId) {
        tvContent.setText(resId);
    }

    @Override
    public void onClick(View v) {
        cancel();
    }
}
