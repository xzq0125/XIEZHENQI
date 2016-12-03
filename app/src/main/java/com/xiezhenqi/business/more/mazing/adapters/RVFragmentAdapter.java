package com.xiezhenqi.business.more.mazing.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiezhenqi.business.more.mazing.fragments.RVFragments;

/**
 * RVFragmentAdapter
 * Created by Tse on 2016/12/3.
 */

public class RVFragmentAdapter extends FragmentPagerAdapter {

    private static final int NUM_ITEMS = 2;

    public RVFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return new RVFragments();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "哈哈";
    }
}
