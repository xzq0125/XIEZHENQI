package com.xiezhenqi.newmvp;


import com.xiezhenqi.base.mvp.ILoadingListView;
import com.xiezhenqi.newmvp.lifecycle.LifecycleActivity;
import com.xiezhenqi.utils.LogUtils;

/**
 * 简单的加载基类，各方法的回调已空实现，按需重写实现自己的逻辑
 * Created by xzq on 2018/7/13.
 */

public abstract class SimpleLoadingActivity extends LifecycleActivity implements ILoadingListView {

    private static final String TAG = "SimpleLoadingActivity";

    @Override
    public void onFirstLoading() {
        LogUtils.debug(TAG, "onFirstLoading");
    }

    @Override
    public void onFirstLoadFinish() {
        LogUtils.debug(TAG, "onFirstLoadFinish");
    }

    @Override
    public void onFirstLoadError(int page, String error) {
        LogUtils.debug(TAG, "onFirstLoadError");
    }

    @Override
    public void onFirstLoadEmpty() {
        LogUtils.debug(TAG, "onFirstLoadEmpty");
    }

    @Override
    public void onShowLoading(String loadingMessage) {
        LogUtils.debug(TAG, "onShowLoading");
    }

    @Override
    public void onHideLoading() {
        LogUtils.debug(TAG, "onHideLoading");
    }

    @Override
    public void onShowEmpty() {
        LogUtils.debug(TAG, "onShowEmpty");
    }

    @Override
    public void onShowError(String error, int page) {
        LogUtils.debug(TAG, "onShowError");
    }

    @Override
    public void onShowLoadMoreEmpty() {
        LogUtils.debug(TAG, "onShowLoadMoreEmpty");
    }

    @Override
    public void onShowLoadMoreError(int page, String error) {
        LogUtils.debug(TAG, "onShowLoadMoreError");
    }
}
