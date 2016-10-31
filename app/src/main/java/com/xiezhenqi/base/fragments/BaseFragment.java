package com.xiezhenqi.base.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基础类
 * 实现一些通用逻辑
 * Created by Tse on 2016/9/11.
 */
public abstract class BaseFragment extends Fragment {

    public enum FragmentState {
        CREATE, START, RESUME, PAUSE, STOP, DESTROY
    }

    private FragmentState mState = FragmentState.CREATE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mState = FragmentState.CREATE;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        mState = FragmentState.START;
        super.onStart();
    }

    @Override
    public void onResume() {
        mState = FragmentState.RESUME;
        super.onResume();
    }

    @Override
    public void onPause() {
        mState = FragmentState.PAUSE;
        super.onPause();
    }

    @Override
    public void onStop() {
        mState = FragmentState.STOP;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mState = FragmentState.DESTROY;
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(inflater, container, savedInstanceState),
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
    }

    /**
     * 设置内容Layout
     * <p/>
     * 返回的必须是资源 layout ID
     *
     * @param inflater           布局容器
     * @param container          根View
     * @param savedInstanceState 保存的状态
     * @return 资源id
     */
    @LayoutRes
    protected abstract int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * Look for a child view with the given id. If this view has the given id,
     * return this view.
     *
     * @param id The id to search for.
     * @return The view that has the given id in the hierarchy or null
     */
    public <T extends View> T findViewById(@IdRes int id) {
        if (getView() == null) {
            throw new IllegalStateException("Fragment " + this
                    + " not attached to Activity");
        }
        return (T) getView().findViewById(id);
    }

    /**
     * 获取是否为Resume状态
     *
     * @return 是否为Resume状态
     */
    public final boolean isResume() {
        return mState == FragmentState.RESUME;
    }
}
