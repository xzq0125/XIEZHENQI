package com.xiezhenqi.business.more.mazing.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.base.fragments.LazyLoadFragment;
import com.xiezhenqi.business.search.SearchActivity;
import com.xiezhenqi.business.songlist.SongAdapter;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ArrayListFragment
 * Created by Tse on 2016/12/3.
 */

public class RVFragments extends LazyLoadFragment implements SongAdapter.OnHolderClickListener, Runnable {

    @Bind(R.id.rv)
    RecyclerView rv;
    private SongAdapter songAdapter;
    private boolean loadData = false;

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_pager_list2;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        songAdapter = new SongAdapter(this);
        rv.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.divider_common_horizontal)));
        rv.setAdapter(songAdapter);
    }

    @Override
    protected void loadData() {
        refreshData();
    }

    @Override
    public void refreshData() {
        songAdapter.setData(null);
        rv.postDelayed(this, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(String s, int pos) {
        SearchActivity.start(getActivity(), s);
    }

    @Override
    public void run() {
        List<String> list = Arrays.asList(getActivity().getResources().getStringArray(R.array.song_name_list));
        loadData = true;
        XZQApplication.sendLocalBroadcast("refreshComplete");
        songAdapter.setData(list);
    }

    public boolean isLoadData() {
        return loadData;
    }
}
