package com.xiezhenqi.business.account;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.xiezhenqi.R;
import com.xiezhenqi.utils.ToastUtils;

public class ShareDialog extends Dialog implements View.OnClickListener {

    private Context context;

    public ShareDialog(Context context) {
        super(context, R.style.BottomTransparentDialogStyle);
        this.context = context;
        setContentView(R.layout.dlg_miniblog_share);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.share_tv_weichat).setOnClickListener(this);
        findViewById(R.id.share_tv_circle).setOnClickListener(this);
        findViewById(R.id.share_btn_close).setOnClickListener(this);
        findViewById(R.id.share_ly_outsize).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.share_tv_weichat:

                ToastUtils.showToast(context, "微信好友");
                dismiss();
                break;

            case R.id.share_tv_circle:
                ToastUtils.showToast(context, "朋友圈");
                dismiss();
                break;

            case R.id.share_btn_close:
            case R.id.share_ly_outsize:
                dismiss();
                break;
        }
    }

}
