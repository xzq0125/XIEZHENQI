package com.xiezhenqi.base.activitys;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xiezhenqi.utils.LogUtils;

import butterknife.ButterKnife;


/**
 * Activity基类
 * 实现一些通用逻辑
 * Created by Tse on 2016/9/11.
 */
public abstract class BaseActivity extends MPermissionActivity {

    private static final String TAG = "BaseActivity";
    //Activity生命周期
    private ActivityState mState = ActivityState.CREATE;

    /**
     * Activity生命周期
     */
    private enum ActivityState {
        CREATE, START, RESTART, RESUME, PAUSE, STOP, DESTROY
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mState = ActivityState.CREATE;
        super.onCreate(savedInstanceState);
        LogUtils.debug(TAG, getClass().getSimpleName());
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initViews(savedInstanceState);
    }

    @Override
    protected void onStart() {
        mState = ActivityState.START;
        super.onStart();
    }

    @Override
    protected void onRestart() {
        mState = ActivityState.RESTART;
        super.onRestart();
    }

    @Override
    protected void onResume() {
        mState = ActivityState.RESUME;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mState = ActivityState.PAUSE;
        super.onPause();
    }

    @Override
    protected void onStop() {
        mState = ActivityState.STOP;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mState = ActivityState.DESTROY;
        super.onDestroy();
    }

    protected boolean isResume() {
        return mState == ActivityState.RESUME;
    }

    /**
     * 获取布局ID
     *
     * @return 布局ID
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化资源
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    /**
     * 设置Toolbar 为ActionBar
     *
     * @param toolbarId Toolbar资源ID
     */
    @SuppressWarnings("all")
    public Toolbar setSupportActionBar(@IdRes final int toolbarId) {
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
            //设置ActionBar的标题和子标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    /**
     * Toolbar 返回按钮点击,若要做返回逻辑，子Activity需重写onBackPressed()
     */
    protected void onNavigationClick() {
        onBackPressed();
    }

    /**
     * Finds a view that was identified by the id attribute from the XML that
     * was processed in {@link #onCreate}.
     *
     * @return The view if found or null otherwise.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById2(@IdRes int id) {
        return (T) findViewById(id);
    }

}
