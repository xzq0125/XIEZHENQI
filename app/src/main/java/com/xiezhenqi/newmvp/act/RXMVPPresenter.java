package com.xiezhenqi.newmvp.act;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.xiezhenqi.base.mvp.BaseListContract;
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
public class RXMVPPresenter extends AbsPresenter<RXMVPContract.View> implements BaseListContract.Presenter {

    public RXMVPPresenter(@NonNull RXMVPContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getList(final int page) {
        doRequest(new ModelService.MethodCallback<ShopBean>() {
            @Override
            public Observable<NetBean<ShopBean>> getApi(ApiService api) {
                return api.getShopList(page, 2, 7, "number_desc");
            }
        }).subscribeWith(new PagingNetCallback<ShopBean>(page) {

            @Override
            protected void onSetData(ShopBean data, int page, boolean hasNextPage) {
                mView.setData(data.list, page, hasNextPage);
            }

            @Override
            protected void onAddData(ShopBean data, int page, boolean hasNextPage) {
                mView.addData(data.list, page, hasNextPage);
            }
        });
    }
}
