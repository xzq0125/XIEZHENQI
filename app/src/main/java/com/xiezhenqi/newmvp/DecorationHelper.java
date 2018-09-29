package com.xiezhenqi.newmvp;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.xiezhenqi.R;
import com.xiezhenqi.XZQApplication;

/**
 * DecorationHelper
 * Created by xzq on 2018/9/3.
 */

public class DecorationHelper {

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private Context context;
        private Drawable drawable;
        private Rect rect = new Rect();
        private int orientation = ItemDivider.VERTICAL;
        private int size = 2;
        private boolean lastItemNoDivider = false;

        Builder(Context context) {
            this.context = context;
        }

        public Builder drawable(@Nullable Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        public Builder drawableRes(@DrawableRes int resId) {
            this.drawable = ContextCompat.getDrawable(context, resId);
            return this;
        }

        public Builder colorRes(@ColorRes int resId) {
            this.drawable = new ColorDrawable(ContextCompat.getColor(context, resId));
            return this;
        }

        public Builder colorHex(@ColorInt int color) {
            this.drawable = new ColorDrawable(color);
            return this;
        }

        public Builder padding(Rect rect) {
            this.rect = rect;
            return this;
        }

        public Builder padding(int dpVal) {
            int padding = dp2px(context, dpVal);
            this.rect.set(padding, padding, padding, padding);
            return this;
        }

        public Builder orientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder pxOfSize(int pxVal) {
            this.size = pxVal;
            return this;
        }

        public Builder dpOfSize(int dpVal) {
            this.size = dp2px(context, dpVal);
            return this;
        }

        public Builder lastItemNoDivider(boolean lastItemNoDivider) {
            this.lastItemNoDivider = lastItemNoDivider;
            return this;
        }

        public RecyclerView.ItemDecoration build() {
            return DecorationHelper.create(this);
        }
    }

    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    private static RecyclerView.ItemDecoration create(Builder builder) {
        ItemDivider divider = new ItemDivider(builder.context, builder.orientation);
        divider.setSize(builder.size);
        divider.setPadding(builder.rect);
        divider.setLastItemNoDivider(builder.lastItemNoDivider);
        if (builder.drawable != null) {
            divider.setDrawable(builder.drawable);
        }
        return divider;
    }

    private static final int DEFAULT_COLOR = R.color.color_divider;

    public static RecyclerView.ItemDecoration VERTICAL
            = create(with(XZQApplication.getContext()).colorRes(DEFAULT_COLOR).orientation(ItemDivider.VERTICAL));

    public static RecyclerView.ItemDecoration HORIZONTAL
            = create(with(XZQApplication.getContext()).colorRes(DEFAULT_COLOR).orientation(ItemDivider.HORIZONTAL));

    public static RecyclerView.ItemDecoration VERTICAL_LAST_NO_DIVIDER
            = create(with(XZQApplication.getContext()).colorRes(DEFAULT_COLOR).lastItemNoDivider(true));

}
