package com.xiezhenqi.newmvp.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BasePresenterActivity;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;
import com.xiezhenqi.newmvp.ShopBean;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;
import butterknife.BindView;

public class RXMVPActivity extends BasePresenterActivity<RXMVPPresenter> implements RXMVPContract.View,
        BaseLoadMoreAdapter.OnLoadMoreCallback, SwipeRefreshLayout.OnRefreshListener {

    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.sfl)
    StateFrameLayout sfl;

    public static void start(Context context) {
        Intent starter = new Intent(context, RXMVPActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rxmvp;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(R.id.tool_bar);
        title.setText("RXMVPActivity");
        getSfl(sfl);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this,
                R.drawable.divider_common_horizontal)));
        recyclerView.setAdapter(myAdapter);

        presenter.getData(page);
    }

    @Override
    protected RXMVPPresenter createPresenter() {
        return new RXMVPPresenter(this);
    }

    private final MyAdapter myAdapter = new MyAdapter(this);

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore, Object lastData) {
        presenter.getData(++page);
    }

    @Override
    public void onReloadClick(StateFrameLayout loadMore) {
        presenter.getData(page);
    }

    @Override
    protected void loadFirstPage() {
        presenter.getData(page);
    }

    @Override
    public void setData(List<ShopBean.ListBean> list, boolean hasNextPage) {
        myAdapter.setData(list, hasNextPage);
    }

    @Override
    public void addData(List<ShopBean.ListBean> list, boolean hasNextPage) {
        myAdapter.addData(list, hasNextPage);
    }

    @Override
    public void onRefresh() {
        onErrorClick(null);
    }

    @Override
    public void onHideLoading() {
        super.onHideLoading();
        swipeRefreshLayout.setRefreshing(false);
    }

    private final class MyAdapter extends BaseLoadMoreAdapter<ShopBean.ListBean, MyViewHolder> {

        public MyAdapter(OnLoadMoreCallback loadMoreCallback) {
            super(loadMoreCallback);
        }

        @Override
        public MyViewHolder onCreateNormalViewHolder(View itemView) {
            return new MyViewHolder(itemView);
        }

        @Override
        public void onConvert(MyViewHolder holder, int position) {
            holder.setData(getDataAt(position));
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_song_list;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }
    }

    private final class MyViewHolder extends BaseLoadMoreViewHolder {


        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }

        public void setData(ShopBean.ListBean data) {
            tv.setText(data.name);
        }
    }

}
