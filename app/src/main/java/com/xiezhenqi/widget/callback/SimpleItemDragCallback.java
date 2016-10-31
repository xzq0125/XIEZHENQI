package com.xiezhenqi.widget.callback;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的纵向Item拖拽回调，另外需要自己的Adapter实现{@link OnItemMovedAdapter}
 * Created by sean on 2016/8/29.
 */
public class SimpleItemDragCallback extends ItemTouchHelper.Callback {

    private static final int BG_COLOR_SELECTED = 0xffe0e0e0;
    private static final int BG_COLOR_NORMAL = 0;
    private int fromPosition = -1;
    private final OnItemMovedAdapter mAdapter;
    private boolean notifyDataAfterClearView = false;
    private boolean needVibrator = true;

    public SimpleItemDragCallback(OnItemMovedAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        if (mAdapter != null)
            mAdapter.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            fromPosition = viewHolder.getAdapterPosition();
            viewHolder.itemView.setBackgroundColor(BG_COLOR_SELECTED);
            if (needVibrator)
                ((Vibrator) viewHolder.itemView.getContext().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(70);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(BG_COLOR_NORMAL);

        int toPosition = viewHolder.getAdapterPosition();

        if (listeners != null && toPosition >= 0 && fromPosition >= 0 && toPosition != fromPosition)
            for (OnPositionChangeListener listener : listeners)
                listener.onPositionChange(fromPosition, toPosition);

        if (notifyDataAfterClearView && !recyclerView.isComputingLayout() && recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        if (mAdapter != null)
            mAdapter.onStop();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    public void setNotifyDataAfterClearView(boolean notifyDataAfterClearView) {
        this.notifyDataAfterClearView = notifyDataAfterClearView;
    }

    public void setNeedVibrator(boolean needVibrator) {
        this.needVibrator = needVibrator;
    }

    private List<OnPositionChangeListener> listeners;

    /**
     * 添加监听器
     *
     * @param listener 监听器
     */
    public void addOnPositionChangeListener(OnPositionChangeListener listener) {
        if (listeners == null)
            listeners = new ArrayList<>();
        if (listeners.contains(listener))
            return;
        listeners.add(listener);
    }

    /**
     * 移除监听器
     *
     * @param listener 监听器
     */
    public void removeOnPositionChangeListener(OnPositionChangeListener listener) {
        if (listeners != null)
            listeners.remove(listener);
    }

    /**
     * 清空监听器
     */
    public void clearOnPositionChangeListener() {
        if (listeners != null)
            listeners.clear();
    }

    public interface OnPositionChangeListener {
        /**
         * 回调position
         *
         * @param fromPosition fromPosition
         * @param toPosition   toPosition
         */
        void onPositionChange(int fromPosition, int toPosition);
    }

    public interface OnItemMovedAdapter {

        void onItemMoved(int fromPosition, int toPosition);

        void onStop();
    }

}
