package com.xiezhenqi.business.more.selectcity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * StickyItemDecoration
 * Created by Wesley on 2018/1/5.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter instanceof CityAdapter) {
            if (((CityAdapter) adapter).isFirstOfGroup(index)) {
                outRect.top = 60;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // 得到item真实的left和right（减去parent的padding）
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i != parent.getChildCount(); i++) {
            // 直接获得的child只有当前显示的，所以就算i是0的index也只是当前第一个，而不是所有第一个
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter instanceof CityAdapter) {
                if (((CityAdapter) adapter).isFirstOfGroup(index)) {
                    // 每组第一个item都留有空间来绘制
                    int top = child.getTop() - 60;
                    int bottom = child.getTop();
                    // 绘制背景色
                    Paint paint = new Paint();
                    paint.setColor(Color.YELLOW);
                    c.drawRect(left, top, right, bottom, paint);
                    // 绘制组名
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(60);
                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setAntiAlias(true);
                    c.drawText(((CityAdapter) adapter).getGroupName(index), left, bottom, paint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
