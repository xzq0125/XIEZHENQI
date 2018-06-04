package com.xiezhenqi.base.mvp;

import java.util.List;

/**
 * 通用加载列表/更多列表的接口
 * Created by Wesley on 2017/12/13.
 */

public interface LoadMoreView<Entity> {

    /**
     * 设置数据
     *
     * @param list        数据列表
     * @param hasNextPage 是否有下一页列表
     */
    void onSetData(List<Entity> list, boolean hasNextPage);

    /**
     * 追加数据
     *
     * @param list        数据列表
     * @param hasNextPage 是否有下一页列表
     */
    void onAddData(List<Entity> list, boolean hasNextPage);

    /**
     * 显示加载空数据
     */
    void onShowLoadMoreEmpty();

    /**
     * 显示加载更多错误
     */
    void onShowLoadMoreError();

}
