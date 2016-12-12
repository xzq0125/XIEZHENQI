package com.xiezhenqi.business.more.galleryviewpager.adapters;

import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * GuideAdapter
 * Created by sean on 2016/1/14.
 */
public class GuideAdapter extends RecyclerPagerAdapter<GuideAdapter.GuideViewHolder> {

    private int[] pics;

    public GuideAdapter(int[] pics) {
        this.pics = pics;
    }

    @Override
    public int getItemCount() {
        return pics == null ? 0 : pics.length;
    }

    @Override
    public GuideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GuideViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(GuideViewHolder holder, int position) {
        holder.setData2Ui(pics[position]);
    }

    class GuideViewHolder extends RecyclerPagerAdapter.PagerViewHolder {

        ImageView guideView;

        public GuideViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            guideView = (ImageView) itemView;
        }

        public void setData2Ui(int resId) {
            guideView.setScaleType(ImageView.ScaleType.FIT_XY);
            guideView.setImageResource(resId);
        }
    }
}
