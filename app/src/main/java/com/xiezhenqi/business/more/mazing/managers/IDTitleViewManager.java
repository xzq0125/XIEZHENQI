package com.xiezhenqi.business.more.mazing.managers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.business.more.mazing.adapters.IDFragmentPagerAdapter;
import com.xiezhenqi.utils.ToastUtils;

public class IDTitleViewManager extends TitleViewManager implements View.OnClickListener {

    private Context mContext;
    private View mTastyTitle;
    private View mStarTitle;
    private View mLikeTitle;

    public void initManager(Context context, ViewGroup replaceView) {
        mContext = context;
        mTastyTitle = LayoutInflater.from(mContext).inflate(R.layout.item_id_title, replaceView, false);
        mStarTitle = LayoutInflater.from(mContext).inflate(R.layout.item_id_title, replaceView, false);
        mLikeTitle = LayoutInflater.from(mContext).inflate(R.layout.item_id_title, replaceView, false);
    }

    @Override
    public void onAddAction(IntentFilter filter) {
        super.onAddAction(filter);
    }

    @Override
    public void onLocalBroadcastReceive(Context context, Intent intent) {
        super.onLocalBroadcastReceive(context, intent);
    }

    @Override
    public View getReplaceView(IDFragmentPagerAdapter.PagerType type) {
        switch (type) {
            case TASTY:
                return mTastyTitle;
            case STAR:
                return mStarTitle;
            case LIKE:
                return mLikeTitle;
            default:
                return null;
        }
    }

    @Override
    public void onAnimation(ViewGroup replace, int correct, int next, float offset, int count,
                            IDFragmentPagerAdapter.PagerType correctType,
                            IDFragmentPagerAdapter.PagerType nextType) {
        View correctV = getReplaceView(correctType);
        View nextV = getReplaceView(nextType);
        if (correctV != null) {
            correctV.setAlpha(offset);
        }
        if (nextV != null) {
            nextV.setAlpha(1F - offset);
        }
        replace.setVisibility(View.VISIBLE);
        boolean isGotoHide = correct == count - 2 && next == count - 1;
        boolean isGotoShow = correct == count - 1 && next == count - 2;
        if (isGotoHide) {
            replace.setAlpha(offset);
        }
        if (isGotoShow) {
            replace.setAlpha(1F - offset);
        }
    }

    @Override
    public void onSelected(ViewGroup replace, int position, int count,
                           IDFragmentPagerAdapter.PagerType type) {
        boolean shouldGone = position == count - 1;
        View child = getReplaceView(type);
        if (child != null) {
            child.setAlpha(1);
        }
        if (shouldGone) {
            replace.setVisibility(View.GONE);
        } else {
            replace.setAlpha(1);
            replace.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        ToastUtils.showToast(mContext, v.toString());
    }
}