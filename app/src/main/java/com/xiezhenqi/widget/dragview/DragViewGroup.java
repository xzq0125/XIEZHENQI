package com.xiezhenqi.widget.dragview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.xiezhenqi.utils.LogUtils;

/**
 * DragViewGroup
 * Created by Tse on 2017/11/30.
 */

public class DragViewGroup extends FrameLayout {

    private static final String TAG = "DragViewGroup";

    // 记录手指上次触摸的坐标
    private float mLastPointX;
    private float mLastPointY;

    // 记录被拖拽之前 child 的位置坐标
    private float mDragViewOrigX;
    private float mDragViewOrigY;

    //用于识别最小的滑动距离
    private int mSlop;
    // 用于标识正在被拖拽的 child，为 null 时表明没有 child 被拖拽
    private View mDragView;

    // 状态分别空闲、拖拽两种
    enum State {
        IDLE,
        DRAGGING
    }

    State mCurrentState;

    public DragViewGroup(@NonNull Context context) {
        super(context);
        init();
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSlop = ViewConfiguration.getWindowTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isPointOnViews(event)) {
                    //标记状态为拖拽，并记录上次触摸坐标
                    mCurrentState = State.DRAGGING;
                    mLastPointX = event.getX();
                    mLastPointY = event.getY();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (event.getX() - mLastPointX);
                int deltaY = (int) (event.getY() - mLastPointY);
                if (mCurrentState == State.DRAGGING && mDragView != null
                        && (Math.abs(deltaX) > mSlop || Math.abs(deltaY) > mSlop)) {
                    //如果符合条件则对被拖拽的 child 进行位置移动
                    ViewCompat.offsetLeftAndRight(mDragView, deltaX);
                    ViewCompat.offsetTopAndBottom(mDragView, deltaY);
                    mLastPointX = event.getX();
                    mLastPointY = event.getY();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mCurrentState == State.DRAGGING) {
                    // 标记状态为空闲，并将 mDragView 变量置为 null
                    if (mDragView != null) {
                        ValueAnimator animator = ValueAnimator.ofFloat(mDragView.getX(), mDragViewOrigX);
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                LogUtils.debug("WISH", mDragView.getX() + "");
                                mDragView.setX((Float) animation.getAnimatedValue());
                            }
                        });
                        ValueAnimator animator1 = ValueAnimator.ofFloat(mDragView.getY(), mDragViewOrigY);
                        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mDragView.setY((Float) animation.getAnimatedValue());
                            }
                        });
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.play(animator).with(animator1);
                        animatorSet.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mDragView = null;
                            }
                        });
                        animatorSet.start();
                    } else {
                        mDragView = null;
                    }
                    mCurrentState = State.IDLE;

                }
                break;
        }

        return true;
    }

    /**
     * 判断触摸的位置是否落在 child 身上
     */
    private boolean isPointOnViews(MotionEvent ev) {
        boolean result = false;
        Rect rect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            rect.set((int) view.getX(), (int) view.getY(), (int) view.getX() + (int) view.getWidth()
                    , (int) view.getY() + view.getHeight());

            if (rect.contains((int) ev.getX(), (int) ev.getY())) {
                //标记被拖拽的child
                mDragView = view;
                result = true;
                break;
            }
        }

        return result && mCurrentState != State.DRAGGING;
    }
}
