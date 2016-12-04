package com.xiezhenqi.business.more.mazing.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiezhenqi.business.more.mazing.fragments.RVFragments;

import java.util.HashMap;
import java.util.Map;

/**
 * RVFragmentAdapter
 * Created by Tse on 2016/12/3.
 */

public class RVFragmentAdapter extends FragmentPagerAdapter {

    private Map<String, RVFragments> fragmentsMap = new HashMap<>();

    private static final int NUM_ITEMS = 2;
    private String fragmentName;

    public RVFragmentAdapter(FragmentManager fm, String fragmentName) {
        super(fm);
        this.fragmentName = fragmentName;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        RVFragments fragments = new RVFragments();
        fragmentsMap.put(getPageTitle(position).toString(), fragments);
        return fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentName + (position + 1);
    }

    public RVFragments getFragmentsByTabName(String tabName) {
        return fragmentsMap.get(tabName);
    }
}
