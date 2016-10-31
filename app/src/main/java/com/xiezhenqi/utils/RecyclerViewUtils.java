package com.xiezhenqi.utils;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

/**
 * 列表工具
 * Created by sean on 2016/9/23.
 */
public class RecyclerViewUtils {

    /**
     * 设置某个item的topMargin
     *
     * @param itemView   itemView
     * @param id         资源id
     * @param needMargin 需要Margin
     */
    public static void setItemViewTopMargin(@NonNull View itemView, @DimenRes int id, boolean needMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
        layoutParams.topMargin = needMargin ? itemView.getResources().getDimensionPixelOffset(id) : 0;
        itemView.setLayoutParams(layoutParams);
    }

    /**
     * 设置某个item的topPadding,其它方向的Padding会置0
     *
     * @param itemView    itemView
     * @param id          资源id
     * @param needPadding 需要Padding
     */
    public static void setItemViewTopPadding(@NonNull View itemView, @DimenRes int id, boolean needPadding) {
        itemView.setPadding(0, needPadding ? itemView.getResources().getDimensionPixelOffset(id) : 0, 0, 0);
    }

    /**
     * 设置某个item的BottomPadding,其它方向的Padding会置0
     *
     * @param itemView    itemView
     * @param id          资源id
     * @param needPadding 需要Padding
     */
    public static void setItemViewBottomPadding(@NonNull View itemView, @DimenRes int id, boolean needPadding) {
        itemView.setPadding(0, 0, 0, needPadding ? itemView.getResources().getDimensionPixelOffset(id) : 0);
    }

    /**
     * 滚动列表到顶部
     *
     * @param recyclerView RecyclerView
     */
    public static void scrollToTopWithAnimation(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() == 0)
                return;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] info = new int[2];
            info = ((StaggeredGridLayoutManager) layoutManager).findFirstCompletelyVisibleItemPositions(info);
            if (info[0] <= 0)
                return;
        }

        recyclerView.scrollToPosition(0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(recyclerView, "translationY", -dp2px(10), dp2px(50), 0);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }

    /**
     * dp 2 px
     *
     * @param dpValue dp
     * @return px
     */
    private static int dp2px(float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                Resources.getSystem().getDisplayMetrics()) + 0.5f);
    }
}
