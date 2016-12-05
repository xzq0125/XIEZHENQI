package com.xiezhenqi.business.more.mazing.managers;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;


/**
 * ToolbarMoveAnimator
 * Created by sean on 2016/9/29.
 */
public class ToolbarMoveAnimator {

    private View view;
    private boolean isUp;
    private ValueAnimator moveUp;
    private ValueAnimator moveDown;

    public ToolbarMoveAnimator(View view) {
        this.view = view;
    }

    public void moveUp(float fromY, int toY) {

        if (isUp)
            return;

        isUp = true;

        if (moveUp == null) {
            moveUp = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
            moveUp.setDuration(300);
        }

        if (moveUp.isRunning())
            return;

        moveUp.start();
    }

    public void moveDown(float fromY, int toY) {

        if (!isUp)
            return;

        isUp = false;

        if (moveDown == null) {
            moveDown = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
            moveDown.setDuration(300);
        }

        if (moveDown.isRunning())
            return;

        moveDown.start();
    }
}
