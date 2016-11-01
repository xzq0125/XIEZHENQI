package com.xiezhenqi.business.home.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.base.fragments.BaseFragment;
import com.xiezhenqi.business.account.ShareDialog;
import com.xiezhenqi.business.h5help.H5HelpActivity;
import com.xiezhenqi.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * DFragment
 * Created by Tse on 2016/10/29.
 */

public class DFragment extends BaseFragment {

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_d;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
    }

    @OnClick({R.id.account_llyt_order,
            R.id.account_llyt_coupon,
            R.id.account_llyt_message,
            R.id.account_tv_share,
            R.id.account_tv_suggest})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.account_llyt_order:
                ToastUtils.showToast(getActivity(), "我的订单");
                break;
            case R.id.account_llyt_coupon:
                ToastUtils.showToast(getActivity(), "米星券");
                break;
            case R.id.account_llyt_message:
                ToastUtils.showToast(getActivity(), "消息");
                break;
            case R.id.account_tv_share:
                new ShareDialog(getActivity()).show();
                break;
            case R.id.account_tv_suggest:
                H5HelpActivity.start(getActivity(), "https://www.baidu.com");
                break;
            default:
                break;
        }
    }

    public static CharSequence getPageTitle(Context context) {
        return "我的";
    }

    public static Bitmap getNormalDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_me_normal);
    }

    public static Bitmap getSelectedDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_me_selected);
    }
}
