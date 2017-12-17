package com.xiezhenqi.business.more.cling;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiezhenqi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ClingAdapter
 * Created by Tse on 2017/12/17.
 */

public class ClingAdapter extends RecyclerView.Adapter<ClingViewHolder> {

    private List<Integer> data = new ArrayList<>();

    @Override
    public ClingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case ClingViewHolder.TYPE_HEADER:
                return new ClingViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_cling_header, parent, false), viewType);
            case ClingViewHolder.TYPE_NORMAL:
                return new ClingViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_cling_normal, parent, false),viewType);
        }
    }

    @Override
    public void onBindViewHolder(ClingViewHolder holder, int position) {
        if (position == 0) {
            holder.setHeader();
            return;
        }
        holder.setNormal(data.get(position - 1));
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ClingViewHolder.TYPE_HEADER : ClingViewHolder.TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public void setData(List<Integer> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
