package com.xiezhenqi.business.more.selectcity;

import android.support.v7.widget.RecyclerView;
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
            case 1: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_city_hot, parent, false);
                return new CityViewHolder(itemView, viewType, listener);
            }

            case 0: {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_city_list, parent, false);
                return new CityViewHolder(itemView, viewType, listener);
            }
        }

    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        if (position != 0)
            holder.setData(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        return 0;
    }

    public void setData(List<CityDto> list) {
        if (list != null) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public boolean isFirstOfGroup(int position) {
        if (position == 0) {
            return true;
        }
        int prevPosition = position - 1;
        if (list.get(prevPosition).first_letter.equals(list.get(position).first_letter)) {
            return false;
        }
        return true;
    }

    public long getGroupId(int position) {
        return Character.toUpperCase(list.get(position).first_letter.charAt(0));
    }


    public String getGroupName(int index) {
        if (index == 0) {
            return list.get(index).name;
        }
        return list.get(index).first_letter;
    }

    public int getSelectedPosition(String letter) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).first_letter.equals(letter)) {
                return i;
            }
        }
        return -1;
    }
}
