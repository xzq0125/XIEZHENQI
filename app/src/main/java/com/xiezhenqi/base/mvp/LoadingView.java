package com.xiezhenqi.base.mvp;

/**
 * 状态View
 * Created by Wesley on 2018/6/4.
 */
public interface LoadingView {

    /**
     * 显示Loading状态
     */
    void onShowLoading();

    /**
     * 隐藏Loading
     */
    void onHideLoading();

    /**
     * 显示空白
     */
    void onShowEmpty();

    /**
     * 显示错误
     */
    void onShowError();
}
