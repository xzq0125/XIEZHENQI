package com.xiezhenqi.mvp.mvp;

/**
 * AbstractBasePresenter
 * Created by Tse on 2017/11/17.
 */

public abstract class AbstractBasePresenter<Model, View extends BaseView>
        implements BasePresenter, BaseView {

    protected View mView;
    protected final Model mModel;

    public AbstractBasePresenter(View mView) {
        this.mView = mView;
        mModel = createModel();
    }

    protected abstract Model createModel();

    @Override
    public void recycle() {
        mView = null;
    }

    protected boolean isViewRecycled() {
        return mView == null;
    }

    @Override
    public void showLoadingView() {
        if (isViewRecycled())
            return;
        mView.showLoadingView();
    }

    @Override
    public void hideLoadingView() {
        if (isViewRecycled())
            return;
        mView.hideLoadingView();
    }

    @Override
    public void showEmptyView() {
        if (isViewRecycled())
            return;
        mView.showEmptyView();
    }

    @Override
    public void showErrorView(String error) {
        if (isViewRecycled())
            return;
        mView.showErrorView(error);
    }
}
