package com.xiezhenqi.business.more.selectcity;

import android.view.View;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;

/**
 * SearchResultAdapter
 * Created by Wesley on 2018/1/6.
 */

public class SearchResultAdapter extends BaseLoadMoreAdapter<CityDto, SearchResultViewHolder> {

    private final SearchResultViewHolder.OnResultCityClickListener listener;

    public SearchResultAdapter(SearchResultViewHolder.OnResultCityClickListener listener) {
        super(null);
        this.listener = listener;
    }

    @Override
    public SearchResultViewHolder onCreateNormalViewHolder(View itemView) {
        return new SearchResultViewHolder(itemView, listener);
    }

    @Override
    public void onConvert(SearchResultViewHolder holder, int position) {
        holder.setData(getDataAt(position));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_city_list;
    }
}
