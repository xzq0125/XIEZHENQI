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
public class NumberProgressBar extends ProgressBar {

    private static final int DEFAULT_REACHED_COLOR = 0xff3498d8;
    private static final int DEFAULT_UNREACHED_COLOR = 0xffdddddd;

    private static final int DEFAULT_REACHED_HEIGHT = 4;//dp
    private static final int DEFAULT_UNREACHED_HEIGHT = 3;//dp

    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    private static final int DEFAULT_TEXT_COLOR = DEFAULT_REACHED_COLOR;

    private static final int DEFAULT_PADDING = 5;//dp

    private int mReachedColor = DEFAULT_REACHED_COLOR;//到达颜色
    private int mUnreachedColor = DEFAULT_UNREACHED_COLOR;//未到达颜色

    private int mReachedHeight = dp2px(DEFAULT_REACHED_HEIGHT);//到达高度
    private int mUnreachedHeight = dp2px(DEFAULT_UNREACHED_HEIGHT);//未到达高度

    private int mTextColor = DEFAULT_TEXT_COLOR;//文本颜色
    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);//文本大小

    private int mTextVisibility = VISIBLE;//文本的可见性（默认可见）
    private int mTextPaddingStart = dp2px(DEFAULT_PADDING);//text padding left
    private int mTextPaddingEnd = dp2px(DEFAULT_PADDING);//text padding right
    private float mRealWidth;//真正的宽度
    private boolean mIfNeedShowText = true;//是否需要显示文本

    private final Paint mPaint = new Paint();//draw line and text

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberProgressBar);
        mReachedColor = a.getColor(R.styleable.NumberProgressBar_npb_reachedColor, mReachedColor);
        mUnreachedColor = a.getColor(R.styleable.NumberProgressBar_npb_unreachedColor, mUnreachedColor);

        mReachedHeight = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_reachedHeight, mReachedHeight);
        mUnreachedHeight = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_unreachedHeight, mUnreachedHeight);

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
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

    }

    private int measureHeight(int heightMeasureSpec) {
        int height;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            float textHeight = (mPaint.descent() - mPaint.ascent());
            height = (int) (getPaddingTop() + getPaddingBottom() +
                    Math.max(Math.max(mReachedHeight, mUnreachedHeight), Math.abs(textHeight)) + 0.5f);
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(specSize, height);
            }
        }

        return height;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();

        canvas.translate(getPaddingLeft(), 0);

        float radio = getProgress() * 1.0f / getMax();
        float progressPosX = (int) (mRealWidth * radio);
        String text = getProgress() + "%";

        float textWidth = mIfNeedShowText ? mPaint.measureText(text) : 0;
        float textHeight = mPaint.descent() + mPaint.ascent();

        boolean drawUnreached = true;
        if (progressPosX + textWidth > mRealWidth) {
            progressPosX = mRealWidth - textWidth;
            drawUnreached = false;
        }

        // draw reached bar
        float endX = progressPosX - (mIfNeedShowText ? mTextPaddingStart : 0);
        if (endX > 0) {
            mPaint.setColor(mReachedColor);
            mPaint.setStrokeWidth(mReachedHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }
        // draw progress bar
        // measure text bound
        if (mIfNeedShowText) {
            mPaint.setColor(mTextColor);
            canvas.drawText(text, progressPosX, -textHeight, mPaint);
        }

        // draw unreached bar
        if (drawUnreached) {
            float start = progressPosX + (mIfNeedShowText ? mTextPaddingEnd : 0) + textWidth;
            mPaint.setColor(mUnreachedColor);
            mPaint.setStrokeWidth(mUnreachedHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
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
    private int sp2px(float spValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                Resources.getSystem().getDisplayMetrics()) + 0.5f);
    }
}

