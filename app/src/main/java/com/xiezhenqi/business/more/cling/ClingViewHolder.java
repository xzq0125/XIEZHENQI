package com.xiezhenqi.business.more.cling;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;

/**
 * ClingViewHolder
 * Created by Tse on 2017/12/17.
 */

public class ClingViewHolder extends RecyclerView.ViewHolder {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    private TextView tvCount;

    public ClingViewHolder(View itemView, int viewType) {
        super(itemView);
        switch (viewType) {
            default:
            case TYPE_HEADER:
                break;
            case TYPE_NORMAL:
                tvCount = (TextView) itemView.findViewById(R.id.tv_count);
                break;
        }

    }

    public void setHeader() {

    }

    public void setNormal(int normal) {
        tvCount.setText("Item " + normal);
    }
}
