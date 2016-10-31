package com.xiezhenqi.widget.progresswebview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 顶部带进度条的WebView
 * Created by sean on 2016/4/25.
 */
public class ProgressWebView extends WebView implements Animation.AnimationListener {

    private ProgressBar mProgressBar;//进度条
    private Animation barDismissAnim;//进度条消失动画
    private TextView titleTextView;//标题
    private int page = 0;
    private Map<Integer, String> titleMap = new TreeMap<>();

    public ProgressWebView(Context context) {
        super(context);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        barDismissAnim = AnimationUtils.loadAnimation(context, R.anim.progressbar_hide);
        barDismissAnim.setAnimationListener(this);
        setWebChromeClient(new WebChromeClient());
    }

    public void bindProgressBar(ProgressBar progressbar) {
        if (progressbar != null) {
            mProgressBar = progressbar;
            mProgressBar.setMax(100);
        }
    }

    public void bindTitle(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void setGoBackTitle() {
        if (this.titleTextView != null) {
            int backPage = page - 2;
            page--;
            if (!StringUtils.isNullOrEmpty(titleMap.get(backPage))) {
                this.titleTextView.setText(titleMap.get(backPage));
            }
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (mProgressBar != null) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.startAnimation(barDismissAnim);
                } else if (mProgressBar.getVisibility() != VISIBLE) {
                    mProgressBar.setVisibility(VISIBLE);
                }
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (titleTextView != null && !StringUtils.isNullOrEmpty(title)) {
                titleTextView.setText(title);
                titleMap.put(page, title);
                page++;
            }
        }
    }

    public interface onScrollChangeListener {
        /**
         * @param scrollX    the x position to scroll to
         * @param scrollY    the y position to scroll to
         * @param oldScrollX the oldX position
         * @param oldScrollY the oldY position
         */
        void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    private List<onScrollChangeListener> listeners;

    public void addOnScrollChangeListener(onScrollChangeListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        this.listeners.add(listener);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listeners != null) {
            for (onScrollChangeListener listener : listeners)
                listener.onScrollChanged(l, t, oldl, oldt);
        }
    }
}
