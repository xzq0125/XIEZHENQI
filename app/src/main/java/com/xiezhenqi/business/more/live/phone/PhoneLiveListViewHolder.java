package com.xiezhenqi.business.more.live.phone;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiezhenqi.R;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;

/**
 * PhoneLiveListViewHolder
 * Created by sean on 2017/4/12.
 */

class PhoneLiveListViewHolder extends BaseLoadMoreViewHolder {

    private final ImageView ivCover;

    PhoneLiveListViewHolder(View itemView) {
        super(itemView);
        ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
    }

    public void setData(PhoneLiveDto.DataDto dto) {
        itemView.setTag(dto);
        Glide.with(itemView.getContext())
                .load(dto.room_src)
                .placeholder(R.color.lineColor_toolbar)
                .into(ivCover);
    }
}
