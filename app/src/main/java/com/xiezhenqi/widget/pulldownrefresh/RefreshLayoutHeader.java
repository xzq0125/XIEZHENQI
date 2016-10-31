package com.xiezhenqi.widget.pulldownrefresh;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.MaterialLoadingProgressDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.utils.AnimatorUtils;
import com.xiezhenqi.widget.stateframelayout.StateFrameLayout;


/**
 * RefreshLayoutHeader
 * Created by Tse on 2016/10/30.
 */

public class RefreshLayoutHeader extends FrameLayout implements RefreshLayout.RefreshHeader, RefreshLayout.OnRefreshListener {

    private StateFrameLayout loading;
    private TextView tvOk;

    public RefreshLayoutHeader(Context context) {
        this(context, null);
    }

    public RefreshLayoutHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayoutHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View loadingView = LayoutInflater.from(context).inflate(R.layout.item_refresh_header, this);
        this.loading = (StateFrameLayout) loadingView.findViewById(R.id.common_pull_down_refresh_loading);
        tvOk = (TextView) loadingView.findViewById(R.id.common_pull_down_refresh_ok);
        this.loading.setStateDrawables(
                new MaterialLoadingProgressDrawable(this.loading, ContextCompat.getColor(context, R.color.transparent),
                        false, ContextCompat.getColor(context, R.color.themeColor)),
                null, null);
    }

    @Override
    public void reset() {
        tvOk.setVisibility(GONE);
    }

    @Override
    public void pull() {
    }

    @Override
    public void refreshing() {
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state) {
    }

    @Override
    public void complete() {
        loading.normal();
        tvOk.setVisibility(VISIBLE);
        AnimatorUtils.alpha(tvOk, 0.5f, 1f, 500);
    }

    @Override
    public void onRefresh() {
        loading.loading();
        tvOk.setVisibility(GONE);
    }
}
