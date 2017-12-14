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
import com.xiezhenqi.business.search.SearchManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * BFragment
 * Created by Tse on 2016/10/29.
 */

public class BFragment extends BaseFragment {

    @BindView(R.id.search_container)
    View vSearch;

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_b;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        new SearchManager(getActivity(), vSearch);
    }

    public static CharSequence getPageTitle(Context context) {
        return "搜索";
    }

    public static Bitmap getNormalDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_operator_normal);
    }

    public static Bitmap getSelectedDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_operator_selected);
    }
}
