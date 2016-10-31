package com.xiezhenqi.business.songlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.R;

import java.util.List;

/**
 * SongAdapter
 * Created by Tse on 2016/10/30.
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> implements View.OnClickListener {

    private List<String> mData;
    private final OnHolderClickListener listener;

    public SongAdapter(OnHolderClickListener listener) {
        this.listener = listener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_list, parent, false));
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.itemView.setOnClickListener(this);
        holder.setData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<String> song) {
        mData = song;
        notifyDataSetChanged();
    }

    boolean checkIndex(int index) {
        return mData != null && index >= 0 && index < mData.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null && v.getTag() != null && v.getTag() instanceof Integer) {
            int pos = (int) v.getTag();
            if (checkIndex(pos))
                listener.onItemClick(mData.get(pos), pos);
        }
    }

    public interface OnHolderClickListener {
        void onItemClick(String s, int pos);
    }
}
