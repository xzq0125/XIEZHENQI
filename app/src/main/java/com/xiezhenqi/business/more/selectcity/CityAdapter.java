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

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_list, parent, false);
        return new CityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
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

    public String getGroupName(int index) {
        return list.get(index).first_letter.toUpperCase();
    }
}
