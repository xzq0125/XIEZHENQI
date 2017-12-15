package com.xiezhenqi.business.more.richtext;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.h5help.H5HelpActivity;

import butterknife.BindView;

public class RichTextActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.textView2)
    TextView textView2;

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

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false); //如果设置为false则不带下划线，true带有下划线
            }
        }, str.length() - 2, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //文字前景颜色，可以设置要改变颜色的文字
        spannableString1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.themeColor)),
                str.length() - 2, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        textView.setText(spannableString1);

        //设置添加链接,变色文字可以点击
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        /*
         * 改变字体大小  颜色 加换行
         */
        Spannable span = new SpannableString("2017"+"\n"+"-03-02");
        span.setSpan(new RelativeSizeSpan(1.5f), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(3.0f), 5, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        /*
         * BackgroundColorSpan设置背景色，为0代表不设置背景色默认为activity背景色
         */
        span.setSpan(new BackgroundColorSpan(Color.BLUE), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(0, 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(span);
    }
}
