package com.xiezhenqi.business.search.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.entity.search.SearchSongDto;
import com.xiezhenqi.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * SearchSongViewHolder
 * Created by Tse on 2016/10/20.
 */

public class SearchSongViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.item_search_songName)
    TextView tvSongName;
    @Bind(R.id.item_search_singerName)
    TextView tvSingerName;
    @Bind(R.id.item_search_special)
    TextView tvSpecial;

    public SearchSongViewHolder(View itemView, boolean isNormalView) {
        super(itemView);
        if (isNormalView)
            ButterKnife.bind(this, itemView);
    }

    public void setData(SearchSongDto.PageBean.ContentListBean data) {
        if (data == null)
            return;
        tvSongName.setText(data.songname);
        tvSingerName.setText(data.singername);
        tvSpecial.setText(StringUtils.isNullOrEmpty(data.albumname) ? null : " － " + data.albumname);
    }
}
