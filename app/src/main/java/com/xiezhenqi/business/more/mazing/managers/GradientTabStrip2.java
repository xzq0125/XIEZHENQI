package com.xiezhenqi.business.more.mazing.managers;

import android.content.Context;
import android.util.AttributeSet;

import am.widget.gradienttabstrip.GradientTabStrip;

/**
 * GradientTabStrip2
 * Created by Tse on 2016/12/3.
 */

public class GradientTabStrip2 extends GradientTabStrip {

    public GradientTabStrip2(Context context) {
        super(context);
    }

    public GradientTabStrip2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTabStrip2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void jumpTo(int current) {
        super.jumpTo(current);
    }

    @Override
    public void gotoLeft(int current, int next, float offset) {
        super.gotoLeft(current, next, offset);
    }

    @Override
    public void gotoRight(int current, int next, float offset) {
        super.gotoRight(current, next, offset);
    }
}
