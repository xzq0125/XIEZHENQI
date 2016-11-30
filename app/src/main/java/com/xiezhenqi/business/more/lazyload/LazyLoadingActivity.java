package com.xiezhenqi.business.more.lazyload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.widget.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LazyLoadingActivity extends BaseActivity implements SmartTabLayout.TabProvider {

    @Bind(android.R.id.title)
    TextView tvTitle;

    @Bind(R.id.vp)
    ViewPager vpFragments;

    @Bind(R.id.stl)
    SmartTabLayout stl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lazy_loading;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        ButterKnife.bind(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
        LazyFragmentPagerAdapter fragmentPagerAdapter = new LazyFragmentPagerAdapter(getSupportFragmentManager());
        vpFragments.setAdapter(fragmentPagerAdapter);
        vpFragments.setOffscreenPageLimit(fragmentPagerAdapter.getCount() - 1);
        stl.setCustomTabView(this);
        stl.setViewPager(vpFragments);
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = getLayoutInflater().inflate(R.layout.item_custom_tab_view, container, false);
        TextView tabTitleView = (TextView) tabView.findViewById(R.id.tv_tab);
        TextView tabCount = (TextView) tabView.findViewById(R.id.tv_count);
        tabTitleView.setText(adapter.getPageTitle(position));
        tabCount.setText("5");
        return tabView;
    }
}















