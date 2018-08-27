package com.xiezhenqi.base.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.base.mvp.BaseListContract;
import com.xiezhenqi.newmvp.IAdapter;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;
import butterknife.BindView;

/**
 * 列表基类
 *
 * @author xzq
 */

public abstract class BaseListActivity<P extends BaseListContract.Presenter, Entity>
        extends BasePresenterActivity<P>
        implements BaseListContract.View<Entity>,
        StateFrameLayout.OnStateClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        BaseLoadMoreAdapter.OnLoadMoreCallback {

    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.sfl)
    StateFrameLayout sfl;//状态布局
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    private IAdapter<Entity> mAdapter;

    protected abstract String getPageTitle();

    protected abstract IAdapter<Entity> getPageAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list2;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText(getPageTitle());
        sfl.setOnStateClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        mAdapter = getPageAdapter();
        if (mAdapter instanceof RecyclerView.Adapter)
            recyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this,
                R.drawable.divider_common_horizontal)));
        onErrorClick(null);
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        mPage = 1;
        presenter.getList(mPage, false);
    }

    @Override
    public void onFirstLoading(boolean isRefresh) {
        if (!isRefresh) {
            sfl.loading();
        }
    }

    @Override
    public void onFirstLoadFinish(boolean isRefresh) {
        if (isRefresh) {
            refreshLayout.setRefreshing(false);
        } else {
            sfl.normal();
        }
    }

    @Override
    public void onFirstLoadEmpty() {
        sfl.empty();
    }

    @Override
    public void onFirstLoadError(int page, String error) {
        sfl.error();
    }

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore, Object lastData) {
        presenter.getList(mPage, false);
    }

    @Override
    public void onReloadClick(StateFrameLayout loadMore) {
        presenter.getList(mPage, false);
    }

    @Override
    public void onLoadMoreError(int page, String error) {
        if (mAdapter != null) {
            mAdapter.onError();
        }
    }

    protected int mPage = 1;

    @Override
    public void onRefresh() {
        mPage = 1;
        presenter.getList(mPage, true);
    }

    @Override
    public void setData(List<Entity> list, int page, boolean hasNextPage) {
        if (hasNextPage) {
            mPage++;
        }
        if (mAdapter != null) {
            mAdapter.setData(list, hasNextPage);
        }
    }

    @Override
    public void addData(List<Entity> list, int page, boolean hasNextPage) {
        if (hasNextPage) {
            mPage++;
        }
        if (mAdapter != null) {
            mAdapter.addData(list, hasNextPage);
        }
    }
}
