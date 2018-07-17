package com.xiezhenqi.base.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiezhenqi.base.mvp.BasePresenter;


/**
 * BasePresenterActivity
 * Created by Wesley on 2017/12/13.
 */

public abstract class BasePresenterActivity<Presenter extends BasePresenter>
        extends BaseActivity {

    protected Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
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

}
