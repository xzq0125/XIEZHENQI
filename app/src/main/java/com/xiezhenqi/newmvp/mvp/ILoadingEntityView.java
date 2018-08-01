package com.xiezhenqi.newmvp.mvp;

/**
 * 状态接口
 * 每次网络加载都会回调的四个方法
 *
 * @author xzq
 */
public interface ILoadingEntityView {

    /**
     * 显示Loading
     *
     * @param loadingMessage 加载提示语
     */
    void onLoadingShow(String loadingMessage);

    /**
     * 隐藏Loading
     */
    void onLoadingHide();

    /**
     * 显示空白
     */
    void onEmpty();

    /**
     * 显示错误
     *
     * @param error 错误信息
     * @param page  页码
     */
    void onError(String error, int page);
}
