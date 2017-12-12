package com.xiezhenqi.mvp.mvp;

/**
 * BaseView
 * Created by Tse on 2017/11/17.
 */

public interface BaseView {

    void showLoadingView();

    void hideLoadingView();

    void showEmptyView();

    void showErrorView(String error);
}
