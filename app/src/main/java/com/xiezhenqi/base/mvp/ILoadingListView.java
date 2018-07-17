package com.xiezhenqi.base.mvp;

/**
 * 通用加载列表/更多列表的接口
 * Created by Wesley on 2017/12/13.
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
    void onShowLoadMoreEmpty();

    /**
     * 首次网络加载数据错误回调
     */
    void onFirstLoadError(int page, String error);

    /**
     * 加载更多错误回调
     */
    void onShowLoadMoreError(int page, String error);

}
