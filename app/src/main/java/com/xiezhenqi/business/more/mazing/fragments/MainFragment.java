package com.xiezhenqi.business.more.mazing.fragments;

import android.os.Bundle;
import android.view.View;

import com.xiezhenqi.base.fragments.BaseFragment;


/**
 * 主页Fragment
 * Created by Alex on 2015/11/18.
 */
public abstract class MainFragment extends BaseFragment {

    public static final String EXTRA_FIRST_POSITION = "first_position";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DATA = "data";
    private int position = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        position = getFirstPosition();
        if (position == getPosition()) {
            onSelected();
        }
    }

    public Bundle getStartData() {
        return getArguments().getBundle(EXTRA_DATA);
    }

    /**
     * 获取位置
     *
     * @return 位置
     */
    public int getPosition() {
        return getArguments().getInt(EXTRA_POSITION, 0);
    }

    /**
     * 页面变化
     */
    public void onPageChanged(int position) {
        this.position = position;
        if (position == getPosition()) {
            onSelected();
        }
    }

    /**
     * 设置顶部View
     * 此时Fragment仅实例化，还未onCreate
     * @param title 顶部View
     */
    public void bindTitleView(View title) {

    }

    /**
     * 获取主页位置
     *
     * @return 主页位置
     */
    public final int getPagePosition() {
        return position;
    }

    /**
     * 被选中
     */
    protected void onSelected() {

    }

    /**
     * 是否被选中
     *
     * @return 是否被选中
     */
    public boolean isSelected() {
        return position == getPosition();
    }

    /**
     * 是否处于活动状态
     *
     * @return 是否处于活动状态
     */
    public boolean isActive() {
        return isResume() && isSelected();
    }

    /**
     * 标签是否开启
     *
     * @return 是否开启
     */
    public boolean isTabTagEnable() {
        return false;
    }

    /**
     * 标签
     *
     * @return 标签文字
     */
    public String getTabTag() {
        return null;
    }

    /**
     * 获取初始位置
     *
     * @return 初始位置
     */
    public int getFirstPosition() {
        return getArguments().getInt(EXTRA_FIRST_POSITION, 0);
    }

    public void onTabClick(int position) {

    }

    public void onSelectedTabClick(int position) {

    }

    public void onTabDoubleClick(int position) {

    }
}
