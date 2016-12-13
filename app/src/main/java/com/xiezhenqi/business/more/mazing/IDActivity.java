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
import com.xiezhenqi.widget.pulldownrefresh.HeaderHelper;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayout;

import java.util.List;

import am.widget.basetabstrip.BaseTabStrip;
import am.widget.replacelayout.ReplaceLayout;
import butterknife.Bind;
import butterknife.ButterKnife;

public class IDActivity extends BroadcastActivity implements AppBarLayout.OnOffsetChangedListener, RefreshLayout.OnRefreshListener, MainFragmentPagerAdapter.OnSelectListener {

    private static final String EXTRA_TAB_NAME = "extra_tab_name";
    private static final String EXTRA_TYPE = "extra_type";
    private static final String EXTRA_SMOOTH = "extra_smooth";
    private static final String EXTRA_PAGE = "extra_page";
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
        mPagerAdapter.setListener(this);

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

        HeaderHelper.initSampleHeader(pullDownRefresh, this);
        pullDownRefresh.setIsIgnoreTouch(true);
        pullDownRefresh.setSlopRate(5);
        toolbarMoveAnimator = new ToolbarMoveAnimator(toolbar);
        toolbar.setVisibility(View.INVISIBLE);

        vpId.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurrentPager((MainFragmentPagerAdapter.PagerType) getIntent().getSerializableExtra(EXTRA_TYPE),
                        getIntent().getBooleanExtra(EXTRA_SMOOTH, false), getIntent().getIntExtra(EXTRA_PAGE, 0));
            }
        }, 0);
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
            getCurrTabName(next);
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

    private void getCurrTabName(int position) {
        ViewGroup smartLayout = (ViewGroup) mPagerAdapter.getReplaceView(null, position);
        if (smartLayout != null && smartLayout.getChildCount() > 0) {
            ViewGroup smartTabStrip = (ViewGroup) smartLayout.getChildAt(0);
            int childCount = smartTabStrip == null ? 0 : smartTabStrip.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (smartTabStrip.getChildAt(i).isSelected()) {
                    View child = smartTabStrip.getChildAt(i);
                    if (child instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) child;
                        TextView tvTabName = (TextView) group.getChildAt(0);
                        currTabName = tvTabName.getText().toString();
                    }
                }
            }
        }
    }

    private void loadFirstDataByTabName(String currTabName) {
        if (currTabName == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        if (fragments == null)
            return;

        for (Fragment fragment : fragments) {

            if (fragment.getView() instanceof ViewPager && fragment.getUserVisibleHint()) {

                ViewPager viewPager = (ViewPager) fragment.getView();
                RVFragmentAdapter pagerAdapter = (RVFragmentAdapter) viewPager.getAdapter();
                if (pagerAdapter != null) {
                    RVFragments rvf = pagerAdapter.getFragmentsByTabName(currTabName);
                    if (rvf != null && !rvf.isLoadData()) {
                        viewPager.setCurrentItem(page, false);
                        page = 0;
                        rvf.preLoadData(true);
                        ToastUtils.showToast(this, "开始加载" + currTabName + "页面");
                        return;
                    }
                }
            }
        }
    }

    private void refreshDataByTabName(String currTabName) {
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
                        ToastUtils.showToast(this, "开始刷新" + currTabName + "页面");
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        refreshDataByTabName(currTabName);
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

    @Override
    public void onTabSelect(int position) {
        getCurrTabName(position);
        vpId.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstDataByTabName(currTabName);
            }
        }, 500);
    }

    int page;

    /**
     * 滚动到某一页面
     *
     * @param type   页面枚举值
     * @param smooth 是否平滑
     * @param page   选中哪个子tab
     */
    private void setCurrentPager(MainFragmentPagerAdapter.PagerType type, boolean smooth, int page) {
        int position = -1;
        if (type != null) {
            position = mPagerAdapter.typeToPosition(type);
        }
        if (position != -1) {
            gtsTabs.performClick(position, smooth, false);
            this.page = page;
        }
    }

    public static void start(Context context, MainFragmentPagerAdapter.PagerType pageType, int page) {
        start(context, pageType, false, page);
    }

    public static void start(Context context, MainFragmentPagerAdapter.PagerType pageType, boolean smooth, int page) {
        Intent starter = new Intent(context, IDActivity.class);
        starter.putExtra(EXTRA_TYPE, pageType);
        starter.putExtra(EXTRA_SMOOTH, smooth);
        starter.putExtra(EXTRA_PAGE, page);
        context.startActivity(starter);
    }
}
