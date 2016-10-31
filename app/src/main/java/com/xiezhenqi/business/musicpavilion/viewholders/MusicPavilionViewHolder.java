package com.xiezhenqi.business.musicpavilion.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiezhenqi.R;
import com.xiezhenqi.business.musicpavilion.adapters.MusicPavilionAdapter;
import com.xiezhenqi.entity.musicpavilion.MusicPavilionDto;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * MusicPavilionViewHolder
 * Created by Tse on 2016/10/19.
 */
@SuppressWarnings("all")
public class MusicPavilionViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.music_pavilion_iv_pic)
    ImageView ivPic;
    @Bind(R.id.music_pavilion_iv_play)
    ImageView ivPlay;
    @Bind(R.id.music_pavilion_iv_pause)
    ImageView ivPause;
    @Bind(R.id.music_pavilion_tv_name1)
    TextView tvName1;
    @Bind(R.id.music_pavilion_tv_name2)
    TextView tvName2;
    @Bind(R.id.music_pavilion_tv_name3)
    TextView tvName3;
    @Bind(R.id.music_pavilion_tv_singer1)
    TextView tvSinger1;
    @Bind(R.id.music_pavilion_tv_singer2)
    TextView tvSinger2;
    @Bind(R.id.music_pavilion_tv_singer3)
    TextView tvSinger3;
    @Bind(R.id.music_pavilion_tv_topName)
    TextView tvTopName;

    public MusicPavilionViewHolder(View itemView, int viewType, View.OnClickListener listener) {
        super(itemView);
        switch (viewType) {
            default:
            case MusicPavilionAdapter.HERDER:
                break;

            case MusicPavilionAdapter.NORMAL:
                ButterKnife.bind(this, itemView);
                ivPlay.setOnClickListener(listener);
                ivPause.setOnClickListener(listener);
                break;
        }

    }

    public void setData(MusicPavilionDto.PageBean data) {

        MusicPavilionDto.Detail first = data.songlist.get(0);
        MusicPavilionDto.Detail second = data.songlist.get(1);
        MusicPavilionDto.Detail third = data.songlist.get(2);

        ivPlay.setTag(data);
        ivPause.setTag(data);

        tvName1.setText("1   " + first.songname);
        tvName2.setText("2   " + second.songname);
        tvName3.setText("3   " + third.songname);

        tvSinger1.setText(first.getSingerName(itemView.getContext()));
        tvSinger2.setText(second.getSingerName(itemView.getContext()));
        tvSinger3.setText(third.getSingerName(itemView.getContext()));

        Glide.with(ivPic.getContext())
                .load(first.albumpic_big)
                .placeholder(R.color.common_bg)
                .centerCrop()
                .into(ivPic);
        tvTopName.setText(data.top_name);

        ivPlay.setVisibility(data.isSongBegin() ? View.GONE : View.VISIBLE);
        ivPause.setVisibility(data.isSongBegin() ? View.VISIBLE : View.GONE);
    }

    public void updatePlayStatus(MusicPavilionDto.PageBean data) {
        ivPlay.setVisibility(data.isSongBegin() ? View.GONE : View.VISIBLE);
        ivPause.setVisibility(data.isSongBegin() ? View.VISIBLE : View.GONE);
    }
}






