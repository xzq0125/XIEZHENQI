package com.xiezhenqi.business.more.mazing.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xiezhenqi.business.more.mazing.fragments.ArrayListFragment;

/**
 * FragmentListAdapter
 * Created by Tse on 2016/12/3.
 */

public class FragmentListAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_ITEMS = 4;

    public FragmentListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return ArrayListFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position + 1);
    }
}
