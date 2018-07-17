package com.xiezhenqi.base.mvp;

/**
 * 状态接口，每次网络加载的回调方法
 * Created by Wesley on 2018/6/4.
 */
public interface ILoadingEntityView {

    /**
     * 显示Loading回调（每次网络加载开始时都会回调次方法）
     *
     * @param loadingMessage 加载提示
     */
    void onShowLoading(String loadingMessage);

    /**
     * 隐藏Loading回调（每次网络加载完成后都会回调次方法）
     */
    void onHideLoading();

    /**
     * 显示空白（每次加载数据为空时回调）
     */
    void onShowEmpty();

    /**
     * 显示错误(每次加载数据错误时回调）
     *
     * @param error 错误信息
     * @param page  页码
     */
    void onShowError(String error, int page);
}
