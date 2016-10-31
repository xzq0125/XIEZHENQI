package com.xiezhenqi.business.search;

import android.support.annotation.NonNull;
import android.view.View;

import com.xiezhenqi.utils.AnimatorUtils;


/**
 * BackTopManager
 * Created by Tse on 2016/10/20.
 */

public class BackTopManager {

    private View view;
    private boolean visible;

    public BackTopManager(@NonNull View view) {
        this.view = view;
        view.setAlpha(0);
    }

    public void visible() {

        if (visible)
            return;

        AnimatorUtils.alpha(view, 0, 1, 1000);

        visible = true;
    }

    public void gone() {

        if (!visible)
            return;

        AnimatorUtils.alpha(view, 1, 0, 1000);

        visible = false;
    }

}






