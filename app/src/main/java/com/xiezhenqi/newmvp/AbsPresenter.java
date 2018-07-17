package com.xiezhenqi.newmvp;

import android.support.annotation.NonNull;

import com.trello.navi2.Event;
import com.trello.navi2.Listener;
import com.trello.navi2.NaviComponent;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.xiezhenqi.base.mvp.BasePresenter;
import com.xiezhenqi.base.mvp.ILoadingEntityView;
import com.xiezhenqi.newmvp.lifecycle.ILifeCycleProviderSupplier;

import io.reactivex.Observable;


/**
 * 抽象的Presenter类 <br/>
 */
public abstract class AbsPresenter<V> implements NaviComponent, ILifeCycleProviderSupplier, BasePresenter {

    protected V mView;
    protected NaviComponent naviComponent;
    protected LifecycleProvider<?> provider;

    public AbsPresenter(@NonNull V view) {
        this.mView = view;
        if (view instanceof NaviComponent) {
            naviComponent = (NaviComponent) view;
        }
        if (view instanceof ILifeCycleProviderSupplier) {
            provider = ((ILifeCycleProviderSupplier) view).getLifecycleProvider();
        }
    }

    @Override
    public final boolean handlesEvents(Event... events) {
        return naviComponent.handlesEvents(events);
    }

    @Override
    public final <T> void addListener(@NonNull Event<T> event, @NonNull Listener<T> listener) {
        naviComponent.addListener(event, listener);
    }

    @Override
    public final <T> void removeListener(@NonNull Listener<T> listener) {
        naviComponent.removeListener(listener);
    }

    @Override
    public LifecycleProvider<?> getLifecycleProvider() {
        return provider;
    }

    /**
     * 发起请求
     *
     * @param callback 回调获取方法
     * @param <T>      实体类型
     * @return Observable
     */
    protected <T> Observable<NetBean<T>> doRequest(ModelService.MethodCallback<T> callback) {
        return ModelService.getData(getLifecycleProvider(), callback);
    }

    /**
     * 实体加载回调
     *
     * @param <T>
     */
    public abstract class EntityNetCallback<T> extends NetCallback<T> {

        public EntityNetCallback() {
            super(mView instanceof ILoadingEntityView ? (ILoadingEntityView) mView : null);
        }

        @Override
        protected void onStart() {
            if (mView != null)
                super.onStart();
        }

        @Override
        public void onComplete() {
            if (mView != null)
                super.onComplete();
        }

        @Override
        protected void onError(String error, int code) {
            if (mView != null)
                super.onError(error, code);
        }

        @Override
        public void onNext(@NonNull NetBean<T> netResponse) {
            if (mView != null)
                super.onNext(netResponse);
        }

    }

    /**
     * 分页加载回调
     *
     * @param <T>
     */
    public abstract class PagingNetCallback<T> extends NetCallback<T> {

        public PagingNetCallback(int page) {
            super(mView instanceof ILoadingEntityView ? (ILoadingEntityView) mView : null, page);
        }

        @Override
        protected void onStart() {
            if (mView != null)
                super.onStart();
        }

        @Override
        public void onComplete() {
            if (mView != null)
                super.onComplete();
        }

        @Override
        protected void onError(String error, int code) {
            if (mView != null)
                super.onError(error, code);
        }

        @Override
        public void onNext(@NonNull NetBean<T> netResponse) {
            if (mView != null)
                super.onNext(netResponse);
        }

        @Override
        protected void onSuccess(T data, String msg, int code, int page, boolean hasNextPage) {
            if (mView != null) {
                if (isFirstPage()) {
                    onSetData(data, page, hasNextPage);
                } else {
                    onAddData(data, page, hasNextPage);
                }
            }
        }

        protected abstract void onSetData(T data, int page, boolean hasNextPage);

        protected abstract void onAddData(T data, int page, boolean hasNextPage);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
