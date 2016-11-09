package com.xiezhenqi.business.more.contextlist;

import android.content.Context;

import com.xiezhenqi.business.dialogs.AddTextDialog;

/**
 * AddSingerDialog
 * Created by sean on 2016/11/9.
 */

public class AddSingerDialog extends AddTextDialog {

    public AddSingerDialog(Context context, TextCallback callBack) {
        super(context, callBack);
    }

    @Override
    protected String getTitle(Context context) {
        return "添加歌手";
    }

    @Override
    protected String getTextHint(Context context) {
        return "请输入歌手名字";
    }
}
