package com.xiezhenqi.widget.progressbar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.xiezhenqi.R;


/**
 * 带数字百分比的进度条
 */
public class NumberProgressBar2 extends ProgressBar {

    private static final int DEFAULT_REACHED_COLOR = 0xff3498d8;
    private static final int DEFAULT_UNREACHED_COLOR = 0xffdddddd;

    private static final int DEFAULT_REACHED_WIDTH = 4;//dp
    private static final int DEFAULT_UNREACHED_WIDTH = 3;//dp

    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    private static final int DEFAULT_TEXT_COLOR = DEFAULT_REACHED_COLOR;

    private static final int DEFAULT_PADDING = 5;//dp

    private int mReachedColor = DEFAULT_REACHED_COLOR;//到达颜色
    private int mUnreachedColor = DEFAULT_UNREACHED_COLOR;//未到达颜色

    private int mReachedWidth = dp2px(DEFAULT_REACHED_WIDTH);//到达宽度
    private int mUnreachedWidth = dp2px(DEFAULT_UNREACHED_WIDTH);//未到达宽度

    private int mTextColor = DEFAULT_TEXT_COLOR;//文本颜色
    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);//文本大小

    private int mTextVisibility = VISIBLE;//文本的可见性（默认可见）
    private int mTextPaddingStart = dp2px(DEFAULT_PADDING);//text padding left
    private int mTextPaddingEnd = dp2px(DEFAULT_PADDING);//text padding right
    private float mRealHeight;//真正的高度
    private boolean mIfNeedShowText = true;//是否需要显示文本
    private boolean drawUnreachedBar = false;//是否需要显示文本

    private final Paint mPaint = new Paint();//draw line and text

    public NumberProgressBar2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberProgressBar);
        mReachedColor = a.getColor(R.styleable.NumberProgressBar_npb_reachedColor, mReachedColor);
        mUnreachedColor = a.getColor(R.styleable.NumberProgressBar_npb_unreachedColor, mUnreachedColor);

        mReachedWidth = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_reachedHeight, mReachedWidth);
        mUnreachedWidth = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_unreachedHeight, mUnreachedWidth);

        mTextColor = a.getColor(R.styleable.NumberProgressBar_npb_textColor, mTextColor);
        mTextSize = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_textSize, mTextSize);
        mTextVisibility = a.getInt(R.styleable.NumberProgressBar_npb_textVisibility, mTextVisibility);
        mTextPaddingStart = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_textPaddingStart, mTextPaddingStart);
        mTextPaddingEnd = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_textPaddingEnd, mTextPaddingEnd);
        a.recycle();

        mIfNeedShowText = mTextVisibility == VISIBLE;

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mRealHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }


    private int measureWidth(int widthMeasureSpec) {
        int width;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            width = specSize;
        } else {
            float textWidth = (int) (mPaint.measureText("100%") + 0.5f);
            width = (int) (getPaddingLeft() + getPaddingRight() +
                    Math.max(Math.max(mReachedWidth, mUnreachedWidth), Math.abs(textWidth)) + 0.5f);
            if (specMode == MeasureSpec.AT_MOST) {
                width = Math.min(specSize, width);
            }
        }

        return width;
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();

        canvas.translate(0, getPaddingTop());

        float percent = getProgress() * 1.0f / getMax();
        float progressPosY = (int) (mRealHeight * percent);
        String text = getProgress() + "%";

        float textWidth = (mIfNeedShowText ? (int) (mPaint.measureText(text) + 0.5f) : 0);
        float textHeight = (mIfNeedShowText ? Math.abs(mPaint.descent() - mPaint.ascent()) : 0);

        int width = getMeasuredWidth();
        int lineX = width / 2;

        boolean drawUnreached = true;
        if (progressPosY + textHeight > mRealHeight) {
            progressPosY = mRealHeight - textHeight;
            drawUnreached = false;
        }

        // draw reached bar
        float endY = progressPosY - (mIfNeedShowText ? mTextPaddingStart : 0);
        if (endY > 0) {
            mPaint.setColor(mReachedColor);
            mPaint.setStrokeWidth(mReachedWidth);
            canvas.drawLine(lineX, 0, lineX, endY, mPaint);
        }

        // draw progress bar
        // measure text bound
        if (mIfNeedShowText) {
            mPaint.setColor(mTextColor);
            canvas.drawText(text, (width - textWidth) / 2, progressPosY + (textHeight + mTextPaddingEnd) / 2, mPaint);
        }

        // draw unreached bar
        if (drawUnreachedBar && drawUnreached) {
            float start = progressPosY + (mIfNeedShowText ? mTextPaddingEnd : 0) + (mIfNeedShowText ? textHeight : 0);
            mPaint.setColor(mUnreachedColor);
            mPaint.setStrokeWidth(mUnreachedWidth);
            canvas.drawLine(lineX, start, lineX, mRealHeight, mPaint);
        }

        canvas.restore();

    }

    /**
     * dp 2 px
     *
     * @param dpValue dp
     * @return px
     */
    private static int dp2px(float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                Resources.getSystem().getDisplayMetrics()) + 0.5f);
    }

    /**
     * sp 2 px
     *
     * @param spValue sp
     * @return px
     */
    private static int sp2px(float spValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                Resources.getSystem().getDisplayMetrics()) + 0.5f);
    }
}

