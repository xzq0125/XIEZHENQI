package com.xiezhenqi.newmvp.lifecycle;

import com.trello.navi2.component.support.NaviAppCompatActivity;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.navi.NaviLifecycle;


/**
 * 生命周期Activity，提供对外的生命周期监听器
 */

@SuppressWarnings("all")
public class LifecycleActivity extends NaviAppCompatActivity implements ILifeCycleProviderSupplier<ActivityEvent> {
    // TODO: 2017/1/9 实现Activity生命周期的对外回调

    protected final LifecycleProvider<ActivityEvent> provider
            = NaviLifecycle.createActivityLifecycleProvider(this);

    public LifecycleProvider<ActivityEvent> getLifecycleProvider() {
        return provider;
    }

}
