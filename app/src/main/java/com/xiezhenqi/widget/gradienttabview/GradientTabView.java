package com.xiezhenqi.widget.gradienttabview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xiezhenqi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信渐变导航标签
 * Created by Tse on 2016/10/19.
 */

public class GradientTabView extends LinearLayout implements View.OnClickListener {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private SparseArray<TabItem> mTabItems;

    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColorSelect;
    private int mTextColorNormal = 0xff999999;
    private int mPadding = dp2px(10);
    private int mStyle;
    private boolean mShowIcon;

    public GradientTabView(Context context) {
        this(context, null);
    }

    public GradientTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getResources().obtainAttributes(attrs, R.styleable.GradientTabView);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.GradientTabView_text_size:
                    mTextSize = a.getDimensionPixelSize(attr, mTextSize);
                    break;
                case R.styleable.GradientTabView_text_normal_color:
                    mTextColorNormal = a.getColor(attr, mTextColorNormal);
                    break;
                case R.styleable.GradientTabView_text_select_color:
                    mTextColorSelect = a.getColor(attr, ContextCompat.getColor(context, R.color.themeColor));
                    break;
                case R.styleable.GradientTabView_item_padding:
                    mPadding = a.getDimensionPixelSize(attr, mPadding);
                    break;
                case R.styleable.GradientTabView_text_style:
                    mStyle = a.getInt(attr, Typeface.NORMAL);
                    break;
                case R.styleable.GradientTabView_show_icon:
                    mShowIcon = a.getBoolean(attr, false);
                    break;
            }
        }
        a.recycle();
        initItem(getItemCount());
    }

    public void attachToViewPager(final ViewPager viewPager) {
        if (viewPager == null)
            return;

        mViewPager = viewPager;
        mPagerAdapter = viewPager.getAdapter();
        if (mPagerAdapter == null) {
            throw new RuntimeException("请先设置Adapter");
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                View currView;
                View nextView;

                if (positionOffset > 0) {
                   // currView = viewPager.getChildAt(position);
                    //nextView = viewPager.getChildAt(position + 1);
                    //currView.setAlpha(1 - positionOffset);
                    //nextView.setAlpha(positionOffset);
                    mTabItems.get(position).setTabAlpha(1 - positionOffset);
                    mTabItems.get(position + 1).setTabAlpha(positionOffset);
                } else {
                    //viewPager.getChildAt(position).setAlpha(1);
                    mTabItems.get(position).setTabAlpha(1 - positionOffset);
                }
                if (listeners != null)
                    for (ViewPager.OnPageChangeListener pageChangeListener : listeners)
                        pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                if (listeners != null)
                    for (ViewPager.OnPageChangeListener pageChangeListener : listeners)
                        pageChangeListener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (listeners != null)
                    for (ViewPager.OnPageChangeListener pageChangeListener : listeners)
                        pageChangeListener.onPageScrollStateChanged(state);
            }
        });

        initItem(getItemCount());
    }

    private int getItemCount() {
        if (isInEditMode())
            return 3;
        if (mPagerAdapter == null)
            return 0;
        return mPagerAdapter.getCount();
    }

    private CharSequence getItemText(int position) {
        if (isInEditMode()) {
            return "Tab" + position;
        }
        if (mPagerAdapter == null)
            return "";
        return mPagerAdapter.getPageTitle(position);
    }

    private List<ViewPager.OnPageChangeListener> listeners;

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (listener == null)
            return;

        if (listeners == null)
            listeners = new ArrayList<>();

        if (listeners.contains(listener))
            return;

        listeners.add(listener);
    }

    private void initItem(int count) {
        if (count > 0)
            mTabItems = new SparseArray<>();

        for (int i = 0; i < count; i++) {
            TabItem tabItem = new TabItem(getContext());
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tabItem.setPadding(mPadding, mPadding, mPadding, mPadding);
            if (mShowIcon && mPagerAdapter instanceof GradientTabAdapter) {
                GradientTabAdapter adapter = (GradientTabAdapter) mPagerAdapter;
                tabItem.setIconSelected(adapter.getSelectedDrawable(i, getContext()));
                tabItem.setIconNormal(adapter.getNormalDrawable(i, getContext()));
            }
            tabItem.setText(getItemText(i).toString());
            tabItem.setTextSize(mTextSize);
            tabItem.setTextColorNormal(mTextColorNormal);
            tabItem.setTextColorSelect(mTextColorSelect);
            tabItem.setLayoutParams(params);
            tabItem.setTag(i);
            tabItem.setOnClickListener(this);
            mTabItems.put(i, tabItem);
            addView(tabItem);
        }
        if (isInEditMode())
            mTabItems.get(0).setTabAlpha(1);
    }

    public interface GradientTabAdapter {
        /**
         * 获取普通状态下的 Drawable
         *
         * @param position 位置
         * @param context  Context
         * @return 必须非空
         */
        Bitmap getNormalDrawable(int position, Context context);

        /**
         * 获取选中状态下的 Drawable
         *
         * @param position 位置
         * @param context  Context
         * @return 必须非空
         */
        Bitmap getSelectedDrawable(int position, Context context);
    }


    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        if (mViewPager.getCurrentItem() == position) {
            return;
        }

        for (int i = 0; i < mTabItems.size(); i++) {
            mTabItems.get(i).setTabAlpha(0);
        }

        mTabItems.get(position).setTabAlpha(1);
        mViewPager.setCurrentItem(position, false);
    }

    class TabItem extends View {

        private int mTextSize = 12;
        private int mTextColorSelect = 0xff45c01a;
        private int mTextColorNormal = 0xff777777;
        private Paint mTextPaintNormal;
        private Paint mTextPaintSelect;
        private int mViewHeight, mViewWidth;
        private String mTextValue;
        private Bitmap mIconNormal;
        private Bitmap mIconSelect;
        private Rect mBoundText;
        private Paint mIconPaintSelect;
        private Paint mIconPaintNormal;

        public TabItem(Context context) {
            this(context, null);
        }

        public TabItem(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView();
            initText();
        }

        private void initView() {
            mBoundText = new Rect();
        }

        private void initText() {
            mTextPaintNormal = new Paint();
            setStyle(mTextPaintNormal);
            mTextPaintNormal.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics()));
            mTextPaintNormal.setColor(mTextColorNormal);
            mTextPaintNormal.setAntiAlias(true);
            mTextPaintNormal.setAlpha(0xff);

            mTextPaintSelect = new Paint();
            setStyle(mTextPaintSelect);
            mTextPaintSelect.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics()));
            mTextPaintSelect.setColor(mTextColorSelect);
            mTextPaintSelect.setAntiAlias(true);
            mTextPaintSelect.setAlpha(0);

            mIconPaintSelect = new Paint(Paint.ANTI_ALIAS_FLAG);
            mIconPaintSelect.setAlpha(0);

            mIconPaintNormal = new Paint(Paint.ANTI_ALIAS_FLAG);
            mIconPaintNormal.setAlpha(0xff);
        }

        private void setStyle(Paint paint) {
            switch (mStyle) {
                default:
                case 0:
                    paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
                    break;
                case 1:
                    paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
                    break;
                case 2:
                    paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
                    break;
                case 3:
                    paint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC));
                    break;
            }
        }

        private void measureText() {
            mTextPaintNormal.getTextBounds(mTextValue, 0, mTextValue.length(), mBoundText);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            int width = 0, height = 0;

            measureText();
            int contentWidth = Math.max(mBoundText.width(), mIconNormal != null ? mIconNormal.getWidth() : mBoundText.width());
            int desiredWidth = getPaddingLeft() + getPaddingRight() + contentWidth;
            switch (widthMode) {
                case MeasureSpec.AT_MOST:
                    width = Math.min(widthSize, desiredWidth);
                    break;
                case MeasureSpec.EXACTLY:
                    width = widthSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    width = desiredWidth;
                    break;
            }
            int contentHeight = mBoundText.height() + (mIconNormal != null ? mIconNormal.getHeight() : mBoundText.height());
            int desiredHeight = getPaddingTop() + getPaddingBottom() + contentHeight;
            switch (heightMode) {
                case MeasureSpec.AT_MOST:
                    height = Math.min(heightSize, desiredHeight);
                    break;
                case MeasureSpec.EXACTLY:
                    height = heightSize;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    height = contentHeight;
                    break;
            }
            setMeasuredDimension(width, height);
            mViewWidth = getMeasuredWidth();
            mViewHeight = getMeasuredHeight();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            drawBitmap(canvas);
            drawText(canvas);
        }

        private void drawBitmap(Canvas canvas) {

            if (mIconNormal == null || mIconSelect == null)
                return;
            int left = (mViewWidth - mIconNormal.getWidth()) / 2;
            int top = (mViewHeight - mIconNormal.getHeight() - mBoundText.height()) / 2;
            canvas.drawBitmap(mIconNormal, left, top, mIconPaintNormal);
            canvas.drawBitmap(mIconSelect, left, top, mIconPaintSelect);
        }

        private void drawText(Canvas canvas) {
            float x = (mViewWidth - mBoundText.width()) / 2.0f;
            float y = (mViewHeight + (mIconNormal != null ? mIconNormal.getHeight() : 0) + mBoundText.height()) / 2.0F;
            canvas.drawText(mTextValue, x, y, mTextPaintNormal);
            canvas.drawText(mTextValue, x, y, mTextPaintSelect);
        }

        public void setTextSize(int textSize) {
            this.mTextSize = textSize;
            mTextPaintNormal.setTextSize(textSize);
            mTextPaintSelect.setTextSize(textSize);
        }

        public void setTextColorSelect(int mTextColorSelect) {
            this.mTextColorSelect = mTextColorSelect;
            mTextPaintSelect.setColor(mTextColorSelect);
            mTextPaintSelect.setAlpha(0);
        }

        public void setTextColorNormal(int mTextColorNormal) {
            this.mTextColorNormal = mTextColorNormal;
            mTextPaintNormal.setColor(mTextColorNormal);
            mTextPaintNormal.setAlpha(0xff);
        }

        public void setTabAlpha(float alpha) {
            int paintAlpha = (int) (alpha * 255);
            mIconPaintSelect.setAlpha(paintAlpha);
            mIconPaintNormal.setAlpha(255 - paintAlpha);
            mTextPaintSelect.setAlpha(paintAlpha);
            mTextPaintNormal.setAlpha(255 - paintAlpha);
            invalidate();
        }

        public void setText(String TextValue) {
            this.mTextValue = TextValue;
        }

        public void setIconNormal(Bitmap iconNormal) {
            this.mIconNormal = iconNormal;
        }

        public void setIconSelected(Bitmap iconSelected) {
            this.mIconSelect = iconSelected;
        }
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
