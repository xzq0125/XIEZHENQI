package com.xiezhenqi.business.musicpavilion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.R;
import com.xiezhenqi.business.action.BroadcastAction;
import com.xiezhenqi.business.musicpavilion.viewholders.MusicPavilionViewHolder;
import com.xiezhenqi.entity.musicpavilion.MusicPavilionDto;

import java.util.ArrayList;
import java.util.List;

/**
 * MusicPavilionAdapter
 * Created by Tse on 2016/10/19.
 */

public class MusicPavilionAdapter extends RecyclerView.Adapter<MusicPavilionViewHolder> implements View.OnClickListener {

    private List<MusicPavilionDto.PageBean> list;
    public static final int HERDER = 0;
    public static final int NORMAL = 1;

    @Override
    public MusicPavilionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case HERDER:
                return new MusicPavilionViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_music_pavilion_header, parent, false), viewType, this);
            case NORMAL:
                return new MusicPavilionViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_music_pavilion_normal, parent, false), viewType, this);
        }
    }

    @Override
    public void onBindViewHolder(MusicPavilionViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(MusicPavilionViewHolder holder, int position, List<Object> payloads) {
        if (position > 0) {

            if (payloads != null && !payloads.isEmpty()) {
                holder.updatePlayStatus(list.get(position));
                return;
            }

            holder.setData(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HERDER : NORMAL;
    }

    public void setData(List<MusicPavilionDto.PageBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(MusicPavilionDto.PageBean data) {
        if (list == null)
            list = new ArrayList<>();
        int pos = list.size();
        this.list.add(data);
        notifyItemInserted(pos);
    }

    @Override
    public void onClick(View v) {

        Object tag = v.getTag();

        if (tag != null && tag instanceof MusicPavilionDto.PageBean) {

            MusicPavilionDto.PageBean clickItem = (MusicPavilionDto.PageBean) tag;

            for (MusicPavilionDto.PageBean data : list) {
                if (data == clickItem) {
                    XZQApplication.sendLocalBroadcast(BroadcastAction.ACTION_MUSIC_PAUSE);
                    if (clickItem.isSongBegin()) {
                        clickItem.stopPlay();
                    } else {
                        clickItem.startPlay();
                        XZQApplication.sendLocalBroadcast(BroadcastAction.ACTION_MUSIC_START);
                    }
                } else {
                    data.stopPlay();
                }

            }

            notifyItemRangeChanged(0, list.size(), "updatePlayStatus");
        }

    }

}
