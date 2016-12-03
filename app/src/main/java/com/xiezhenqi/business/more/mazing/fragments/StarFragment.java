package com.xiezhenqi.business.more.mazing.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.business.more.mazing.adapters.RVFragmentAdapter;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.widget.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * TastyFragment
 * Created by sean on 2016/12/2.
 */

public class StarFragment extends MainFragment implements SmartTabLayout.TabProvider {

    @Bind(R.id.vp_star)
    ViewPager vpStar;
    private SmartTabLayout stl;

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_star;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        RVFragmentAdapter adapter = new RVFragmentAdapter(getFragmentManager());
        vpStar.setAdapter(adapter);
        if (stl != null)
            stl.setViewPager(vpStar);
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_custom_tab_view, container, false);
        TextView tabTitleView = (TextView) tabView.findViewById(R.id.tv_tab);
        TextView tabCount = (TextView) tabView.findViewById(R.id.tv_count);
        tabTitleView.setText(adapter.getPageTitle(position));
        tabCount.setText("5");
        return tabView;
    }

    @Override
    public void bindTitleView(View title) {
        if (title instanceof SmartTabLayout) {
            stl = (SmartTabLayout) title;
            stl.setCustomTabView(this);
            stl.setViewPager(vpStar);
        } else {
            LogUtils.w("WISH", "根布局不是SmartTabLayout");
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
