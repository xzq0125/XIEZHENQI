package com.xiezhenqi.business.more.mazing.managers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.business.more.mazing.adapters.MainFragmentPagerAdapter;

/**
 * 顶部栏管理器
 * Created by Alex on 2016/5/4.
 */
public abstract class TitleViewManager {

    public abstract void initManager(Context context, ViewGroup replaceView);

    public void onAddAction(IntentFilter filter) {

    }

    public void onLocalBroadcastReceive(Context context, Intent intent) {

    }

    public abstract View getReplaceView(MainFragmentPagerAdapter.PagerType type);

    public abstract void onAnimation(ViewGroup replace, int correct, int next, float offset,
                                     int count, MainFragmentPagerAdapter.PagerType correctType,
                                     MainFragmentPagerAdapter.PagerType nextType);

    public abstract void onSelected(ViewGroup replace, int position, int count,
                                    MainFragmentPagerAdapter.PagerType type);
}
