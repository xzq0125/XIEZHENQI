package com.xiezhenqi.base.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiezhenqi.base.mvp.BasePresenter;
import com.xiezhenqi.base.mvp.ILoadingListView;

import am.widget.stateframelayout.StateFrameLayout;


/**
 * BasePresenterActivity
 * Created by Wesley on 2017/12/13.
 */

public abstract class BasePresenterActivity<Presenter extends BasePresenter>
        extends BaseActivity implements ILoadingListView,
        StateFrameLayout.OnStateClickListener {

    protected Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        onShowLoading();
        page = 1;
        loadFirstPage();
    }

    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    protected abstract Presenter createPresenter();

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        if (presenter != null)
            presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onShowLoading() {
        if (sfl != null)
            sfl.loading();
    }

    @Override
    public void onHideLoading() {
        if (sfl != null)
            sfl.normal();
    }

    @Override
    public void onShowEmpty() {
        if (sfl != null)
            sfl.empty();
    }

    @Override
    public void onShowError(String error) {
        if (sfl != null)
            sfl.error();
    }

    @Override
    public void onShowLoadMoreEmpty() {

    }

    @Override
    public void onShowLoadMoreError(String error) {

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
