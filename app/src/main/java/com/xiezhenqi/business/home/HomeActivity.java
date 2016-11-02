package com.xiezhenqi.business.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BroadcastActivity;
import com.xiezhenqi.business.home.adapters.HomeFragmentPagerAdapter;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.gradienttabview.GradientTabView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BroadcastActivity {

    @Bind(R.id.main_vp_fragments)
    ViewPager vpFragments;
    @Bind(R.id.main_gtv_tabs)
    GradientTabView gtvTabs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        FragmentPagerAdapter fragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        vpFragments.setAdapter(fragmentPagerAdapter);
        vpFragments.setOffscreenPageLimit(3);
        gtvTabs.attachToViewPager(vpFragments);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, HomeActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        if (canFinish)
            super.onBackPressed();
        else {
            canFinish = true;
            ToastUtils.showToast(this, "再按一次退出~");
            vpFragments.postDelayed(mCancelFinishTask, 2000);
        }
    }

    private boolean canFinish = false;
    private final Runnable mCancelFinishTask = new Runnable() {
        @Override
        public void run() {
            canFinish = false;
        }
    };
}
