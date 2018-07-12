package com.xiezhenqi.newmvp;


import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ModelService
 * Created by Wesley on 2018/7/9.
 */
public class ModelService {

    public interface MethodCallback<T> {
        /**
         * 获取调用方法
         *
         * @param api Api服务
         * @return Observable
         */
        Observable<NetBean<T>> getApi(ApiService api);
    }

    private static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable<NetBean<T>> getData(LifecycleProvider<?> provider, MethodCallback<T> select) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return select.getApi(api)
                .compose(provider.<NetBean<T>>bindToLifecycle())
                .compose(ModelService.<NetBean<T>>schedulersTransformer());
    }
}
