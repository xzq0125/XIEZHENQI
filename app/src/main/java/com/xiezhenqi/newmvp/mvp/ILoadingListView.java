package com.xiezhenqi.newmvp.mvp;

/**
 * 通用加载列表/更多列表的接口
 *
 * @author xzq
 */

public interface ILoadingListView extends ILoadingEntityView {

    /**
     * 首次网络加载开始时回调
     */
    void onFirstLoading();

    /**
     * 首次网络加载完成回调
     */
    void onFirstLoadFinish();

    /**
     * 首次网络加载数据为空回调
     */
    void onFirstLoadEmpty();

    /**
     * 加载更多数据为空回调
     */
    void onLoadMoreEmpty();

    /**
     * 首次网络加载数据错误回调
     *
     * @param page  当前页码
     * @param error 错误信息
     */
    void onFirstLoadError(int page, String error);

    /**
     * 加载更多错误回调
     *
     * @param page  当前页码
     * @param error 错误信息
     */
    void onLoadMoreError(int page, String error);

}
