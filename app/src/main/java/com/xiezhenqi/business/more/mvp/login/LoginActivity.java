package com.xiezhenqi.business.more.mvp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BasePresenterActivity;
import com.xiezhenqi.utils.ToastUtils;

import butterknife.BindView;

/**
 * LoginActivity
 * Created by Wesley on 2018/6/4.
 */
public class LoginActivity extends BasePresenterActivity<LoginPresenter> implements LoginContract.LView {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

    }

    public void login(View view) {
        presenter.doLogin(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onShowEmpty() {

    }

    @Override
    public void onShowError(String error) {

    }

    @Override
    public void onFinishActivity() {
        finish();
    }

    @Override
    public void setData(String s) {
        ToastUtils.show(s);
    }

}
