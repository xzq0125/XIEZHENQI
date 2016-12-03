package com.xiezhenqi.business.more.mazing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.business.more.mazing.adapters.IDFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.managers.IDTitleViewManager;
import com.xiezhenqi.business.more.mazing.managers.TitleViewManager;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.widget.smarttablayout.SmartTabLayout;

import am.widget.basetabstrip.BaseTabStrip;
import am.widget.gradienttabstrip.GradientTabStrip;
import am.widget.replacelayout.ReplaceLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IDActivity extends BaseActivity implements SmartTabLayout.TabProvider, AppBarLayout.OnOffsetChangedListener, BaseTabStrip.OnChangeListener {

    @Bind(R.id.vp)
    ViewPager vpId;

    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;

    @Bind(R.id.toolBar)
    Toolbar toolbar;

    @Bind(R.id.gts)
    GradientTabStrip gtsTabs;

    @Bind(R.id.rl)
    ReplaceLayout rlTitles;

    private TitleViewManager mTitleViewManager;
    private MainFragmentPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        appBarLayout.addOnOffsetChangedListener(this);

        mTitleViewManager = new IDTitleViewManager();
        mPagerAdapter = new IDFragmentPagerAdapter(getSupportFragmentManager(), mTitleViewManager, getIntent().getBundleExtra("data"));
        mTitleViewManager.initManager(this, rlTitles);
        gtsTabs.addOnChangeListener(this);
        gtsTabs.bindViewPager(vpId);
        rlTitles.setAdapter(mPagerAdapter);
        vpId.setOffscreenPageLimit(3);
        vpId.setAdapter(mPagerAdapter);
        gtsTabs.setAdapter(mPagerAdapter);
        vpId.addOnPageChangeListener(mPagerAdapter);
        gtsTabs.setOnItemClickListener(mPagerAdapter);
        vpId.setCurrentItem(0);
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.debug("WISH", "verticalOffset = " + verticalOffset);
        if (verticalOffset == 0) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void jumpTo(int correct) {
        rlTitles.moveTo(correct);
    }

    @Override
    public void gotoLeft(int correct, int next, float offset) {
        rlTitles.moveLeft(correct, offset);
    }

    @Override
    public void gotoRight(int correct, int next, float offset) {
        rlTitles.moveRight(correct, offset);
    }

}
