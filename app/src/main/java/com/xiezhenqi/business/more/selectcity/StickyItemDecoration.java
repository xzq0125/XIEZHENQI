package com.xiezhenqi.business.more.selectcity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import com.xiezhenqi.R;

/**
 * StickyItemDecoration
 * Created by Tse on 2018/1/5.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private Paint.FontMetrics fontMetrics;

    public StickyItemDecoration(Context context) {
        Resources res = context.getResources();
        paint = new Paint();
        paint.setColor(res.getColor(R.color.common_background));

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(res.getDimensionPixelSize(R.dimen.selected_bar_text_size));
        textPaint.setColor(Color.BLACK);
        textPaint.getFontMetrics(fontMetrics);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap = res.getDimensionPixelSize(R.dimen.selected_bar_height);

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        CityAdapter adapter = (CityAdapter) parent.getAdapter();
        long groupId = adapter.getGroupId(pos);
        if (groupId < 0) return;
        if (adapter.isFirstOfGroup(pos)) {
            outRect.top = topGap - 1;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = textPaint.getTextSize() + fontMetrics.descent;
        long preGroupId, groupId = -1;
        CityAdapter adapter = (CityAdapter) parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = adapter.getGroupId(position);
            if (groupId < 0 || groupId == preGroupId) continue;

            String textLine = adapter.getGroupName(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                long nextGroupId = adapter.getGroupId(position + 1);
                if (nextGroupId != groupId && viewBottom < textY) {//组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
            c.drawRect(left, textY - topGap, right, textY, paint);
            c.drawText(textLine, left + topGap, textY - (topGap - lineHeight) / 2 - 5, textPaint);
        }

    }

}
