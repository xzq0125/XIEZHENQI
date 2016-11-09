package com.xiezhenqi.business.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.utils.StringUtils;


/**
 * 通用添加文本Dialog
 * Created by sean on 2016/8/25.
 */
public abstract class AddTextDialog extends Dialog implements View.OnClickListener, DialogInterface.OnDismissListener {

    private final EditText edtText;
    private TextCallback callback;
    private Object tag;

    public AddTextDialog(Context context, TextCallback callBack) {
        super(context, R.style.TransparentDialogStyle);
        this.callback = callBack;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_add_text);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        setOnDismissListener(this);
        edtText = (EditText) findViewById(R.id.dat_edt_text);
        edtText.setHint(getTextHint(context));
        TextView tvTitle = (TextView) findViewById(R.id.dat_title);
        TextView tvCancel = (TextView) findViewById(R.id.dat_cancel);
        TextView tvConfirm = (TextView) findViewById(R.id.dat_confirm);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvTitle.setText(getTitle(context));
        tvCancel.setText(getCancelButtonText(context));
        tvConfirm.setText(getConfirmButtonText(context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dat_cancel:
                dismiss();
                break;

            case R.id.dat_confirm:
                if (edtText != null) {
                    String text = edtText.getText().toString().trim();
                    if (!StringUtils.isNullOrEmpty(text) && callback != null) {
                        callback.callbackText(this, text);
                    }
                }
                dismiss();
                break;
        }
    }

    public void setTextCallback(TextCallback callback) {
        this.callback = callback;
    }

    public void setText(String text) {
        if (edtText != null && !StringUtils.isNullOrEmpty(text)) {
            edtText.setText(text);
            edtText.setSelection(text.length());
        }
    }

    /**
     * 获取标题
     *
     * @param context Context
     * @return 标题
     */
    protected abstract String getTitle(Context context);

    /**
     * 获取文本提示语
     *
     * @param context Context
     * @return 文本提示语
     */
    protected abstract String getTextHint(Context context);

    /**
     * 获取取消按钮文本
     *
     * @param context Context
     * @return 取消按钮文本
     */
    protected String getCancelButtonText(Context context) {
        return context.getString(R.string.cancel);
    }

    /**
     * 获取确定按钮文本
     *
     * @param context Context
     * @return 确定按钮文本
     */
    protected String getConfirmButtonText(Context context) {
        return context.getString(R.string.confirm);
    }

    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (edtText != null) {
            edtText.setText("");
        }
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public interface TextCallback {
        /**
         * 回调已经填写的文本,只有按下确定按钮并且文本不为空的时候才会回调此方法
         *
         * @param addTextDialog
         * @param text 文本
         */
        void callbackText(AddTextDialog addTextDialog, String text);
    }
}
