package com.xiezhenqi.business.more.mazing;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BroadcastActivity;
import com.xiezhenqi.business.more.mazing.adapters.IDFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.adapters.RVFragmentAdapter;
import com.xiezhenqi.business.more.mazing.fragments.RVFragments;
import com.xiezhenqi.business.more.mazing.managers.GradientTabStrip2;
import com.xiezhenqi.business.more.mazing.managers.IDTitleViewManager;
import com.xiezhenqi.business.more.mazing.managers.TitleViewManager;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayout;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayoutHeader;

import java.util.List;

import am.widget.basetabstrip.BaseTabStrip;
import am.widget.replacelayout.ReplaceLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IDActivity extends BroadcastActivity implements AppBarLayout.OnOffsetChangedListener, RefreshLayout.OnRefreshListener {

    @Bind(R.id.vp)
    ViewPager vpId;

    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;

    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    @Bind(R.id.gts)
    GradientTabStrip2 gtsTabs;

    @Bind(R.id.gts2)
    GradientTabStrip2 gtsTabs2;

    @Bind(R.id.rl)
    ReplaceLayout rlTitles;

    @Bind(R.id.refresh)
    RefreshLayout pullDownRefresh;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id;
    }

    MainFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        appBarLayout.addOnOffsetChangedListener(this);

        TitleViewManager mTitleViewManager = new IDTitleViewManager();
        mTitleViewManager.initManager(this, rlTitles);

        mPagerAdapter = new IDFragmentPagerAdapter(getSupportFragmentManager(), mTitleViewManager, getIntent().getBundleExtra("data"));

        rlTitles.setAdapter(mPagerAdapter);
        vpId.setOffscreenPageLimit(3);
        vpId.setAdapter(mPagerAdapter);
        vpId.addOnPageChangeListener(mPagerAdapter);

        gtsTabs.addOnChangeListener(gtsTabsListener);
        gtsTabs.bindViewPager(vpId);
        gtsTabs.setAdapter(mPagerAdapter);
        gtsTabs.setOnItemClickListener(mPagerAdapter);

        gtsTabs2.addOnChangeListener(gtsTabs2Listener);
        gtsTabs2.bindViewPager(vpId);
        gtsTabs2.setAdapter(mPagerAdapter);
        gtsTabs2.setOnItemClickListener(mPagerAdapter);

        RefreshLayoutHeader header = new RefreshLayoutHeader(this);
        pullDownRefresh.addOnRefreshListener(header);
        pullDownRefresh.addOnRefreshListener(this);
        pullDownRefresh.setRefreshHeader(header);
        pullDownRefresh.setIsIgnoreTouch(true);
        pullDownRefresh.setSlopRate(5);
        pullDownRefresh.autoRefresh();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        LogUtils.debug("WISH", "verticalOffset = " + verticalOffset);
        if (verticalOffset == 0) {
            toolbar.setVisibility(View.GONE);
            pullDownRefresh.setEnabled(true);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            pullDownRefresh.setEnabled(false);
        }
    }

    private BaseTabStrip.OnChangeListener gtsTabsListener = new BaseTabStrip.OnChangeListener() {
        @Override
        public void jumpTo(int correct) {
            rlTitles.moveTo(correct);
            gtsTabs2.jumpTo(correct);
        }

        @Override
        public void gotoLeft(int correct, int next, float offset) {
            rlTitles.moveLeft(correct, offset);
            gtsTabs2.gotoLeft(correct, next, offset);
        }

        @Override
        public void gotoRight(int correct, int next, float offset) {
            rlTitles.moveRight(correct, offset);
            gtsTabs2.gotoRight(correct, next, offset);
        }
    };

    private BaseTabStrip.OnChangeListener gtsTabs2Listener = new BaseTabStrip.OnChangeListener() {
        @Override
        public void jumpTo(int correct) {
            rlTitles.moveTo(correct);
            gtsTabs.jumpTo(correct);
        }

        @Override
        public void gotoLeft(int correct, int next, float offset) {
            rlTitles.moveLeft(correct, offset);
            gtsTabs.gotoLeft(correct, next, offset);
        }

        @Override
        public void gotoRight(int correct, int next, float offset) {
            rlTitles.moveRight(correct, offset);
            gtsTabs.gotoRight(correct, next, offset);
        }
    };

    @Override
    public void onRefresh() {
        pullDownRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullDownRefresh.refreshComplete();
                isRefresh = false;
            }
        }, 500);

        if (tabName == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments == null)
            return;

        for (Fragment fragment : fragments) {

            if (fragment.getView() instanceof ViewPager) {
                RVFragmentAdapter pagerAdapter = (RVFragmentAdapter) ((ViewPager) fragment.getView()).getAdapter();
                RVFragments rvf = pagerAdapter.getFragmentsByTabName(tabName);
                if (rvf != null) {
                    rvf.refreshData();
                    ToastUtils.showToast(this, "刷新" + tabName + "页面");
                    return;
                }
            }
        }

    }

    @Override
    protected void onAddAction(IntentFilter filter) {
        super.onAddAction(filter);
        filter.addAction("update");
    }

    private String tabName;
    private boolean isRefresh = false;

    @Override
    protected void onLocalBroadcastReceive(Context context, Intent intent) {
        super.onLocalBroadcastReceive(context, intent);

        if ("update".equals(intent.getAction())) {

            if (isRefresh)
                return;

            isRefresh = true;
            pullDownRefresh.autoRefresh(0);
            tabName = intent.getStringExtra("title");

        } else if ("loadData".equals(intent.getAction())) {
            pullDownRefresh.autoRefresh();
        }
    }
}
