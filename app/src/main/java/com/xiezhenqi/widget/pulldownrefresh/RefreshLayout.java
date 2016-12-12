package com.xiezhenqi.widget.pulldownrefresh;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;


/**
 * 下拉刷新控件
 * Created by AItsuki on 2016/6/13.
 */
@SuppressWarnings("all")
public class RefreshLayout extends ViewGroup {

    private static final String TAG = RefreshLayout.class.getSimpleName();
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;

    // scroller duration
    private static final int SCROLL_TO_TOP_DURATION = 500;
    private static final int SCROLL_TO_REFRESH_DURATION = 2000;
    private static final long SHOW_COMPLETED_TIME = 500;
    private final Interpolator interpolator = new OvershootInterpolator();

    private View refreshHeader;
    private View target;
    private int currentTargetOffsetTop; // target/header偏移距离
    private int lastTargetOffsetTop;

    private boolean hasMeasureHeader;   // 是否已经计算头部高度
    private final int touchSlop;
    private int headerHeight;       // header高度
    private int totalDragDistance;  // 需要下拉这个距离才进入松手刷新状态，默认和header高度一致
    private int maxDragDistance;
    private int activePointerId;
    private boolean isTouch;
    private boolean hasSendCancelEvent;
    private float lastMotionY;
    private float initDownY;
    private static final int START_POSITION = 0;
    private MotionEvent lastEvent;
    private boolean mIsBeginDragged;
    private final AutoScroll autoScroll;
    private State state = State.RESET;
    private boolean isAutoRefresh;
    private List<OnRefreshListener> listeners;
    private boolean isIgnoreTouch;
    private float slopRate = 1;


    // 刷新成功，显示500ms成功状态再滚动回顶部
    private final Runnable delayToScrollTopRunnable = new Runnable() {
        @Override
        public void run() {
            autoScroll.scrollTo(START_POSITION, SCROLL_TO_TOP_DURATION);
        }
    };

