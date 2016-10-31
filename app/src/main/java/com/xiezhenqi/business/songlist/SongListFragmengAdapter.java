package com.xiezhenqi.business.songlist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * SongListFragmengAdapter
 * Created by Tse on 2016/10/30.
 */

public class SongListFragmengAdapter extends FragmentPagerAdapter {

    public SongListFragmengAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
            case 0:
                return new SingerFragment();
            case 1:
                return new SongNameFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default:
            case 0:
                return SingerFragment.getPageTitle();
            case 1:
                return SongNameFragment.getPageTitle();
        }
    }
}
