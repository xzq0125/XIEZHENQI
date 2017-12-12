package com.xiezhenqi.mvp.mvp;

/**
 * StateView
 * Created by sean on 2017/2/15.
 */

public interface StateView {

    void showLoadingView();

    void hideLoadingView();

    void showEmptyView();

    void showErrorView(String error);

}
