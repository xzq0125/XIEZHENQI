package com.xiezhenqi.widget.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    @IntDef({VERTICAL_LIST, HORIZONTAL_LIST})
    public @interface Orientation {
    }

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    private Drawable mDivider;
    private Rect rectDrawablePadding = new Rect();

    private int mOrientation;

    private boolean enabled = true;
    private boolean isLastItemNoDivider = false;

    /**
     * Construct a decoration with system drawable,the default orientation is {@link #VERTICAL_LIST}
     *
     * @param context Context
     */
    public DividerItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(VERTICAL_LIST);
    }

    /**
     * Construct a decoration with system drawable and must be specify the orientation
     *
     * @param orientation the orientation must be one of the {@link #VERTICAL_LIST} or {@link #HORIZONTAL_LIST}.
     * @param context     Context
     */
    public DividerItemDecoration(@Orientation int orientation, Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    /**
     * Construct a decoration with a specified resId,the default orientation is {@link #VERTICAL_LIST}
     *
     * @param context Context
     * @param resId   id of divider
     */
    public DividerItemDecoration(Context context, @DrawableRes int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
        setOrientation(VERTICAL_LIST);
    }

    /**
     * Construct a decoration with a specified drawable,the default orientation is {@link #VERTICAL_LIST}
     *
     * @param divider
     */
    public DividerItemDecoration(Drawable divider) {
        mDivider = divider;
        setOrientation(VERTICAL_LIST);
    }

    /**
     * Construct a decoration with a specified resId and must be specify the orientation,
     *
     * @param context     Context
     * @param resId       id of divider
     * @param orientation the orientation must be one of the {@link #VERTICAL_LIST} or {@link #HORIZONTAL_LIST}.
     */
    public DividerItemDecoration(Context context, @DrawableRes int resId, int orientation) {
        mDivider = ContextCompat.getDrawable(context, resId);
        setOrientation(orientation);
    }

    /**
     * Construct a decoration with a specified drawable and must be specify the orientation
     *
     * @param divider     divider
     * @param orientation the orientation must be one of the {@link #VERTICAL_LIST} or {@link #HORIZONTAL_LIST}.
     */
    public DividerItemDecoration(Drawable divider, int orientation) {
        mDivider = divider;
        setOrientation(orientation);
    }


    public void setOrientation(@Orientation int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (enabled) {
            if (mOrientation == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        mDivider.getPadding(rectDrawablePadding);
        final int left = parent.getPaddingLeft() + rectDrawablePadding.left;
        final int right = parent.getWidth() - parent.getPaddingRight() - rectDrawablePadding.right;

        final int childCount = isLastItemNoDivider ? parent.getChildCount() - 1 : parent.getChildCount();

        for (int i = 0; i < childCount; i++) {

            if (isItemNoDivider(i)) continue;

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        mDivider.getPadding(rectDrawablePadding);
        final int top = parent.getPaddingTop() + rectDrawablePadding.top;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - rectDrawablePadding.bottom;

        final int childCount = isLastItemNoDivider ? parent.getChildCount() - 1 : parent.getChildCount();

        for (int i = 0; i < childCount; i++) {

            if (isItemNoDivider(i)) continue;

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (enabled) {
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the enabled state of this decoration.
     * After set, you should call {@link RecyclerView#invalidateItemDecorations()} to alter.
     *
     * @param enabled True if this decoration is enabled, false otherwise.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Check whether the item is need a divider
     *
     * @param position The position of item
     * @return True if this item need a divider, false otherwise.
     */
    private boolean isItemNoDivider(final int position) {

        if (posList == null) return false;

        for (int pos : posList) {
            if (pos == position) {
                return true;
            }
        }

        return false;
    }

    /**
     * A list of unneeded positon
     */
    private List<Integer> posList;

    /**
     * Set specified item no divider
     *
     * @param positions positions
     */
    public void setItemNoDivider(int... positions) {
        if (posList == null)
            posList = new ArrayList<>();

        for (int pos : positions) {
            posList.add(pos);
        }
    }

    /**
     * Set last item no divider
     *
     * @param isLastItemNoDivider True set the last item no divider, false otherwise.
     */
    public void setLastItemNoDivider(boolean isLastItemNoDivider) {
        this.isLastItemNoDivider = isLastItemNoDivider;
    }

}