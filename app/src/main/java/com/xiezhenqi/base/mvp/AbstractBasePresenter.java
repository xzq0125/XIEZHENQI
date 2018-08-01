package com.xiezhenqi.base.mvp;

import com.xiezhenqi.newmvp.mvp.ILoadingEntityView;

/**
 * AbstractBasePresenter
 * Created by Wesley on 2017/12/13.
 */

public abstract class AbstractBasePresenter<Model extends BaseModel, View extends ILoadingEntityView>
        implements BasePresenter, ILoadingEntityView, BaseModel {

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
    public void onLoadingShow(String loadingMessage) {
        if (mView != null)
            mView.onLoadingShow(loadingMessage);
    }

    @Override
    public void onLoadingHide() {
        if (mView != null)
            mView.onLoadingHide();
    }

    @Override
    public void onEmpty() {
        if (mView != null)
            mView.onEmpty();
    }

    @Override
    public void onError(String error, int page) {
        if (mView != null)
            mView.onError(error, page);
    }

}
