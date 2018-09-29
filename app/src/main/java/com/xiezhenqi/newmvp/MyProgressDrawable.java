package com.xiezhenqi.newmvp;

import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.utils.SizeUtils;


/**
 * MyProgressDrawable
 * Created by xzq on 2018/9/29.
 */
public class MyProgressDrawable extends ProgressDrawable {

    @Override
    public int getIntrinsicWidth() {
        return SizeUtils.dp2px(XZQApplication.getContext(), 20);
    }

    @Override
    public int getIntrinsicHeight() {
        return SizeUtils.dp2px(XZQApplication.getContext(), 20);
    }
}
