package com.xiezhenqi.business.more.richtext;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.h5help.H5HelpActivity;

import butterknife.BindView;

public class RichTextActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rich_text;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        String str = textView.getText().toString();
        SpannableString spannableString1 = new SpannableString(str);

        spannableString1.setSpan(new ClickableSpan() {
            public void onClick(View widget) {
                H5HelpActivity.start(RichTextActivity.this, "https://www.baidu.com");
            }
        }, str.length() - 2, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.themeColor)),
                str.length() - 2, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        spannableString1.setSpan(new ClickableSpan() {
            public void onClick(View widget) {
                H5HelpActivity.start(RichTextActivity.this, "https://www.baidu.com");
            }
        }, 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.themeColor)), 5, 7, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannableString1);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
