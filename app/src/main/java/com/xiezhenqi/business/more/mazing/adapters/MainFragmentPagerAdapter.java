package com.xiezhenqi.business.more.mazing.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.business.more.mazing.fragments.MainFragment;
import com.xiezhenqi.business.more.mazing.managers.TitleViewManager;

import am.widget.gradienttabstrip.GradientTabStrip;
import am.widget.replacelayout.ReplaceLayout;

/**
 * 主Adapter
 * Created by Alex on 2015/9/6.
 */
public abstract class MainFragmentPagerAdapter extends FragmentPagerAdapter implements
        GradientTabStrip.GradientTabAdapter, ReplaceLayout.ReplaceAdapter,
        ViewPager.OnPageChangeListener, GradientTabStrip.OnItemClickListener {

    private int pageNo = 0;
    private final FragmentManager mFragmentManager;
    private TitleViewManager mManager;
    private Bundle startData;


    public enum PagerType {
        TASTY(0),
        STAR(1),
        LIKE(2);
        private int value;

        PagerType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static PagerType toPagerType(int value) {
            switch (value) {
                case 0:
                    return TASTY;
                case 1:
                    return STAR;
                case 2:
                    return LIKE;
                default:
                    return null;
            }
        }
    }

    public MainFragmentPagerAdapter(FragmentManager fm, TitleViewManager manager, Bundle startData) {
        super(fm);
        mFragmentManager = fm;
        mManager = manager;
        this.startData = startData;
    }

    @Override
    public Fragment getItem(int position) {
        MainFragment fragment = getPageFragment(position);
        fragment.bindTitleView(mManager.getReplaceView(positionToType(position)));
        Bundle bundle = new Bundle();
        bundle.putInt(MainFragment.EXTRA_POSITION, position);
        bundle.putInt(MainFragment.EXTRA_FIRST_POSITION, getPageNo());
        if (startData != null)
            bundle.putBundle(MainFragment.EXTRA_DATA, startData);
        fragment.setArguments(bundle);
        return fragment;
    }

    public abstract MainFragment getPageFragment(int position);

    public abstract int typeToPosition(PagerType type);

    public abstract PagerType positionToType(int position);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageNo = position;
        if (mFragmentManager.getFragments() != null)
            for (Fragment fragment : mFragmentManager.getFragments()) {
                if (fragment != null && fragment instanceof MainFragment) {
                    MainFragment mainFragment = (MainFragment) fragment;
                    mainFragment.onPageChanged(position);
                }
            }
    }

    public MainFragment getFragment(int position) {
        if (mFragmentManager.getFragments() != null)
            for (Fragment fragment : mFragmentManager.getFragments()) {
                if (fragment != null && fragment instanceof MainFragment) {
                    MainFragment mainFragment = (MainFragment) fragment;
                    if (mainFragment.getPosition() == position)
                        return mainFragment;
                }
            }
        return null;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void onItemClick(int position) {
        if (mFragmentManager.getFragments() != null)
            for (Fragment fragment : mFragmentManager.getFragments()) {
                if (fragment != null && fragment instanceof MainFragment) {
                    MainFragment mainFragment = (MainFragment) fragment;
                    mainFragment.onTabClick(position);
                }
            }
    }

    @Override
    public void onSelectedClick(int position) {
        if (mFragmentManager.getFragments() != null)
            for (Fragment fragment : mFragmentManager.getFragments()) {
                if (fragment != null && fragment instanceof MainFragment) {
                    MainFragment mainFragment = (MainFragment) fragment;
                    mainFragment.onSelectedTabClick(position);
                }
            }
    }

    @Override
    public void onDoubleClick(int position) {
        if (mFragmentManager.getFragments() != null)
            for (Fragment fragment : mFragmentManager.getFragments()) {
                if (fragment != null && fragment instanceof MainFragment) {
                    MainFragment mainFragment = (MainFragment) fragment;
                    mainFragment.onTabDoubleClick(position);
                }
            }
    }

    @Override
    public View getReplaceView(ReplaceLayout replaceLayout, int position) {
        return mManager.getReplaceView(positionToType(position));
    }

    @Override
    public void onAnimation(ViewGroup replace, int correct, int next, float offset) {
        mManager.onAnimation(replace, correct, next, offset, getCount(),
                positionToType(correct), positionToType(next));
    }

    @Override
    public void onSelected(ViewGroup replace, int position) {
        mManager.onSelected(replace, position, getCount(), positionToType(position));
        if (listener != null)
            listener.onTabSelect(position);
    }

    public boolean isTagEnable(int position) {
        MainFragment fragment = getFragment(position);
        return fragment != null && fragment.isTabTagEnable();
    }

    public String getTag(int position) {
        MainFragment fragment = getFragment(position);
        return fragment == null ? null : fragment.getTabTag();
    }

    public void rebindTitleView() {
        for (int i = 0; i < getCount(); i++) {
            MainFragment fragment = getFragment(i);
            if (fragment != null)
                fragment.bindTitleView(mManager.getReplaceView(positionToType(i)));
        }
    }

    private OnSelectListener listener;

    public void setListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onTabSelect(int position);
    }
}
