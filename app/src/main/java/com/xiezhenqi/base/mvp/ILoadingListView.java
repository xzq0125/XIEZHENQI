package com.xiezhenqi.base.mvp;

/**
 * 通用加载列表/更多列表的接口
 * Created by Wesley on 2017/12/13.
 */

public interface ILoadingListView extends ILoadingView {

    /**
     * 显示加载空数据
     */
    void onShowLoadMoreEmpty();

    /**
     * 显示加载更多错误
     */
    void onShowLoadMoreError(String error);

}
