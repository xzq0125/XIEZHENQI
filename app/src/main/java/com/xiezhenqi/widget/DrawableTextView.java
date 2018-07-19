package com.xiezhenqi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.xiezhenqi.R;

/**
 * 图片文本控件
 * 支持给TextView设置左上右下图片的时候设置固定宽高
 * Created by xzq on 2018/7/18.
 */
public class DrawableTextView extends android.support.v7.widget.AppCompatTextView {

    public DrawableTextView(Context context) {
        super(context);
        init(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        final Drawable drawableLeft = a.getDrawable(R.styleable.DrawableTextView_drawableLeft);
        final Drawable drawableTop = a.getDrawable(R.styleable.DrawableTextView_drawableTop);
        final Drawable drawableRight = a.getDrawable(R.styleable.DrawableTextView_drawableRight);
        final Drawable drawableBottom = a.getDrawable(R.styleable.DrawableTextView_drawableBottom);
        final int drawableWidth = a.getDimensionPixelOffset(R.styleable.DrawableTextView_drawableWidth, 0);
        final int drawableHeight = a.getDimensionPixelOffset(R.styleable.DrawableTextView_drawableHeight, 0);
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        if (drawableLeft != null || drawableTop != null || drawableRight != null || drawableBottom != null)
            setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
        a.recycle();
    }

}

