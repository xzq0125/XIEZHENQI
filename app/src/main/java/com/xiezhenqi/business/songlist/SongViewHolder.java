package com.xiezhenqi.business.songlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * SongViewHolder
 * Created by Tse on 2016/10/30.
 */

public class SongViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_song)
    TextView tvSong;

    public SongViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(String data, int position) {
        tvSong.setText(data);
        tvSong.setTag(position);
    }
}
