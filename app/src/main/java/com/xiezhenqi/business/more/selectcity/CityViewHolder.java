package com.xiezhenqi.business.more.selectcity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;

/**
 * CityViewHolder
 * Created by Wesley on 2018/1/5.
 */

public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvName;
    private CityDto data;
    private int position;

    public CityViewHolder(View itemView, int viewType, OnItemClickListener listener) {
        super(itemView);
        switch (viewType) {
            default:
            case 0: {
                this.listener = listener;
                itemView.setOnClickListener(this);
                tvName = (TextView) itemView.findViewById(R.id.icl_tv_name);
            }
            break;

            case 1: {
                this.listener = listener;
                itemView.setOnClickListener(this);
            }
            break;
        }

    }

    public void setData(CityDto data, int position) {
        this.data = data;
        this.position = position;
        tvName.setText(data.name);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onCityClick(data, position);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onCityClick(CityDto city, int position);
    }
}
