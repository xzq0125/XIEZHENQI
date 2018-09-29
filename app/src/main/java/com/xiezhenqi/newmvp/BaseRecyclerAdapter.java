package com.xiezhenqi.newmvp;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;

/**
 * 列表适配器基类
 *
 * @author xzq
 */

@SuppressWarnings("all")
public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerViewHolder<T>>
        extends RecyclerView.Adapter<VH>
        implements IAdapter<T>,
        StateFrameLayout.OnStateClickListener {

    public static final int TYPE_FOOTER = 2;
    protected List<T> mData = new ArrayList<>();
    private LayoutInflater mInflater = null;
    protected boolean hasNext = false;//是否有下一页
    private StateFrameLayout sflLoadMore;
    protected OnLoadMoreCallback loadMoreCallback;

    public void setLoadMoreCallback(OnLoadMoreCallback loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }

    public BaseRecyclerAdapter() {
    }

    public BaseRecyclerAdapter(OnLoadMoreCallback loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        switch (viewType) {
            default: {
                View itemView = null;
                final int layoutId = getItemLayoutId(viewType);
                if (layoutId > 0) {
                    itemView = mInflater.inflate(layoutId, parent, false);
                }
                return onCreateViewHolder(parent, itemView, viewType);
            }

            case TYPE_FOOTER:
                View itemView = mInflater.inflate(R.layout.item_common_loadmore, parent, false);
                sflLoadMore = (StateFrameLayout) itemView;
                sflLoadMore.setLoadingDrawable(new MyProgressDrawable());
                sflLoadMore.setOnStateClickListener(this);
                return onCreateFooterViewHolder(itemView);
        }
    }

    VH onCreateFooterViewHolder(View itemView) {
        return (VH) new LoadMoreViewHolder(itemView);
    }

    class LoadMoreViewHolder extends BaseRecyclerViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(Object data) {
            //empty
        }
    }

    @NonNull
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, @Nullable View itemView, int viewType);

    @LayoutRes
    protected abstract int getItemLayoutId(int viewType);

    public abstract void onConvert(@NonNull VH holder, T data, int position, @NonNull List<Object> payload);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        //覆盖了下面的方法，因此不会执行到这里
        onBindViewHolder(holder, position, Collections.EMPTY_LIST);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        if (hasNext && position == getItemCount() - 1) {
            sflLoadMore.loading();
            if (loadMoreCallback != null) {
                loadMoreCallback.onAutoLoadMore(sflLoadMore);
            }
            return;
        }
        T data = getDataAt(position);
        holder.bindData(data, position);
        onConvert(holder, data, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size() + (hasNext ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return (hasNext && position == getItemCount() - 1) ? TYPE_FOOTER
                : super.getItemViewType(position);
    }

    public List<T> getData() {
        return mData;
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    public T getDataAt(int index) {
        if (!checkIndex(index)) {
            return null;
        }
        return mData.get(index);
    }

    public T getLastItem() {
        if (mData.isEmpty()) {
            return null;
        }
        return mData.get(mData.size() - 1);
    }

    public boolean checkIndex(int index) {
        return index >= 0 && index < mData.size();
    }

    public int indexOf(T item) {
        return mData.indexOf(item);
    }

    public T removeItem(T item) {
        return removeItem(indexOf(item));
    }

    public T removeItem(int index) {
        T item = null;
        if (checkIndex(index)) {
            item = mData.remove(index);
        }
        return item;
    }

    @Override
    public void setData(List<T> data) {
        setData(data, false);
    }

    @Override
    public void setData(List<T> data, boolean hasNext) {
        this.mData = data;
        this.hasNext = hasNext;
        notifyDataSetChanged();
    }

    @Override
    public void addData(List<T> data, boolean hasNext) {
        if (data != null) {
            if (mData != null) {
                this.hasNext = hasNext;
                int position = getItemCount() - 1;
                if (mData.addAll(data)) {
                    notifyItemRangeChanged(position, hasNext ? data.size() : data.size() - 1);
                } else {
                    if (hasNext) {
                        notifyDataSetChanged();
                    } else {
                        notifyItemRemoved(position + 1);
                    }
                }
            } else {
                setData(data, hasNext);
            }
        }
    }

    @Override
    public void clear() {
        hasNext = false;
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onError() {
        sflLoadMore.error();
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        if (layout != null) {
            layout.loading();
        }
        if (loadMoreCallback != null) {
            loadMoreCallback.onReloadMore(layout);
        }
    }

    /**
     * item事件监听
     *
     * @param <Data>
     */
    public interface OnLoadMoreCallback {

        /**
         * 自动加载更多
         *
         * @param loadMore StateFrameLayout
         */
        void onAutoLoadMore(StateFrameLayout loadMore);

        /**
         * 重新加载更多
         *
         * @param loadMore StateFrameLayout
         */
        void onReloadMore(StateFrameLayout loadMore);

    }
}
