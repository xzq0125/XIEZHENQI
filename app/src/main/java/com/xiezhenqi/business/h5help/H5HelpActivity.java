package com.xiezhenqi.business.h5help;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.utils.StringUtils;
import com.xiezhenqi.widget.progresswebview.ProgressWebView;


/**
 * H5帮助说明
 * Created by sean on 2016/5/27.
 */
public class H5HelpActivity extends BaseActivity implements View.OnClickListener {

    private static final String EXTRA_URL = "extra_url";     // 目标url
    private final H5HelpActivity me = this;
    private ProgressWebView webH5Help;
    private ImageButton ibtnClose;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_h5help;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

        String url = getIntent().getStringExtra(EXTRA_URL);

        ibtnClose = (ImageButton) findViewById(R.id.h5help_ibtn_close);
        ImageButton ibtnBack = (ImageButton) findViewById(R.id.h5help_ibtn_back);
        ibtnClose.setOnClickListener(me);
        ibtnBack.setOnClickListener(me);

        webH5Help = (ProgressWebView) findViewById(R.id.h5help_web);
        webH5Help.bindProgressBar((ProgressBar) findViewById(R.id.h5help_progress_bar));
        webH5Help.bindTitle((TextView) findViewById(R.id.h5help_tv_title));
        webH5Help.requestFocusFromTouch();
        WebSettings webSettings = webH5Help.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js

        //设置加载进来的页面自适应手机屏幕,这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小。
        //webSettings.setUseWideViewPort(true);
        //webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(true);  //支持缩放
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setBuiltInZoomControls(true); //设置支持缩放
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDisplayZoomControls(false);  //关闭放大缩小按钮
        webH5Help.setWebViewClient(new MyWebViewClient());
        webH5Help.loadUrl(url);//目标url
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.h5help_ibtn_close: {
                finish();
            }
            break;

            case R.id.h5help_ibtn_back: {
                if (webH5Help.canGoBack()) {
                    webH5Help.goBack();
                    webH5Help.setGoBackTitle();
                    ibtnClose.setVisibility(View.VISIBLE);
                } else
                    finish();
            }
            break;
        }

    }

    // 监听 所有点击的链接，如果拦截到我们需要的，就跳转到相对应的页面。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);

        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webH5Help.canGoBack()) {
            webH5Help.goBack();
            webH5Help.setGoBackTitle();
            ibtnClose.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 启动H5页面(帮助说明)
     *
     * @param context context
     * @param url     url
     */
    public static void start(Context context, String url) {
        if (context != null && !StringUtils.isNullOrEmpty(url))
            context.startActivity(new Intent(context, H5HelpActivity.class)
                    .putExtra(EXTRA_URL, url));
    }

}
