package com.xiezhenqi.newmvp.act;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.xiezhenqi.newmvp.AbsPresenter;
import com.xiezhenqi.newmvp.ApiService;
import com.xiezhenqi.newmvp.ModelService;
import com.xiezhenqi.newmvp.NetBean;
import com.xiezhenqi.newmvp.ShopBean;

import io.reactivex.Observable;

/**
 * RXMVPPresenter
 * Created by Wesley on 2018/7/10.
 */
public class RXMVPPresenter extends AbsPresenter<RXMVPContract.View> implements RXMVPContract.Presenter {

    public RXMVPPresenter(@NonNull RXMVPContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getData(final int page) {
        doRequest(new ModelService.MethodCallback<ShopBean>() {
            @Override
            public Observable<NetBean<ShopBean>> getApi(ApiService api) {
                return api.getShopList(page, 2, 7, "number_desc");
            }
        }).subscribeWith(new PagingNetCallback<ShopBean>(page) {
            @Override
            protected void onSuccess(ShopBean data) {
                if (page == 1) {
                    mView.setData(data.list, data.list.size() == 10);
                } else {
                    mView.addData(data.list, data.list.size() == 10);
                }
            }
        });
    }
}