    private final Runnable autoRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            // 标记当前是自动刷新状态，finishScroll调用时需要判断
            // 在actionDown事件中重新标记为false
            isAutoRefresh = true;
            changeState(State.PULL);
            autoScroll.scrollTo(totalDragDistance, SCROLL_TO_REFRESH_DURATION);
        }
    };


    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        autoScroll = new AutoScroll();

        // 添加默认的头部，先简单的用一个ImageView代替头部
        ImageView imageView = new ImageView(context);
        setRefreshHeader(imageView);
    }

    /**
     * 设置自定义header
     */
    public void setRefreshHeader(View view) {
        if (view != null && view != refreshHeader) {
            removeView(refreshHeader);

            // 为header添加默认的layoutParams
            LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
            }
            refreshHeader = view;
            addView(refreshHeader);
        }
    }

    public void addOnRefreshListener(OnRefreshListener refreshListener) {
        if (refreshListener == null)
            return;

        if (listeners == null)
            listeners = new ArrayList<>();

        if (listeners.contains(refreshListener))
            return;

        listeners.add(refreshListener);
    }

    public void refreshComplete() {
        //调用refreshComplete的时候延时500ms才设置State.COMPLETE
        postDelayed(mDelayToSetComplete, 500);
    }

    private final Runnable mDelayToSetComplete = new Runnable() {
        @Override
        public void run() {
            changeState(State.COMPLETE);
            // if refresh completed and the target at top, change state to reset.
            if (currentTargetOffsetTop == START_POSITION) {
                changeState(State.RESET);
            } else {
                // waiting for a time to show refreshView completed state.
                // at next touch event, remove this runnable
                if (isIgnoreTouch || !isTouch) {
                    postDelayed(delayToScrollTopRunnable, SHOW_COMPLETED_TIME);
                }
            }
        }
    };

    public void autoRefresh() {
        autoRefresh(500);
    }

    /**
     * 在onCreate中调用autoRefresh，此时View可能还没有初始化好，需要延长一段时间执行。
     *
     * @param duration 延时执行的毫秒值
     */
    public void autoRefresh(long duration) {
        if (state != State.RESET) {
            return;
        }
        postDelayed(autoRefreshRunnable, duration);
    }

    /**
     * 设置回滚到顶部的时候是否忽略手指触摸
     *
     * @param isIgnoreTouch true表示忽略
     */
    public void setIsIgnoreTouch(boolean isIgnoreTouch) {
        this.isIgnoreTouch = isIgnoreTouch;
    }

    /**
     * 设置溢出比率
     *
     * @param slopRate 溢出比率,默认值是1
     */
    public void setSlopRate(float slopRate) {
        this.slopRate = slopRate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (target == null) {
            ensureTarget();
        }

        if (target == null) {
            return;
        }

        // ----- measure target -----
        // target占满整屏
        target.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));

        // ----- measure refreshView-----
        measureChild(refreshHeader, widthMeasureSpec, heightMeasureSpec);
        if (!hasMeasureHeader) { // 防止header重复测量
            hasMeasureHeader = true;
            headerHeight = refreshHeader.getMeasuredHeight(); // header高度
            totalDragDistance = headerHeight;   // 需要pull这个距离才进入松手刷新状态
            if (maxDragDistance == 0) {  // 默认最大下拉距离为控件高度的五分之四
                maxDragDistance = totalDragDistance * 3;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }

        if (target == null) {
            ensureTarget();
        }
        if (target == null) {
            return;
        }

        // target铺满屏幕
        final View child = target;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop() + currentTargetOffsetTop;
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

        // header放到target的上方，水平居中
        int refreshViewWidth = refreshHeader.getMeasuredWidth();
        refreshHeader.layout((width / 2 - refreshViewWidth / 2),
                -headerHeight + currentTargetOffsetTop,
                (width / 2 + refreshViewWidth / 2),
                currentTargetOffsetTop);
    }

    /**
     * 将第一个Child作为target
     */
    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (target == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(refreshHeader)) {
                    target = child;
                    break;
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || target == null) {
            return super.dispatchTouchEvent(ev);
        }

        final int actionMasked = ev.getActionMasked(); // support Multi-touch
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                isAutoRefresh = false;
                isTouch = true;
                hasSendCancelEvent = false;
                mIsBeginDragged = false;
                lastTargetOffsetTop = currentTargetOffsetTop;
                currentTargetOffsetTop = target.getTop();
                initDownY = lastMotionY = ev.getY(0);
                autoScroll.stop();
                if (!isIgnoreTouch)
                    removeCallbacks(delayToScrollTopRunnable);
                removeCallbacks(autoRefreshRunnable);
                super.dispatchTouchEvent(ev);
                return true;    // return true，否则可能接受不到move和up事件

            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_POINTER) {
                    Log.e(TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return super.dispatchTouchEvent(ev);
                }
                lastEvent = ev;
                float y = ev.getY(ev.findPointerIndex(activePointerId));
                float yDiff = y - lastMotionY;
                float offsetY = yDiff * DRAG_RATE;
                lastMotionY = y;

                if (!mIsBeginDragged && Math.abs(y - initDownY) > touchSlop * slopRate) {
                    mIsBeginDragged = true;
                }
                if (mIsBeginDragged) {
                    boolean moveDown = offsetY > 0; // ↓
                    boolean canMoveDown = !canChildScrollUp();
                    boolean moveUp = !moveDown;     // ↑
                    boolean canMoveUp = currentTargetOffsetTop > START_POSITION;

                    if (moveUp && (state == State.LOADING || state == State.COMPLETE))
                        return super.dispatchTouchEvent(ev);


                    // 判断是否拦截事件
                    if (moveDown && canMoveDown) {
                        moveSpinner(offsetY);
                        return true;
                    }

                    if (moveUp && canMoveUp) {
                        if (offsetY < -20)
                            offsetY = -40;
                        moveSpinner(offsetY);
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouch = false;
                if (currentTargetOffsetTop > START_POSITION) {
                    finishSpinner();
                }
                activePointerId = INVALID_POINTER;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return super.dispatchTouchEvent(ev);
                }
                lastMotionY = ev.getY(pointerIndex);
                lastEvent = ev;
                activePointerId = ev.getPointerId(pointerIndex);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                lastMotionY = ev.getY(ev.findPointerIndex(activePointerId));
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void moveSpinner(float diff) {
        int offset = Math.round(diff);
        if (offset == 0) {
            return;
        }

        // 发送cancel事件给child
        if (!hasSendCancelEvent && isTouch && currentTargetOffsetTop > START_POSITION) {
            sendCancelEvent();
            hasSendCancelEvent = true;
        }

        int targetY = Math.max(0, currentTargetOffsetTop + offset); // target不能移动到小于0的位置……
        // y = x - (x/2)^2
        float extraOS = targetY - totalDragDistance;
        float slingshotDist = totalDragDistance;
        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2) / slingshotDist);
        float tensionPercent = (float) (tensionSlingshotPercent - Math.pow(tensionSlingshotPercent / 2, 2));

        if (offset > 0) { // 下拉的时候才添加阻力
            offset = (int) (offset * (1f - tensionPercent));
            targetY = Math.max(0, currentTargetOffsetTop + offset);
        }

        // 1. 在RESET状态时，第一次下拉出现header的时候，设置状态变成PULL
        if (state == State.RESET && currentTargetOffsetTop == START_POSITION && targetY > 0) {
            changeState(State.PULL);
        }

        // 2. 在PULL或者COMPLETE状态时，header回到顶部的时候，状态变回RESET
        if (currentTargetOffsetTop > START_POSITION && targetY <= START_POSITION) {
            if (state == State.PULL || state == State.COMPLETE) {
                changeState(State.RESET);
            }
        }

        // 3. 如果是从底部回到顶部的过程(往上滚动)，并且手指是松开状态, 并且当前是PULL状态，状态变成LOADING，这时候我们需要强制停止autoScroll
        if (state == State.PULL && !isTouch && currentTargetOffsetTop > totalDragDistance && targetY <= totalDragDistance) {
            autoScroll.stop();
            changeState(State.LOADING);
            if (listeners != null)
                for (OnRefreshListener refreshListener : listeners)
                    refreshListener.onRefresh();
            // 因为判断条件targetY <= totalDragDistance，会导致不能回到正确的刷新高度（有那么一丁点偏差），调整change
            int adjustOffset = totalDragDistance - targetY;
            offset += adjustOffset;
        }

        setTargetOffsetTopAndBottom(offset);

        // 别忘了回调header的位置改变方法。
        if (refreshHeader instanceof RefreshHeader) {
            ((RefreshHeader) refreshHeader)
                    .onPositionChange(currentTargetOffsetTop, lastTargetOffsetTop, totalDragDistance, isTouch, state);

        }

    }

    private void finishSpinner() {
        if (state == State.LOADING) {
            if (currentTargetOffsetTop > totalDragDistance) {
                autoScroll.scrollTo(totalDragDistance, SCROLL_TO_REFRESH_DURATION);
            }
        } else {
            autoScroll.scrollTo(START_POSITION, SCROLL_TO_TOP_DURATION);
        }
    }


    private void changeState(State state) {
        this.state = state;

//        Toast.makeText(getContext(), state.toString(), Toast.LENGTH_SHORT).show();
        RefreshHeader refreshHeader = this.refreshHeader instanceof RefreshHeader ? ((RefreshHeader) this.refreshHeader) : null;
        if (refreshHeader != null) {
            switch (state) {
                case RESET:
                    refreshHeader.reset();
                    break;
                case PULL:
                    refreshHeader.pull();
                    break;
                case LOADING:
                    refreshHeader.refreshing();
                    break;
                case COMPLETE:
                    refreshHeader.complete();
                    break;
            }
        }
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        if (offset == 0) {
            return;
        }
        target.offsetTopAndBottom(offset);
        refreshHeader.offsetTopAndBottom(offset);
        lastTargetOffsetTop = currentTargetOffsetTop;
        currentTargetOffsetTop = target.getTop();
//        Log.e(TAG, "moveSpinner: currentTargetOffsetTop = "+ currentTargetOffsetTop);
        invalidate();
    }

    private void sendCancelEvent() {
        if (lastEvent == null) {
            return;
        }
        MotionEvent ev = MotionEvent.obtain(lastEvent);
        ev.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(ev);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == activePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            lastMotionY = ev.getY(newPointerIndex);
            activePointerId = ev.getPointerId(newPointerIndex);
        }
    }

    private boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (target instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) target;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(target, -1) || target.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(target, -1);
        }
    }

    private class AutoScroll implements Runnable {
        private final Scroller scroller;
        private int lastY;

        public AutoScroll() {
            scroller = new Scroller(getContext(), interpolator);
        }

        @Override
        public void run() {
            boolean finished = !scroller.computeScrollOffset() || scroller.isFinished();
            if (!finished) {
                int currY = scroller.getCurrY();
                int offset = currY - lastY;
                lastY = currY;
                moveSpinner(offset);
                post(this);
                onScrollFinish(false);
            } else {
                stop();
                onScrollFinish(true);
            }
        }

        public void scrollTo(int to, int duration) {
            int from = currentTargetOffsetTop;
            int distance = to - from;
            stop();
            if (distance == 0) {
                return;
            }
            scroller.startScroll(0, 0, 0, distance, duration);
            post(this);
        }

        private void stop() {
            removeCallbacks(this);
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
            lastY = 0;
        }
    }

    /**
     * 在scroll结束的时候会回调这个方法
     *
     * @param isForceFinish 是否是强制结束的
     */
    private void onScrollFinish(boolean isForceFinish) {
        if (isAutoRefresh && !isForceFinish) {
            isAutoRefresh = false;
            changeState(State.LOADING);
            if (listeners != null)
                for (OnRefreshListener refreshListener : listeners)
                    refreshListener.onRefresh();
            finishSpinner();
        }
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public enum State {
        RESET, PULL, LOADING, COMPLETE
    }

    /**
     * Created by AItsuki on 2016/6/13.
     */
    public interface RefreshHeader {

        /**
         * 松手，头部隐藏后会回调这个方法
         */
        void reset();

        /**
         * 下拉出头部的一瞬间调用
         */
        void pull();

        /**
         * 正在刷新的时候调用
         */
        void refreshing();

        /**
         * 头部滚动的时候持续调用
         *
         * @param currentPos target当前偏移高度
         * @param lastPos    target上一次的偏移高度
         * @param refreshPos 可以松手刷新的高度
         * @param isTouch    手指是否按下状态（通过scroll自动滚动时需要判断）
         * @param state      当前状态
         */
        void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state);

        /**
         * 刷新成功的时候调用
         */
        void complete();
    }
}
