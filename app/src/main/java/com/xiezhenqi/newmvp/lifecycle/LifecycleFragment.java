package com.xiezhenqi.newmvp.lifecycle;

import com.trello.navi2.component.support.NaviFragment;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.navi.NaviLifecycle;

/**
 * 生命周期Fragment，提供对外的生命周期监听器
 */

public abstract class LifecycleFragment extends NaviFragment implements ILifeCycleProviderSupplier<FragmentEvent> {

    protected final LifecycleProvider<FragmentEvent> provider
            = NaviLifecycle.createFragmentLifecycleProvider(this);

    @Override
    public LifecycleProvider<FragmentEvent> getLifecycleProvider() {
        return provider;
    }
    // TODO: 2017/1/9 实现Fragment的对外回调
}
