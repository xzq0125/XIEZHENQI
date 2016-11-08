package com.xiezhenqi.business.more.drag;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.activity.BaseListActivity;
import com.xiezhenqi.base.list.adapter.BaseLoadMoreAdapter;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;
import com.xiezhenqi.utils.ToastUtils;
import com.xiezhenqi.widget.callback.SimpleItemDragCallback;
import com.xiezhenqi.widget.divider.DividerItemDecoration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DragActivity extends BaseListActivity implements BaseLoadMoreAdapter.OnItemLongClickListener, BaseLoadMoreAdapter.OnItemClickListener {

    private final MyAdapter mAdapter = new MyAdapter(null);
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView recyclerView;
    private ViewGroup container;

    @Override
    protected String getMyTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected void initRecyclerView(RecyclerView recyclerView, ViewGroup container) {
        this.recyclerView = recyclerView;
        this.container = container;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.addOnItemClickListener(this);
        mAdapter.addOnItemLongClickListener(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.divider_common_horizontal)));
        mItemTouchHelper = new ItemTouchHelper(new SimpleItemDragCallback(mAdapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void loadData(boolean loadFirstPage) {
        List<String> list =
                Arrays.asList(getResources().getStringArray(R.array.singer_list));
        showNormal();
        mAdapter.setData(list);
    }

    @Override
    public void onItemLongClick(Object dto, int position) {
        container.setEnabled(false);
        mItemTouchHelper.startDrag(recyclerView.findViewHolderForAdapterPosition(position));
    }

    @Override
    public void onItemClick(Object dto, int position) {
        ToastUtils.showToast(this, "position = " + position);
    }

    class MyAdapter extends BaseLoadMoreAdapter<String, MyViewHolder> implements SimpleItemDragCallback.OnItemMovedAdapter {

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
        public void onItemMoved(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mData, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mData, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onStop() {
            container.setEnabled(true);
        }
    }

    class MyViewHolder extends BaseLoadMoreViewHolder {

        @Bind(R.id.tv_item)
        TextView tvItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(String data) {
            itemView.setTag(data);
            tvItem.setText(data);
        }
    }

}
