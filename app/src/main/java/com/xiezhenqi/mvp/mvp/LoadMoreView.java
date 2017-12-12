package com.xiezhenqi.mvp.mvp;

import java.util.List;

/**
 * LoadMoreView
 * Created by sean on 2017/2/22.
 */

public interface LoadMoreView<T> extends StateView {

    void setData(List<T> list, boolean hasNext);

    void addData(List<T> list, boolean hasNext);

    void showLoadMoreErrorView(String error);
}
