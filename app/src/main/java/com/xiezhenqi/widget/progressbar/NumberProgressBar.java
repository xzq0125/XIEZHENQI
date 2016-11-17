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

    private static final int DEFAULT_REACHED_THICKNESS = 4;//dp
    private static final int DEFAULT_UNREACHED_THICKNESS = 3;//dp

    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    private static final int DEFAULT_TEXT_COLOR = DEFAULT_REACHED_COLOR;

    private static final int DEFAULT_PADDING = 5;//dp

    private int mReachedColor = DEFAULT_REACHED_COLOR;//到达颜色
    private int mUnreachedColor = DEFAULT_UNREACHED_COLOR;//未到达颜色

    private int mReachedThickness = dp2px(DEFAULT_REACHED_THICKNESS);//到达厚度
    private int mUnreachedThickness = dp2px(DEFAULT_UNREACHED_THICKNESS);//未到达厚度
    private boolean mDrawUnreachedBar = true;

    private int mTextColor = DEFAULT_TEXT_COLOR;//文本颜色
    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);//文本大小

    private boolean mDrawText = true;
    private int mTextPaddingStart = dp2px(DEFAULT_PADDING);//text padding start
    private int mTextPaddingEnd = dp2px(DEFAULT_PADDING);//text padding end
    private int mOrientation = 0;//进度条方向
    private int mDrawOrientation = 0;//进度条方向

    private int mRealLength;//真正的长度

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

        mReachedThickness = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_reachedThickness, mReachedThickness);
        mUnreachedThickness = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_unreachedThickness, mUnreachedThickness);
        mDrawUnreachedBar = a.getBoolean(R.styleable.NumberProgressBar_npb_drawUnreachedBar, mDrawUnreachedBar);

        mTextColor = a.getColor(R.styleable.NumberProgressBar_npb_textColor, mTextColor);
        mTextSize = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_textSize, mTextSize);
        mTextPaddingStart = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_textPaddingStart, mTextPaddingStart);
        mTextPaddingEnd = a.getDimensionPixelSize(R.styleable.NumberProgressBar_npb_textPaddingEnd, mTextPaddingEnd);
        mDrawText = a.getBoolean(R.styleable.NumberProgressBar_npb_drawText, mDrawText);

        mOrientation = a.getInt(R.styleable.NumberProgressBar_npb_orientation, mOrientation);
        mDrawOrientation = a.getInt(R.styleable.NumberProgressBar_npb_drawDirection, mDrawOrientation);

        a.recycle();

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = measureHeight(heightMeasureSpec);
            setMeasuredDimension(width, height);
            mRealLength = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        } else {
            int width = measureWidth(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
            mRealLength = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        }
    }

    private int measureWidth(int widthMeasureSpec) {
        int width;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            width = specSize;
        } else {
            float textWidth = getTextWidth("100%");
            width = (int) (getPaddingLeft() + getPaddingRight() +
                    Math.max(Math.max(mReachedThickness, mUnreachedThickness), textWidth) + 0.5f);
            if (specMode == MeasureSpec.AT_MOST) {
                width = Math.min(specSize, width);
            }
        }

        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            float textHeight = getTextHeight();
            height = (int) (getPaddingTop() + getPaddingBottom() +
                    Math.max(Math.max(mReachedThickness, mUnreachedThickness), textHeight) + 0.5f);
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(specSize, height);
            }
        }

        return height;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (mOrientation == 0) {
            drawHorizontal(canvas);
        } else {
            drawVertical(canvas);
        }
    }

    private float getTextWidth(String text) {
        return mDrawText ? mPaint.measureText(text) : 0;
    }

    private float getTextHeight() {
        return Math.abs(mDrawText ? (mPaint.descent() - mPaint.ascent()) : 0);
    }

    private void drawHorizontal(Canvas canvas) {
        canvas.save();

        canvas.translate(getPaddingLeft(), 0);

        float percent = getProgress() * 1.0f / getMax();
        float progressPosX = (int) (mRealLength * percent);
        String text = getProgress() + "%";

        float textWidth = getTextWidth(text);
        float textHeight = mPaint.descent() + mPaint.ascent();
        int textPaddingStart = mDrawText ? mTextPaddingStart : 0;
        int textPaddingEnd = mDrawText ? mTextPaddingEnd : 0;

        boolean drawUnreached = true;
        if (progressPosX + textWidth > mRealLength) {
            progressPosX = mRealLength - textWidth;
            drawUnreached = false;
        }

        // draw reached bar
        float endX = progressPosX - textPaddingStart;
        if (endX > 0) {
            mPaint.setColor(mReachedColor);
            mPaint.setStrokeWidth(mReachedThickness);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }
        // draw progress bar
        // measure text bound
        if (mDrawText) {
            mPaint.setColor(mTextColor);
            canvas.drawText(text, progressPosX, -textHeight + 3, mPaint);
        }

        // draw unreached bar
        if (mDrawUnreachedBar && drawUnreached) {
            float start = progressPosX + textPaddingEnd + textWidth;
            mPaint.setColor(mUnreachedColor);
            mPaint.setStrokeWidth(mUnreachedThickness);
            canvas.drawLine(start, 0, mRealLength, 0, mPaint);
        }

        canvas.restore();
    }

    private void drawVertical(Canvas canvas) {
        canvas.save();

        canvas.translate(0, getPaddingTop());

        float percent = getProgress() * 1.0f / getMax();
        float progressPosY = (int) (mRealLength * percent);
        String text = getProgress() + "%";

        float textWidth = getTextWidth(text);
        float textHeight = getTextHeight();
        int textPaddingStart = mDrawText ? mTextPaddingStart : 0;
        int textPaddingEnd = mDrawText ? mTextPaddingEnd : 0;

        int width = getMeasuredWidth();
        int lineX = width / 2;

        boolean drawUnreached = true;
        if (progressPosY + textHeight > mRealLength) {
            progressPosY = mRealLength - textHeight;
            drawUnreached = false;
        }

        // draw reached bar
        float endY = progressPosY - textPaddingStart;
        if (endY > 0) {
            mPaint.setColor(mReachedColor);
            mPaint.setStrokeWidth(mReachedThickness);
            canvas.drawLine(lineX, 0, lineX, endY, mPaint);
        }

        // draw progress bar
        // measure text bound
        if (mDrawText) {
            mPaint.setColor(mTextColor);
            canvas.drawText(text, (width - textWidth) / 2, progressPosY + textHeight, mPaint);
        }

        // draw unreached bar
        if (mDrawUnreachedBar && drawUnreached) {
            float start = progressPosY + textPaddingEnd + textHeight;
            mPaint.setColor(mUnreachedColor);
            mPaint.setStrokeWidth(mUnreachedThickness);
            canvas.drawLine(lineX, start, lineX, mRealLength, mPaint);
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

