package com.xiezhenqi.base.list.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.MaterialLoadingProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiezhenqi.R;
import com.xiezhenqi.base.list.viewholder.BaseLoadMoreViewHolder;
import com.xiezhenqi.newmvp.IAdapter;

import java.util.ArrayList;
import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;


/**
 * BaseLoadMoreAdapter
 * Created by sean on 2016/9/26.
 */
@SuppressWarnings("all")
public abstract class BaseLoadMoreAdapter<Data, ViewHolder extends BaseLoadMoreViewHolder>
        extends RecyclerView.Adapter<ViewHolder>
        implements StateFrameLayout.OnStateClickListener, IAdapter<Data> {

    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;
    protected List<Data> mData;
    protected boolean hasNext = false;
    private StateFrameLayout sflLoadMore;
    protected  OnLoadMoreCallback loadMoreCallback;
    private LayoutInflater inflater;

    public void setLoadMoreCallback(OnLoadMoreCallback loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }

    public BaseLoadMoreAdapter() {
    }

    public BaseLoadMoreAdapter(OnLoadMoreCallback loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }

    private LayoutInflater getLayoutInflater(Context context) {
        if (inflater == null)
            inflater = LayoutInflater.from(context);
        return inflater;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent.getContext());
        View itemView;
        switch (viewType) {

            default:
            case TYPE_NORMAL:
                itemView = inflater.inflate(getItemLayoutId(), parent, false);
                if (itemClickListeners != null)
                    itemView.setOnClickListener(onClickListener);
                if (itemLongClickListeners != null)
                    itemView.setOnLongClickListener(onLongClickListener);
                return onCreateNormalViewHolder(itemView);

            case TYPE_FOOTER:
                itemView = inflater.inflate(getFooterLayoutId(), parent, false);
                sflLoadMore = (StateFrameLayout) itemView;
                sflLoadMore.setStateDrawables(new MaterialLoadingProgressDrawable(sflLoadMore),
                        ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_loading_error), null);
                sflLoadMore.setOnStateClickListener(this);
                return onCreateFooterViewHolder(itemView);
        }
    }

    public abstract ViewHolder onCreateNormalViewHolder(View itemView);

    ViewHolder onCreateFooterViewHolder(View itemView) {
        return (ViewHolder) new LoadMoreViewHolder(itemView);
    }

    class LoadMoreViewHolder extends BaseLoadMoreViewHolder {
        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(BaseLoadMoreViewHolder holder, int position) {
        //Do nothing
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (hasNext && position == getItemCount() - 1) {
            sflLoadMore.normal();
            sflLoadMore.loading();
            if (loadMoreCallback != null)
                loadMoreCallback.onAutoLoadMore(sflLoadMore, getDataAt(mData.size() - 1));
            return;
        }
        onConvert(holder, position, payloads);
    }

    public void onConvert(ViewHolder holder, int position, List<Object> payload) {
        onConvert(holder, position);
    }

    public abstract void onConvert(ViewHolder holder, int position);

    @LayoutRes
    protected abstract int getItemLayoutId();

    /**
     * 默认的Footer
     *
     * @return id
     */
    @LayoutRes
    protected int getFooterLayoutId() {
        return R.layout.item_common_loadmore;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size() + (hasNext ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return (hasNext && position == getItemCount() - 1) ? TYPE_FOOTER : TYPE_NORMAL;
    }

    public boolean isEmpty() {
        return mData == null || mData.isEmpty();
    }

    public Data getDataAt(int index) {
        if (!checkIndex(index))
            return null;
        return mData.get(index);
    }

    public boolean checkIndex(int index) {
        return mData != null && index >= 0 && index < mData.size();
    }

    public int indexOf(Data data) {
        if (mData == null)
            return -1;
        return mData.indexOf(data);
    }

    public void setData(List<Data> data) {
        setData(data, false);
    }

    public void setData(List<Data> data, boolean hasNext) {
        this.mData = data;
        this.hasNext = hasNext;
        notifyDataSetChanged();
    }

    public void addData(List<Data> data, boolean hasNext) {
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
    public void onError() {
        sflLoadMore.error();
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        if (layout != null)
            layout.loading();
        if (loadMoreCallback != null)
            loadMoreCallback.onReloadClick(layout);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onItemClick(v);
        }
    };

    protected void onItemClick(View v) {
        if (itemClickListeners != null && v.getTag() != null) {
            try {
                @SuppressWarnings("unchecked") Data data = (Data) v.getTag();
                int pos = mData.indexOf(data);
                for (OnItemClickListener listener : itemClickListeners)
                    listener.onItemClick(data, pos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private final View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            return onItemLongClick(v);
        }
    };

    protected boolean onItemLongClick(View v) {
        if (itemLongClickListeners != null && v.getTag() != null) {
            try {
                @SuppressWarnings("unchecked") Data data = (Data) v.getTag();
                int pos = mData.indexOf(data);
                for (OnItemLongClickListener listener : itemLongClickListeners)
                    listener.onItemLongClick(data, pos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * item事件监听
     *
     * @param <Data>
     */
    public interface OnLoadMoreCallback<Data> {

        /**
         * 自动加载更多
         *
         * @param loadMore StateFrameLayout
         * @param lastData 数据实体
         */
        void onAutoLoadMore(StateFrameLayout loadMore, Data lastData);

        /**
         * 重新加载更多
         *
         * @param loadMore StateFrameLayout
         */
        void onReloadClick(StateFrameLayout loadMore);

    }

    private List<OnItemClickListener> itemClickListeners;

    public void addOnItemClickListener(OnItemClickListener listener) {
        if (listener == null)
            return;

        if (itemClickListeners == null)
            itemClickListeners = new ArrayList<>();

        if (itemClickListeners.contains(listener))
            return;

        itemClickListeners.add(listener);
    }

    public interface OnItemClickListener {

        /**
         * item点击
         *
         * @param dto      数据实体
         * @param position 位置
         */
        void onItemClick(Object dto, int position);
    }

    private List<OnItemLongClickListener> itemLongClickListeners;

    public void addOnItemLongClickListener(OnItemLongClickListener listener) {

        if (listener == null)
            return;

        if (itemLongClickListeners == null)
            itemLongClickListeners = new ArrayList<>();

        if (itemLongClickListeners.contains(listener))
            return;

        itemLongClickListeners.add(listener);
    }

    public interface OnItemLongClickListener {

        /**
         * item长按
         *
         * @param dto      数据实体
         * @param position 位置
         */
        void onItemLongClick(Object dto, int position);
    }

    @Override
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }
}
