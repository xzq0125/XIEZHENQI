package com.xiezhenqi.widget.pulldownrefresh;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.MaterialLoadingProgressDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.widget.stateframelayout.StateFrameLayout;


/**
 * RefreshLayoutHeader
 * Created by Tse on 2016/10/30.
 */

public class RefreshLayoutHeader extends FrameLayout implements RefreshLayout.RefreshHeader, RefreshLayout.OnRefreshListener {

    /**
     * 是否显示箭头
     */
    private boolean isShowArrow = true;
    private final StateFrameLayout loading;
    private final TextView tvOk;
    private final ImageView ivArrow;

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
        ivArrow = (ImageView) loadingView.findViewById(R.id.common_pull_down_refresh_arrow);
        ivArrow.setVisibility(isShowArrow ? VISIBLE : GONE);
        this.loading.setStateDrawables(
                new MaterialLoadingProgressDrawable(this.loading, ContextCompat.getColor(context, R.color.transparent),
                        false, ContextCompat.getColor(context, R.color.themeColor)),
                null, null);
    }

    @Override
    public void reset() {
        ViewCompat.setAlpha(tvOk, 0);
    }

    @Override
    public void pull() {
    }

    @Override
    public void refreshing() {
    }

    private boolean isUpward;

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, RefreshLayout.State state) {
        if (!isShowArrow)
            return;

        if (state == RefreshLayout.State.PULL) {
            ViewCompat.setAlpha(ivArrow, 1);
            if (currentPos > refreshPos) {
                if (isUpward)
                    return;
                isUpward = true;
                ObjectAnimator rotation = ObjectAnimator.ofFloat(ivArrow, "rotation", 0, -180);
                rotation.setDuration(300);
                rotation.start();
            } else {
                if (!isUpward)
                    return;
                isUpward = false;
                ObjectAnimator rotation = ObjectAnimator.ofFloat(ivArrow, "rotation", 180, 0);
                rotation.setDuration(300);
                rotation.start();
            }

        } else {

            ViewCompat.setAlpha(ivArrow, 0);

        }
    }

    @Override
    public void complete() {
        loading.normal();
        ViewCompat.setAlpha(tvOk, 1);
    }

    @Override
    public void onRefresh() {
        loading.loading();
        ViewCompat.setAlpha(tvOk, 0);
    }

    /**
     * 设置下拉时是否显示箭头
     *
     * @param showArrow true表示显示下拉箭头,默认为true
     */
    public void setShowArrow(boolean showArrow) {
        isShowArrow = showArrow;
    }
}
