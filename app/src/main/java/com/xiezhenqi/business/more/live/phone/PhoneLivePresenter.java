package com.xiezhenqi.business.more.live.phone;

import com.xiezhenqi.entity.DataEntity;

/**
 * PhoneLivePresenter
 * Created by sean on 2017/4/13.
 */

class PhoneLivePresenter implements PhoneLiveContract.Presenter<DataEntity> {

    private PhoneLiveContract.View<DataEntity> mView;
    private final PhoneLiveContract.Model mModel = new PhoneLiveModel(this);

    public PhoneLivePresenter(PhoneLiveContract.View<DataEntity> mView) {
        this.mView = mView;
    }

    @Override
    public void recycle() {
        mView = null;
    }

    @Override
    public void startLive(DataEntity data) {
        if (mView == null)
            return;
        mView.startLive(data);
    }

    @Override
    public void showErrorWithStatus(String msg) {
        if (mView == null)
            return;
        mView.showErrorWithStatus(msg);
    }

    @Override
    public void getPhoneLiveVideoInfo(String room_id) {
        if (mView == null)
            return;
        mModel.getPhoneLiveVideoInfo(room_id);
    }
}
