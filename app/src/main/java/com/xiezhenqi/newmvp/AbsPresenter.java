package com.xiezhenqi.newmvp;

import android.support.annotation.NonNull;

import com.trello.navi2.Event;
import com.trello.navi2.Listener;
import com.trello.navi2.NaviComponent;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.xiezhenqi.base.mvp.ILoadingView;
import com.xiezhenqi.newmvp.lifecycle.ILifeCycleProviderSupplier;

import io.reactivex.Observable;


public abstract class AbsPresenter<V> implements NaviComponent, ILifeCycleProviderSupplier, com.xiezhenqi.base.mvp.BasePresenter {

    protected final V mView;
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

    @Override
    public void onDestroy() {

    }

    protected <T> Observable<NetBean<T>> doRequest(ModelService.MethodCallback<T> callback) {
        return ModelService.getData(getLifecycleProvider(), callback);
    }

    public abstract class PagingNetCallback<T> extends NetCallback<T> {

        public PagingNetCallback(int page) {
            super(mView instanceof ILoadingView ? (ILoadingView) mView : null, page);
        }

    }

}
