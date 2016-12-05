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
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BroadcastActivity;
import com.xiezhenqi.business.more.mazing.action.LocalAction;
import com.xiezhenqi.business.more.mazing.adapters.IDFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter;
import com.xiezhenqi.business.more.mazing.adapters.RVFragmentAdapter;
import com.xiezhenqi.business.more.mazing.fragments.RVFragments;
import com.xiezhenqi.business.more.mazing.managers.GradientTabStrip2;
import com.xiezhenqi.business.more.mazing.managers.IDTitleViewManager;
import com.xiezhenqi.business.more.mazing.managers.TitleViewManager;
import com.xiezhenqi.business.more.mazing.managers.ToolbarMoveAnimator;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayout;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayoutHeader;

import java.util.List;

import am.widget.basetabstrip.BaseTabStrip;
import am.widget.replacelayout.ReplaceLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IDActivity extends BroadcastActivity implements AppBarLayout.OnOffsetChangedListener, RefreshLayout.OnRefreshListener {

    private static final String EXTRA_TAB_NAME = "extra_tab_name";
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
    private MainFragmentPagerAdapter mPagerAdapter;
    private String currTabName;
    private ToolbarMoveAnimator toolbarMoveAnimator;
    private int toolbarHeight;
    private boolean isToolbarVisible;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_id;
    }

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
        toolbarMoveAnimator = new ToolbarMoveAnimator(toolbar);
        toolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        pullDownRefresh.setEnabled(verticalOffset == 0);

        float translationY = toolbar.getTranslationY();
        if (toolbarHeight <= 0)
            toolbarHeight = toolbar.getMeasuredHeight();
        if (-verticalOffset < 200) {
            toolbarMoveAnimator.moveUp(translationY, -toolbarHeight);
        } else {
            toolbarMoveAnimator.moveDown(translationY, 0);
            if (!isToolbarVisible) {
                isToolbarVisible = true;
                toolbar.setVisibility(View.VISIBLE);
            }
        }
    }

    private BaseTabStrip.OnChangeListener gtsTabsListener = new BaseTabStrip.OnChangeListener() {
        @Override
        public void jumpTo(int correct) {
            rlTitles.moveTo(correct);
            gtsTabs2.jumpTo(correct);
            getCurrTabName(correct);
        }

        @Override
        public void gotoLeft(int correct, int next, float offset) {
            rlTitles.moveLeft(correct, offset);
            gtsTabs2.gotoLeft(correct, next, offset);
            getCurrTabName(next);
        }

        @Override
        public void gotoRight(int correct, int next, float offset) {
            rlTitles.moveRight(correct, offset);
            gtsTabs2.gotoRight(correct, next, offset);
            getCurrTabName(next);
        }
    };

    private BaseTabStrip.OnChangeListener gtsTabs2Listener = new BaseTabStrip.OnChangeListener() {
        @Override
        public void jumpTo(int correct) {
            rlTitles.moveTo(correct);
            gtsTabs.jumpTo(correct);
            getCurrTabName(correct);
        }

        @Override
        public void gotoLeft(int correct, int next, float offset) {
            rlTitles.moveLeft(correct, offset);
            gtsTabs.gotoLeft(correct, next, offset);
            getCurrTabName(next);
        }

        @Override
        public void gotoRight(int correct, int next, float offset) {
            rlTitles.moveRight(correct, offset);
            gtsTabs.gotoRight(correct, next, offset);
            getCurrTabName(next);
        }
    };

    private void getCurrTabName(int position) {
        ViewGroup viewGroup = (ViewGroup) mPagerAdapter.getReplaceView(null, position);
        if (viewGroup != null && viewGroup.getChildCount() > 0) {
            ViewGroup childGroup = (ViewGroup) viewGroup.getChildAt(0);
            int childCount = childGroup == null ? 0 : childGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (childGroup.getChildAt(i).isSelected()) {
                    View child = childGroup.getChildAt(i);
                    if (child instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) child;
                        TextView tvTabName = (TextView) group.getChildAt(0);
                        currTabName = tvTabName.getText().toString();
                    }
                }
            }
        }
    }

    @Override
    public void onRefresh() {

        if (currTabName == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments == null)
            return;

        for (Fragment fragment : fragments) {

            if (fragment.getView() instanceof ViewPager && fragment.getUserVisibleHint()) {
                RVFragmentAdapter pagerAdapter = (RVFragmentAdapter) ((ViewPager) fragment.getView()).getAdapter();
                if (pagerAdapter != null) {
                    RVFragments rvf = pagerAdapter.getFragmentsByTabName(currTabName);
                    if (rvf != null) {
                        rvf.refreshData();
                        ToastUtils.showToast(this, "刷新" + currTabName + "页面");
                        return;
                    }
                }
            }
        }

    }

    @Override
    protected void onAddAction(IntentFilter filter) {
        super.onAddAction(filter);
        filter.addAction(LocalAction.ACTION_REFRESH_START);
        filter.addAction(LocalAction.ACTION_REFRESH_COMPLETE);
        filter.addAction(LocalAction.ACTION_UPDATE_TAB_NAME);
    }

    @Override
    protected void onLocalBroadcastReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (LocalAction.ACTION_REFRESH_START.equals(action)) {
            pullDownRefresh.autoRefresh(0);
        } else if (LocalAction.ACTION_REFRESH_COMPLETE.equals(action)) {
            pullDownRefresh.refreshComplete();
        } else if (LocalAction.ACTION_UPDATE_TAB_NAME.equals(action)) {
            currTabName = intent.getStringExtra(EXTRA_TAB_NAME);
        }
    }

    public static Intent getIntent(String tabName) {
        return new Intent(LocalAction.ACTION_UPDATE_TAB_NAME).putExtra(EXTRA_TAB_NAME, tabName);
    }
}
