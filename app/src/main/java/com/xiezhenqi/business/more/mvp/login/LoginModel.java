package com.xiezhenqi.business.more.mvp.login;

import android.os.Handler;
import android.os.Looper;

import com.xiezhenqi.base.mvp.AbstractModel;

/**
 * LoginModel
 * Created by Wesley on 2018/6/4.
 */
public class LoginModel extends AbstractModel<LoginPresenter> implements LoginContract.LModel {

    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public void doLogin(String account, String pwd) {
        presenter.onLoadingShow(null);
        addRequest(null);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.onLoadingHide();
                presenter.setData("登录实体");
                presenter.onFinishActivity();
            }
        }, 3000);
    }

}
