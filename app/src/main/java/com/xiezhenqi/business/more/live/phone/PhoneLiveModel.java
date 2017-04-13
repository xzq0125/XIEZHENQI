package com.xiezhenqi.business.more.live.phone;

import com.xiezhenqi.api.LiveGenerator;
import com.xiezhenqi.api.ResponseCallback;
import com.xiezhenqi.entity.DataEntity;
import com.xiezhenqi.entity.ResponseDto2;
import com.xiezhenqi.utils.encryption.MD5Util;

import retrofit2.Call;

/**
 * PhoneLiveModel
 * Created by sean on 2017/4/13.
 */

class PhoneLiveModel implements PhoneLiveContract.Model, ResponseCallback.OnResponseListener<DataEntity> {

    private final PhoneLivePresenter presenter;
    private final ResponseCallback<DataEntity> responseCallback
            = new ResponseCallback<>(this);

    PhoneLiveModel(PhoneLivePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getPhoneLiveVideoInfo(String room_id) {
        long time = (System.currentTimeMillis() / 1000);
        String str = "lapi/live/thirdPart/getPlay/" + room_id + "?aid=pcclient&rate=0&time=" + time + "9TUk5fjjUjg9qIMH3sdnh";
        String auth = MD5Util.getToMd5Low32(str);
        new LiveGenerator().getService().getPhoneLiveVideoInfo(auth, String.valueOf(time), room_id, 0)
                .enqueue(responseCallback);
    }

    @Override
    public void onSucceed(Call<ResponseDto2<DataEntity>> call, ResponseCallback<DataEntity> callback, DataEntity result) {
        presenter.startLive(result);
    }

    @Override
    public void onFailed(Call<ResponseDto2<DataEntity>> call, ResponseCallback<DataEntity> callback, int code, String message) {
        presenter.showErrorWithStatus(message);
    }
}
