package com.xiezhenqi.business.more.selectcity;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * CityAdapter
 * Created by Wesley on 2018/1/5.
 */

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

    private List<CityDto> list = new ArrayList<>();
    private CityViewHolder.OnItemClickListener listener;

    public CityAdapter(CityViewHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case CityViewHolder.TYPE_SEARCH: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_city_search, parent, false);
                return new CityViewHolder(itemView, viewType, listener);
            }
            case CityViewHolder.TYPE_LOCATION: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_city_hot, parent, false);
                return new CityViewHolder(itemView, viewType, listener);
            }
            case CityViewHolder.TYPE_HOT: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_city_hot, parent, false);
                return new CityViewHolder(itemView, viewType, listener);
            }
            case CityViewHolder.TYPE_NORMAL: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_city_list, parent, false);
                return new CityViewHolder(itemView, viewType, listener);
            }
        }

    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == CityViewHolder.TYPE_NORMAL)
            holder.setData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return CityViewHolder.TYPE_SEARCH;
        if (position == 1)
            return CityViewHolder.TYPE_LOCATION;
        if (position == 2)
            return CityViewHolder.TYPE_HOT;
        return CityViewHolder.TYPE_NORMAL;
    }

    public void setData(List<CityDto> list) {
        if (list != null) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public boolean isFirstOfGroup(int position) {
        if (position == 0) {
            return false;
        }
        int prevPosition = position - 1;
        String prevFirstLetter = list.get(prevPosition).first_letter;
        String nextFirstLetter = list.get(position).first_letter;
        return !TextUtils.equals(prevFirstLetter, nextFirstLetter);
    }

    public long getGroupId(int position) {
        String first_letter = list.get(position).first_letter;
        if (first_letter == null)
            return -1;
        return Character.toUpperCase(first_letter.charAt(0));
    }

    public String getGroupName(int index) {
        if (index == 0 || index == 1 || index == 2) {
            return list.get(index).name;
        }
        return list.get(index).first_letter;
    }

    public int getSelectedPosition(String letter) {
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(list.get(i).first_letter, letter))
                return i;
        }
        return -1;
    }
}
