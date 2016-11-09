package com.xiezhenqi.widget.menurecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

/**
 * 上下文RecyclerView
 * Created by Tse on 2016/9/11.
 */
public class ContextMenuRecyclerView extends RecyclerView {

    private RecyclerContextMenuInfo mContextMenuInfo = new RecyclerContextMenuInfo();

    public ContextMenuRecyclerView(Context context) {
        super(context);
    }

    public ContextMenuRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContextMenuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        if (originalView.getTag(originalView.getId()) instanceof RecyclerItem) {
            mContextMenuInfo.mRecycleItem = (RecyclerItem) originalView.getTag(originalView.getId());
        }
        return super.showContextMenuForChild(originalView);
    }

    public static class RecyclerItem {

        private Object item = null;

        public RecyclerItem() {
        }

        public RecyclerItem(Object item) {
            this.item = item;
        }

        public Object getItem() {
            return item;
        }

        public RecyclerItem setItem(Object item) {
            this.item = item;
            return this;
        }
    }

    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public RecyclerItem mRecycleItem;
    }
}


