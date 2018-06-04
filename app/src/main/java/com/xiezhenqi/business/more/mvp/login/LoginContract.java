package com.xiezhenqi.business.more.mvp.login;


import com.xiezhenqi.base.mvp.LoadingView;
import com.xiezhenqi.base.mvp.EntityView;
import com.xiezhenqi.base.mvp.FinishView;

/**
 * LoginContract
 * Created by Wesley on 2018/6/4.
 */
public interface LoginContract {

    interface LView extends LoadingView, FinishView, EntityView<String> {
    }

    interface LModel {
        void doLogin(String account, String pwd);
    }

    interface LPresenter extends LModel, LView {
    }
}
