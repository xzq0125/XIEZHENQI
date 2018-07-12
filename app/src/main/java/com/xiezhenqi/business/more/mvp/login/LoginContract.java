package com.xiezhenqi.business.more.mvp.login;


import com.xiezhenqi.base.mvp.IEntityView;
import com.xiezhenqi.base.mvp.FinishView;

/**
 * LoginContract
 * Created by Wesley on 2018/6/4.
 */
public interface LoginContract {

    interface LView extends  FinishView, IEntityView<String> {
    }

    interface LModel {
        void doLogin(String account, String pwd);
    }

    interface LPresenter extends LModel, LView {
    }
}
