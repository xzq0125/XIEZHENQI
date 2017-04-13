package com.xiezhenqi.business.more.live.phone;

import com.xiezhenqi.mvp.BasePresenter;

/**
 * PhoneLiveContract
 * Created by sean on 2017/4/13.
 */

public class PhoneLiveContract {

    interface View<T> {
        void startLive(T data);

        void showErrorWithStatus(String msg);
    }

    interface Model {
        void getPhoneLiveVideoInfo(String room_id);
    }

    interface Presenter<T> extends BasePresenter, Model, View<T> {

    }
}
