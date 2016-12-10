package com.xiezhenqi.business.home.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.base.fragments.BaseFragment;

/**
 * AFragment
 * Created by Tse on 2016/10/29.
 */

public class AFragment extends BaseFragment {

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_a;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //getFragmentManager().beginTransaction().replace(R.id.a_container, new MusicPavilionFragment()).commit();
    }

    public static CharSequence getPageTitle(Context context) {
        return "首页";
    }

    public static Bitmap getNormalDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_manager_normal);
    }

    public static Bitmap getSelectedDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_manager_selected);
    }
}
