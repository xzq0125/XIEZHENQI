package com.xiezhenqi.business.more.selectcity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;

/**
 * CityViewHolder
 * Created by Wesley on 2018/1/5.
 */

public class CityViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private CityDto data;

    public CityViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.icl_tv_name);
    }

    public void setData(CityDto data) {
        this.data = data;
        tvName.setText(data.name);
    }
}
