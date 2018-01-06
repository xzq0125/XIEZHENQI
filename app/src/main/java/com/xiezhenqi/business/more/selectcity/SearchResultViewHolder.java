package com.xiezhenqi.business.more.selectcity;

import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;

/**
 * SearchResultViewHolder
 * Created by Wesley on 2018/1/6.
 */

public class SearchResultViewHolder extends BaseLoadMoreViewHolder implements View.OnClickListener {

    private final TextView tvName;
    private CityDto data;

    public SearchResultViewHolder(View itemView, OnResultCityClickListener listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
        tvName = (TextView) itemView.findViewById(R.id.icl_tv_name);
    }

    public void setData(CityDto data) {
        this.data = data;
        tvName.setText(data.name);
    }

    @Override
    public void onClick(View v) {
        if (listener != null && data != null) {
            listener.onCityClick(data);
        }
    }

    private final OnResultCityClickListener listener;

    public interface OnResultCityClickListener {
        void onCityClick(CityDto city);
    }
}
