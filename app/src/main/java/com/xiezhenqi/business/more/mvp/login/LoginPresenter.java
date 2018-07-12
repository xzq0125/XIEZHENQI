package com.xiezhenqi.business.more.mvp.login;

import android.text.TextUtils;

import com.xiezhenqi.base.mvp.AbstractBasePresenter;
import com.xiezhenqi.utils.ToastUtils;

/**
 * LoginPresenter
 * Created by Wesley on 2018/6/4.
 */
public class LoginPresenter extends AbstractBasePresenter<LoginModel, LoginContract.LView>
        implements LoginContract.LPresenter {

    public LoginPresenter(LoginContract.LView mView) {
        super(mView);
    }

    @Override
    protected LoginModel createModel() {
        return new LoginModel(this);
    }

    @Override
    public void doLogin(String account, String pwd) {
        mModel.doLogin(account, pwd);
    }

    @Override
    public void onFinishActivity() {
        if (isViewDestroy())
            return;
        mView.onFinishActivity();
    }

    public void doLogin(LoginActivity activity) {

        String account = activity.edtAccount.getText().toString();
        String pwd = activity.edtPwd.getText().toString();

        if (TextUtils.isEmpty(account)) {
            ToastUtils.show("账号不能为空~~");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show("密码不能为空~~");
            return;
        }

        doLogin(account, pwd);
    }

    @Override
    public void setData(String s) {
        if (isViewDestroy())
            return;
        mView.setData(s);
    }

}
