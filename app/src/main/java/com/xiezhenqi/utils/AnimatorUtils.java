package com.xiezhenqi.utils;

import android.animation.ObjectAnimator;
import android.view.View;

/**
 * AnimatorUtils
 * Created by sean on 2016/10/12.
 */
public class AnimatorUtils {

    /**
     * 给View设置alpha
     *
     * @param view     View
     * @param from     fromAlpha
     * @param to       toAlpha
     * @param duration 持续时间
     */
    public static void alpha(View view, float from, float to, int duration) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", from, to);
        alpha.setDuration(duration);
        alpha.start();
    }

    /**
     * 给View设置alpha，从0到1,持续2s
     *
     * @param view View
     */
    public static void alpha(View view) {
        alpha(view, 0, 1, 2000);
    }

}
