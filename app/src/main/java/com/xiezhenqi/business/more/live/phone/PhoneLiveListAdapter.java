package com.xiezhenqi.business.more.live.phone;

import android.view.View;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;

/**
 * PhoneLiveListAdapter
 * Created by sean on 2017/4/12.
 */

class PhoneLiveListAdapter extends BaseLoadMoreAdapter<PhoneLiveDto.DataDto, PhoneLiveListViewHolder> {

    PhoneLiveListAdapter(OnLoadMoreCallback loadMoreCallback) {
        super(loadMoreCallback);
    }

    @Override
    public PhoneLiveListViewHolder onCreateNormalViewHolder(View itemView) {
        return new PhoneLiveListViewHolder(itemView);
    }

    @Override
    public void onConvert(PhoneLiveListViewHolder holder, int position) {
        holder.setData(getDataAt(position));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_phone_list;
    }
}
