package com.xiezhenqi.base.mvp;

import com.xiezhenqi.newmvp.mvp.IListView;

/**
 * 通用列表基类约束
 * Created by xzq on 2018/7/12.
 */

public interface BaseListContract {

    interface View<Entity> extends IListView<Entity> {
    }

    interface Presenter {
        /**
         * 加载列表
         *
         * @param page      页码
         * @param isRefresh 是否是下拉刷新
         */
        void getList(final int page, boolean isRefresh);
    }
}
