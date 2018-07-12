package com.xiezhenqi.business.more.pulldownrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.activitys.BaseActivity;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;
import com.xiezhenqi.widget.menurecyclerview.ContextMenuRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PullDownRefreshActivity extends BaseActivity implements BaseLoadMoreAdapter.OnLoadMoreCallback {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pull_down_refresh;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);

        // init SuperSwipeRefreshLayout
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setHeaderViewBackgroundColor(0xff888888);
        swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        swipeRefreshLayout.setFooterView(createFooterView());
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        // pull distance
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

        swipeRefreshLayout
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                footerImageView.setVisibility(View.VISIBLE);
                                footerProgressBar.setVisibility(View.GONE);
                                swipeRefreshLayout.setLoadMore(false);
                            }
                        }, 5000);
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {
                        // TODO Auto-generated method stub

                    }

                });
        initDatas();
    }

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SuperSwipeRefreshLayout swipeRefreshLayout;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipeRefreshLayout.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = (ProgressBar) footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = (ImageView) footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = (TextView) footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.ic_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipeRefreshLayout.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (TextView) headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = (ImageView) headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.ic_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    private void initDatas() {
        List<String> data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.singer_list)));
        myAdapter.setData(data);
    }

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore, Object lastData) {

    }

    @Override
    public void onReloadClick(StateFrameLayout loadMore) {

    }
    class MyAdapter extends BaseLoadMoreAdapter<String, MyViewHolder> {

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
            return R.layout.item_drag_list;
        }

        @Override
        public void onViewRecycled(MyViewHolder holder) {
            super.onViewRecycled(holder);
            holder.itemView.setOnCreateContextMenuListener(null);
        }

        public void addData(int position, String newData) {
            int toPos = position + 1;
            mData.add(toPos, newData);
            notifyItemInserted(toPos);
        }

        public boolean isDataExist(String text) {
            for (String s : mData)
                if (TextUtils.equals(s, text))
                    return true;
            return false;
        }

        public void remove(int position) {
            mData.remove(position);
            notifyItemRemoved(position);
        }

        public void update(int position, String text) {
            mData.remove(position);
            mData.add(position, text);
            notifyItemChanged(position);
        }
    }

    class MyViewHolder extends BaseLoadMoreViewHolder {

        final ContextMenuRecyclerView.RecyclerItem item = new ContextMenuRecyclerView.RecyclerItem();

        @BindView(R.id.tv_item)
        TextView tvItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvItem.setCompoundDrawables(null, null, null, null);
            itemView.setLongClickable(true);
        }

        public void setData(String data) {
            itemView.setTag(itemView.getId(), item.setItem(data));
            itemView.setTag(data);
            tvItem.setText(data);
        }
    }
}
