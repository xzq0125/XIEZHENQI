package com.xiezhenqi.newmvp.act;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.xiezhenqi.base.mvp.BaseListContract;
import com.xiezhenqi.newmvp.AbsPresenter;
import com.xiezhenqi.newmvp.ApiService;
import com.xiezhenqi.newmvp.HomePageBean;
import com.xiezhenqi.newmvp.ModelService;
import com.xiezhenqi.newmvp.NetBean;

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
    public void getList(final int page, boolean isRefresh) {
        doRequest(new ModelService.MethodCallback<HomePageBean>() {
            @Override
            public Observable<NetBean<HomePageBean>> getApi(ApiService api) {
                return api.getWangAndroidHomePage(page);
            }
        }).subscribeWith(new PagingNetCallback<HomePageBean>(page, isRefresh) {

            @Override
            protected void onSetData(HomePageBean data, int page, boolean hasNextPage) {
                mView.setData(data.datas, page, hasNextPage);
            }

            @Override
            protected void onAddData(HomePageBean data, int page, boolean hasNextPage) {
                mView.addData(data.datas, page, hasNextPage);
            }
        });
    }
}
