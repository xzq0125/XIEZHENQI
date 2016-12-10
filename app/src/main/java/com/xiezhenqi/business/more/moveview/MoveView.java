package com.xiezhenqi.business.more.moveview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * MoveView
 * Created by sean on 2016/12/6.
 */

public class MoveView extends FrameLayout {

    private static final int WIDTH = 40;
    private final int mTouchSlop;
    private int lastX;
    private int lastY;

    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (onInterceptTouchEvent)
            return true;
        return super.onInterceptTouchEvent(ev);
    }

    private boolean onInterceptTouchEvent = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                lastX = rawX;
                lastY = rawY;
                if (offsetX > mTouchSlop || offsetY > mTouchSlop)
                    onInterceptTouchEvent = true;
                else {
                    onInterceptTouchEvent = false;
                }
                // 同时对left和right进行偏移
                offsetLeftAndRight(offsetX);
//                // 同时对top和bottom进行偏移
                offsetTopAndBottom(offsetY);
                break;

            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
//                ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "translationX", getRight(), 0);
//                ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "translationY", getBottom(), 0);
//                AnimatorSet set = new AnimatorSet();
//                set.playTogether(animator1, animator2);
//                set.start();
                onInterceptTouchEvent = true;
                break;
        }
        return true;
    }
}