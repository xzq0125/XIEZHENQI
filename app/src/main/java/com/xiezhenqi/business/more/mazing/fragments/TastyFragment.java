package com.xiezhenqi.business.more.mazing.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.business.more.mazing.adapters.RVFragmentAdapter;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.utils.RecyclerViewUtils;
import com.xiezhenqi.widget.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * TastyFragment
 * Created by sean on 2016/12/2.
 */

public class TastyFragment extends MainFragment implements
        SmartTabLayout.TabProvider,
        SmartTabLayout.OnTabClickListener {

    @Bind(R.id.vp_tasty)
    ViewPager vpTasty;
    private SmartTabLayout stl;
    private RVFragmentAdapter pagerAdapter;

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_tasty;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
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
            stl.setOnTabClickListener(this);
            stl.setCustomTabView(this);
            stl.setViewPager(vpTasty);
        } else {
            LogUtils.w("WISH", "根布局不是SmartTabLayout");
        }
    }

    public static CharSequence getPageTitle(Context context) {
        return "体验";
    }

    public static Drawable getNormalDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_main_operator_normal);
    }

    public static Drawable getSelectedDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_main_operator_selected);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onTabClicked(View v, int position) {
        TextView textView = (TextView) v.findViewById(R.id.tv_tab);
        String tabName = textView.getText().toString();
        if (v.isSelected()) {
            View child = vpTasty.getChildAt(vpTasty.getCurrentItem());
            if (child instanceof RecyclerView)
                RecyclerViewUtils.scrollToTopWithAnimation((RecyclerView) child);
            XZQApplication.sendLocalBroadcast(new Intent().putExtra("title", tabName).setAction("update"));
        } else if (pagerAdapter != null) {
            RVFragments fragment = pagerAdapter.getFragmentsByTabName(tabName);
            if (fragment != null) {
                if (fragment.isLoadData())
                    XZQApplication.sendLocalBroadcast(new Intent().putExtra("title", tabName).setAction("updateTab"));
                else
                    XZQApplication.sendLocalBroadcast(new Intent().putExtra("title", tabName).setAction("update"));
            }
        }
    }

    @Override
    protected void loadData() {
        pagerAdapter = new RVFragmentAdapter(getFragmentManager(), "体验");
        vpTasty.setAdapter(pagerAdapter);
        if (stl != null)
            stl.setViewPager(vpTasty);
        XZQApplication.sendLocalBroadcast(new Intent().putExtra("title", "体验1").setAction("update"));
    }
}
