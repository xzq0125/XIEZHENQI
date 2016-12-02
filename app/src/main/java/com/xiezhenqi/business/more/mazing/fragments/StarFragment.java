package com.xiezhenqi.business.more.mazing.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiezhenqi.R;

/**
 * TastyFragment
 * Created by sean on 2016/12/2.
 */

public class StarFragment extends MainFragment {

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_star;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    public static CharSequence getPageTitle(Context context) {
        return "推荐";
    }

    public static Drawable getNormalDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_main_me_normal);
    }

    public static Drawable getSelectedDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_main_me_selected);
    }
}
