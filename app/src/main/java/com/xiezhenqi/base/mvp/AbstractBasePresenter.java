package com.xiezhenqi.base.mvp;

/**
 * AbstractBasePresenter
 * Created by Wesley on 2017/12/13.
 */

public abstract class AbstractBasePresenter<Model extends BaseModel, View extends ILoadingView>
        implements BasePresenter, ILoadingView, BaseModel {

    protected View mView;
    protected final Model mModel;

    public AbstractBasePresenter(View mView) {
        this.mView = mView;
        mModel = createModel();
    }

    protected abstract Model createModel();

    @Override
    public void onDestroy() {
        mView = null;
        mModel.cancelRequest();
    }

    @Override
    public void cancelRequest() {
        mModel.cancelRequest();
    }

    protected boolean isViewDestroy() {
        return mView == null;
    }

    @Override
    public void onShowLoading() {
        if (mView != null)
            mView.onShowLoading();
    }

    @Override
    public void onHideLoading() {
        if (mView != null)
            mView.onHideLoading();
    }

    @Override
    public void onShowEmpty() {
        if (mView != null)
            mView.onShowEmpty();
    }

    @Override
    public void onShowError(String error) {
        if (mView != null)
            mView.onShowError(error);
    }

}
