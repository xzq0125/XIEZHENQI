package com.xiezhenqi.business.home.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.base.fragments.BaseFragment;
import com.xiezhenqi.business.songlist.SongListFragmengAdapter;
import com.xiezhenqi.widget.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * CFragment
 * Created by Tse on 2016/10/29.
 */

public class CFragment extends BaseFragment {

    @Bind(R.id.stl)
    SmartTabLayout smartTabLayout;

    @Bind(R.id.vp_song_list)
    ViewPager viewPager;

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_c;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        viewPager.setAdapter(new SongListFragmengAdapter(getFragmentManager()));
        smartTabLayout.setViewPager(viewPager);
    }

    public static CharSequence getPageTitle(Context context) {
        return "推荐";
    }

    public static Bitmap getNormalDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_order_normal);
    }

    public static Bitmap getSelectedDrawable(Context context) {
        return BitmapFactory.decodeResource(XZQApplication.getContext().getResources(), R.drawable.ic_main_order_selected);
    }
}
