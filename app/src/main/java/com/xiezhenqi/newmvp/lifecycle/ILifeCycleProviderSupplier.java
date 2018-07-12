package com.xiezhenqi.newmvp.lifecycle;

import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 生命周期提供者持有者
 */

public interface ILifeCycleProviderSupplier<E> {

    LifecycleProvider<E> getLifecycleProvider();

}
