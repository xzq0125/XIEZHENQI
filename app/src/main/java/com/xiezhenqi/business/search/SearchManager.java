package com.xiezhenqi.business.search;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.MaterialLoadingProgressDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.business.search.adapters.SearchSongAdapter;
import com.xiezhenqi.entity.search.SearchSongDto;
import com.xiezhenqi.request.RequestError;
import com.xiezhenqi.request.RequestFactory;
import com.xiezhenqi.request.RequestTask;
import com.xiezhenqi.utils.RecyclerViewUtils;
import com.xiezhenqi.utils.SoftInputUtils;
import com.xiezhenqi.utils.StringUtils;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.lang.ref.WeakReference;
import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * SearchManager
 * Created by Tse on 2016/10/23.
 */

public class SearchManager implements TextWatcher,
        RequestTask.OnTaskListener,
        BaseLoadMoreAdapter.OnLoadMoreCallback,
        StateFrameLayout.OnStateClickListener, TextView.OnEditorActionListener {

    @BindView(R.id.search_song_sfl)
    StateFrameLayout sflState;
    @BindView(R.id.search_song_rv)
    RecyclerView rvSong;
    @BindView(R.id.search_song_edt_content)
    EditText edtContent;
    @BindView(R.id.search_song_ibtn_clear)
    ImageButton iBtnClear;
    @BindView(R.id.search_song_iv_back_top)
    ImageView ivBackTop;

    private int mPage = 1;
    private final SearchSongAdapter mAdapter = new SearchSongAdapter(this);
    private WeakReference<StateFrameLayout> mLoadMoreView;
    private final LinearLayoutManager layoutManager;
    private final BackTopManager backTopManager;
    private final Activity activity;
    private final View vSearch;

    public SearchManager(Activity activity, View vSearch) {
        this.activity = activity;
        this.vSearch = vSearch;
        ButterKnife.bind(this, vSearch);
        sflState.setStateDrawables(new MaterialLoadingProgressDrawable(sflState),
                ContextCompat.getDrawable(XZQApplication.getContext(), R.drawable.ic_loading_error),
                ContextCompat.getDrawable(XZQApplication.getContext(), R.drawable.ic_loading_empty));
        sflState.setOnStateClickListener(this);
        rvSong.setLayoutManager(layoutManager = new LinearLayoutManager(activity));
        rvSong.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(activity, R.drawable.divider_song_list)));
        rvSong.setAdapter(mAdapter);
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (SoftInputUtils.isShowing(SearchManager.this.activity))
                    SoftInputUtils.hide(SearchManager.this.activity);
                if (layoutManager.findFirstVisibleItemPosition() > 30) {
                    if (newState != 0)
                        backTopManager.visible();
                } else {
                    backTopManager.gone();
                }
            }
        };
        rvSong.addOnScrollListener(onScrollListener);
        iBtnClear.setVisibility(View.GONE);
        edtContent.addTextChangedListener(this);
        edtContent.setOnEditorActionListener(this);
        backTopManager = new BackTopManager(ivBackTop);
    }


    @OnClick({R.id.search_song_ibtn_clear,
            R.id.search_song_ibtn_search,
            R.id.search_song_iv_back_top})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.search_song_ibtn_clear:
                edtContent.setText(null);
                SoftInputUtils.show(activity, edtContent);
                break;

            case R.id.search_song_ibtn_search:
                mPage = 1;
                doSearch(true);
                break;

            case R.id.search_song_iv_back_top:
                backTopManager.gone();
                RecyclerViewUtils.scrollToTopWithAnimation(rvSong);
                break;

            default:
                break;
        }
    }

    private void doSearch(boolean showLoading) {
        String content = edtContent.getText().toString();
        if (StringUtils.isNullOrEmpty(content)) {
            ToastUtils.showToast(activity, activity.getString(R.string.search_toast));
            return;
        }

        if (showLoading)
            sflState.loading();

        if (SoftInputUtils.isShowing(activity))
            SoftInputUtils.hide(activity);

        new RequestTask(this).execute(RequestFactory.searchSong(content, mPage));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        mPage = 1;
        doSearch(true);
        return true;
    }

    @Override
    public void onFailed(RequestError error) {
        if (mPage == 1) {
            sflState.error();
        } else {
            try {
                mLoadMoreView.get().error();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sflState.normal();
        }
    }

    @Override
    public void onSucceed(Object dto, Object tag) {
        sflState.normal();
        if (dto != null && dto instanceof SearchSongDto) {
            SearchSongDto songDto = (SearchSongDto) dto;
            if (songDto.pagebean == null || songDto.pagebean.contentlist == null)
                return;

            List<SearchSongDto.PageBean.ContentListBean> data = songDto.pagebean.contentlist;

            boolean hasNext = data.size() > 0;
            if (mPage == 1) {
                if (data.isEmpty())
                    sflState.empty();
                mAdapter.setData(data, hasNext);
            } else {
                mAdapter.addData(data, hasNext);
            }
            mPage++;
        }
    }

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore, Object lastData) {
        mLoadMoreView = new WeakReference<>(loadMore);
        doSearch(false);
    }

    @Override
    public void onReloadClick(StateFrameLayout loadMore) {
        mLoadMoreView = new WeakReference<>(loadMore);
        doSearch(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        iBtnClear.setVisibility(StringUtils.isNullOrEmpty(s.toString().trim()) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        layout.loading();
        mPage = 1;
        doSearch(true);
    }

}
