package com.xiezhenqi.base.list.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.fragments.BroadcastFragment;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayout;
import com.xiezhenqi.widget.pulldownrefresh.RefreshLayoutHeader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * BaseListFragment
 * Created by Tse on 2016/10/31.
 */

public abstract class BaseListFragment extends BroadcastFragment
        implements RefreshLayout.OnRefreshListener {

    @Bind(R.id.rl)
    RefreshLayout pullDownRefresh;

    @Bind(android.R.id.empty)
    ImageView empty;

    @Bind(android.R.id.list)
    RecyclerView recyclerView;


    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ButterKnife.bind(this, getView());
        RefreshLayoutHeader header = new RefreshLayoutHeader(getActivity());
        pullDownRefresh.addOnRefreshListener(header);
        pullDownRefresh.addOnRefreshListener(this);
        pullDownRefresh.setRefreshHeader(header);
        pullDownRefresh.autoRefresh();
        initRecyclerView(recyclerView, pullDownRefresh);
    }

    protected abstract void initRecyclerView(RecyclerView recyclerView, ViewGroup container);

    protected abstract void loadData(boolean loadFirstPage);

    protected void showNormal() {
        empty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        pullDownRefresh.refreshComplete();
    }

    protected void showEmpty() {
        empty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
}
