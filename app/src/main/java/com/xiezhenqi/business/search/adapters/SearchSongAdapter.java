package com.xiezhenqi.business.search.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.business.search.viewholders.SearchSongViewHolder;
import com.xiezhenqi.entity.search.SearchSongDto;
import com.xiezhenqi.utils.RecyclerViewUtils;

/**
 * SearchSongAdapter
 * Created by Tse on 2016/10/20.
 */

public class SearchSongAdapter extends BaseLoadMoreAdapter<SearchSongDto.PageBean.ContentListBean, SearchSongViewHolder> {

    public SearchSongAdapter(OnLoadMoreCallback loadMoreCallback) {
        super(loadMoreCallback);
    }

    @Override
    public SearchSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isNormalView, View itemView) {
        return new SearchSongViewHolder(itemView, isNormalView);
    }

    @Override
    public void onConvert(SearchSongViewHolder holder, int position) {
        holder.setData(getDataAt(position));
        RecyclerViewUtils.setItemViewTopMargin(holder.itemView, R.dimen.common_item_marginTop, position == 0);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_search_normal;
    }
}
