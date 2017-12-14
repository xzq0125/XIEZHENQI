package com.xiezhenqi.business.search.viewholders;

import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;
import com.xiezhenqi.entity.search.SearchSongDto;
import com.xiezhenqi.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SearchSongViewHolder
 * Created by Tse on 2016/10/20.
 */

public class SearchSongViewHolder extends BaseLoadMoreViewHolder {

    @BindView(R.id.item_search_songName)
    TextView tvSongName;
    @BindView(R.id.item_search_singerName)
    TextView tvSingerName;
    @BindView(R.id.item_search_special)
    TextView tvSpecial;

    public SearchSongViewHolder(View itemView) {
        super(itemView);
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
