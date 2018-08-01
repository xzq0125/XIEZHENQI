package com.xiezhenqi.newmvp;


import com.xiezhenqi.newmvp.mvp.ILoadingListView;
import com.xiezhenqi.newmvp.lifecycle.LifecycleActivity;
import com.xiezhenqi.utils.LogUtils;

/**
 * 简单的加载基类，各方法的回调已空实现，按需重写实现自己的逻辑
 * Created by xzq on 2018/7/13.
 */

public abstract class SimpleLoadingActivity extends LifecycleActivity implements ILoadingListView {

    private static final String TAG = "SimpleLoadingActivity";

    @Override
    public void onFirstLoading(boolean isRefresh) {
        LogUtils.debug(TAG, "onFirstLoading");
    }

    @Override
    public void onFirstLoadFinish(boolean isRefresh) {
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
    public void onLoadingShow(String loadingMessage) {
        LogUtils.debug(TAG, "onLoadingShow");
    }

    @Override
    public void onLoadingHide() {
        LogUtils.debug(TAG, "onLoadingHide");
    }

    @Override
    public void onEmpty() {
        LogUtils.debug(TAG, "onEmpty");
    }

    @Override
    public void onError(String error, int page) {
        LogUtils.debug(TAG, "onError");
    }

    @Override
    public void onLoadMoreEmpty() {
        LogUtils.debug(TAG, "onLoadMoreEmpty");
    }

    @Override
    public void onLoadMoreError(int page, String error) {
        LogUtils.debug(TAG, "onLoadMoreError");
    }
}
