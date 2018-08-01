package com.xiezhenqi.base.activitys;

import com.xiezhenqi.base.mvp.BasePresenter;
import com.xiezhenqi.newmvp.mvp.ILoadingListView;

import am.widget.stateframelayout.StateFrameLayout;

/**
 * BaseListActivity
 * Created by xzq on 2018/7/17.
 */

public abstract class BaseListActivity<P extends BasePresenter> extends BasePresenterActivity<P>
        implements ILoadingListView,
        StateFrameLayout.OnStateClickListener {

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        onLoadingShow(null);
        page = 1;
        loadFirstPage();
    }

    @Override
    public void onLoadingShow(String loadingMessage) {
        if (sfl != null)
            sfl.loading();
    }

    @Override
    public void onLoadingHide() {
        if (sfl != null)
            sfl.normal();
    }

    @Override
    public void onEmpty() {
        if (sfl != null)
            sfl.empty();
    }

    @Override
    public void onError(String error, int page) {
        if (sfl != null)
            sfl.error();
    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoadMoreError(int page, String error) {

    }

    private StateFrameLayout sfl;

    protected void getSfl(StateFrameLayout sfl) {
        if (sfl != null) {
            this.sfl = sfl;
            sfl.setOnStateClickListener(this);
        }
    }

    protected void loadFirstPage() {

    }

    protected int page = 1;
}
