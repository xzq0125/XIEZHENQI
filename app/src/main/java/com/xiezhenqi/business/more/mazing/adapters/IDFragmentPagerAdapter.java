package com.xiezhenqi.business.more.mazing.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.xiezhenqi.business.more.mazing.fragments.LikeFragment;
import com.xiezhenqi.business.more.mazing.fragments.MainFragment;
import com.xiezhenqi.business.more.mazing.fragments.StarFragment;
import com.xiezhenqi.business.more.mazing.fragments.TastyFragment;
import com.xiezhenqi.business.more.mazing.managers.TitleViewManager;

import static com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter.PagerType.LIKE;
import static com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter.PagerType.STAR;
import static com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter.PagerType.TASTY;

/**
 * IDFragmentPagerAdapter
 * Created by sean on 2016/12/2.
 */

public class IDFragmentPagerAdapter extends MainFragmentPagerAdapter {

    public IDFragmentPagerAdapter(FragmentManager fm, TitleViewManager manager, Bundle startData) {
        super(fm, manager, startData);
    }

    @Override
    public MainFragment getPageFragment(int position) {
        switch (positionToType(position)) {
            case TASTY:
                return new TastyFragment();
            case STAR:
                return new StarFragment();
            case LIKE:
                return new LikeFragment();
        }
        return null;
    }

    @Override
    public int typeToPosition(PagerType type) {
        switch (type) {
            case TASTY:
                return 0;
            case STAR:
                return 1;
            case LIKE:
                return 2;
        }
        return -1;
    }

    @Override
    public PagerType positionToType(int position) {
        switch (position) {
            default:
            case 0:
                return TASTY;
            case 1:
                return STAR;
            case 2:
                return LIKE;
        }
    }

    @Override
    public Drawable getNormalDrawable(int position, Context context) {
        switch (positionToType(position)) {
            default:
            case TASTY:
                return TastyFragment.getNormalDrawable(context);
            case STAR:
                return StarFragment.getNormalDrawable(context);
            case LIKE:
                return LikeFragment.getNormalDrawable(context);
        }
    }

    @Override
    public Drawable getSelectedDrawable(int position, Context context) {
        switch (positionToType(position)) {
            default:
            case TASTY:
                return TastyFragment.getSelectedDrawable(context);
            case STAR:
                return StarFragment.getSelectedDrawable(context);
            case LIKE:
                return LikeFragment.getSelectedDrawable(context);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
