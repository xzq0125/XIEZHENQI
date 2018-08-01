package com.xiezhenqi.newmvp.mvp;

import java.util.List;

/**
 * 列表接口
 * 网络响应以列表形式返回
 *
 * @author xzq
 */
public interface IListView<Entity> extends ILoadingListView {

    /**
     * 设置数据
     *
     * @param list        数据列表
     * @param page        当前页码
     * @param hasNextPage 是否有下一页
     */
    void setData(List<Entity> list, int page, boolean hasNextPage);

    /**
     * 追加数据
     *
     * @param list        数据列表
     * @param page        当前页码
     * @param hasNextPage 是否有下一页
     */
    void addData(List<Entity> list, int page, boolean hasNextPage);
}
