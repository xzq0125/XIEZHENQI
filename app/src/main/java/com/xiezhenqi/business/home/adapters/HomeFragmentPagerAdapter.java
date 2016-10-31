package com.xiezhenqi.business.home.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.business.home.fragments.AFragment;
import com.xiezhenqi.business.home.fragments.BFragment;
import com.xiezhenqi.business.home.fragments.CFragment;
import com.xiezhenqi.business.home.fragments.DFragment;
import com.xiezhenqi.widget.gradienttabview.GradientTabView;

/**
 * HomeFragmentPagerAdapter
 * Created by Tse on 2016/10/29.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter
        implements GradientTabView.GradientTabAdapter {

    public enum PagerType {
        A(0),
        B(1),
        C(2),
        D(3);
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
                    return A;
                case 1:
                    return B;
                case 2:
                    return C;
                case 3:
                    return D;
                default:
                    return null;
            }
        }
    }

    public PagerType positionToType(int position) {
        switch (position) {
            default:
            case 0:
                return PagerType.A;
            case 1:
                return PagerType.B;
            case 2:
                return PagerType.C;
            case 3:
                return PagerType.D;
        }
    }

    public int typeToPosition(PagerType type) {
        switch (type) {
            case A:
                return 0;
            case B:
                return 1;
            case C:
                return 2;
            case D:
                return 3;
            default:
                return -1;
        }
    }

    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (positionToType(position)) {
            default:
            case A:
                return new AFragment();
            case B:
                return new BFragment();
            case C:
                return new CFragment();
            case D:
                return new DFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (positionToType(position)) {
            default:
            case A:
                return AFragment.getPageTitle(XZQApplication.getContext());
            case B:
                return BFragment.getPageTitle(XZQApplication.getContext());
            case C:
                return CFragment.getPageTitle(XZQApplication.getContext());
            case D:
                return DFragment.getPageTitle(XZQApplication.getContext());
        }
    }

    @Override
    public Bitmap getNormalDrawable(int position, Context context) {
        switch (positionToType(position)) {
            default:
            case A:
                return AFragment.getNormalDrawable(XZQApplication.getContext());
            case B:
                return BFragment.getNormalDrawable(XZQApplication.getContext());
            case C:
                return CFragment.getNormalDrawable(XZQApplication.getContext());
            case D:
                return DFragment.getNormalDrawable(XZQApplication.getContext());
        }
    }

    @Override
    public Bitmap getSelectedDrawable(int position, Context context) {
        switch (positionToType(position)) {
            default:
            case A:
                return AFragment.getSelectedDrawable(XZQApplication.getContext());
            case B:
                return BFragment.getSelectedDrawable(XZQApplication.getContext());
            case C:
                return CFragment.getSelectedDrawable(XZQApplication.getContext());
            case D:
                return DFragment.getSelectedDrawable(XZQApplication.getContext());
        }
    }
}
