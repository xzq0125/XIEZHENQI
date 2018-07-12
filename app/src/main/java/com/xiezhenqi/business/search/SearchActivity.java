package com.xiezhenqi.business.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.activity.BaseListActivity;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.business.search.adapters.SearchSongAdapter;
import com.xiezhenqi.entity.search.SearchSongDto;
import com.xiezhenqi.request.RequestError;
import com.xiezhenqi.request.RequestFactory;
import com.xiezhenqi.request.RequestTask;
import com.xiezhenqi.utils.StringUtils;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.lang.ref.WeakReference;
import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;

public class SearchActivity extends BaseListActivity implements
        BaseLoadMoreAdapter.OnLoadMoreCallback,
        RequestTask.OnTaskListener {

    private static final String EXTRA_KEYWORD = "keyword";
    private SearchActivity me = this;
    private int mPage = 1;
    private SearchSongAdapter mAdapter;
    private WeakReference<StateFrameLayout> mLoadMoreView;
    private String keyword = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        keyword = getIntent().getStringExtra(EXTRA_KEYWORD);
        if (StringUtils.isNullOrEmpty(keyword))
            finish();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initRecyclerView(RecyclerView recyclerView, ViewGroup container) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new SearchSongAdapter(me));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(me, R.drawable.divider_song_list)));
        container.setBackgroundResource(R.color.common_bg);
    }

    @Override
    protected void loadData(boolean loadFirstPage) {
        mPage = loadFirstPage ? 1 : mPage;
        new RequestTask(me).execute(RequestFactory.searchSong(keyword, mPage));
    }

    @Override
    protected String getMyTitle() {
        return "搜索歌曲";
    }

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore, Object lastData) {
        mLoadMoreView = new WeakReference<>(loadMore);
        loadData(false);
    }

    @Override
    public void onReloadClick(StateFrameLayout loadMore) {
        mLoadMoreView = new WeakReference<>(loadMore);
        loadData(false);
    }

    @Override
    public void onFailed(RequestError error) {
        ToastUtils.showToast(me, error.getMessage());
        if (mPage == 1) {
            showNormal();
        } else {
            try {
                mLoadMoreView.get().error();
            } catch (Exception e) {
                e.printStackTrace();
            }
            showNormal();
        }
    }

    @Override
    public void onSucceed(Object dto, Object tag) {

        showNormal();

        if (dto != null && dto instanceof SearchSongDto) {
            SearchSongDto songDto = (SearchSongDto) dto;
            if (songDto.pagebean == null || songDto.pagebean.contentlist == null)
                return;

            List<SearchSongDto.PageBean.ContentListBean> data = songDto.pagebean.contentlist;

            boolean hasNext = data.size() > 0;
            if (mPage == 1) {
                if (data.isEmpty())
                    showEmpty();
                mAdapter.setData(data, hasNext);
            } else {
                mAdapter.addData(data, hasNext);
                if (!hasNext)
                    ToastUtils.showToast(this, "没有更多了");
            }
            mPage++;
        }
    }

    public static void start(Context context, String keyword) {
        Intent starter = new Intent(context, SearchActivity.class);
        starter.putExtra(EXTRA_KEYWORD, keyword);
        context.startActivity(starter);
    }
}
