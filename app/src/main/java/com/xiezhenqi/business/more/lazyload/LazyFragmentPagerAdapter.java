package com.xiezhenqi.business.more.lazyload;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * LazyFragmentPagerAdapter
 * Created by sean on 2016/11/30.
 */

public class LazyFragmentPagerAdapter extends FragmentPagerAdapter {

    public LazyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
            case 0:
                return new SingerFragment2();
            case 1:
                return new SongNameFragment2();
            case 2:
                return new DFragment2();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default:
            case 0:
                return SingerFragment2.getPageTitle();
            case 1:
                return SongNameFragment2.getPageTitle();
            case 2:
                return DFragment2.getPageTitle();
        }
    }
}
