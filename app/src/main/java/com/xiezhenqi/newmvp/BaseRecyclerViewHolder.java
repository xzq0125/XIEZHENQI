package com.xiezhenqi.newmvp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * BaseRecyclerViewHolder
 *
 * @author xzq
 */

public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    protected T data;
    protected int position;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    void bindData(T data, int position) {
        this.data = data;
        this.position = position;
    }

    public abstract void setData(T data);
}
