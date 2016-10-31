package com.xiezhenqi.base.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.xiezhenqi.XZQApplication;


/**
 * 广播基类Fragment
 * Created by Tse on 2016/9/11.
 */
public abstract class BroadcastFragment extends BaseFragment {

    private LocalBroadcastManager mLocalBroadcastManager;
    /**
     * 广播接受器
     */
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            dispatchLocalBroadcastReceive(context, intent);
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter filter = new IntentFilter();
        dispatchAction(filter);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, filter);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
        mLocalBroadcastManager = null;
        super.onDestroy();
    }

    private void dispatchLocalBroadcastReceive(Context context, Intent intent) {
        onLocalBroadcastReceive(context, intent);
    }

    private void dispatchAction(IntentFilter filter) {
        onAddAction(filter);
    }

    /**
     * 处理本地广播
     *
     * @param context 发起广播的context
     * @param intent  发起广播的意图
     */
    protected void onLocalBroadcastReceive(Context context, Intent intent) {

    }

    /**
     * 添加本地广播监听意图
     *
     * @param filter 意图过滤器
     */
    protected void onAddAction(IntentFilter filter) {

    }

    /**
     * 发送广播
     *
     * @param action 广播动作
     */
    protected final void sendLocalBroadcast(String action) {
        sendLocalBroadcast(new Intent(action));
    }

    /**
     * 发送广播
     *
     * @param intent 广播意图
     */
    protected final void sendLocalBroadcast(Intent intent) {
        if (mLocalBroadcastManager == null)
            XZQApplication.sendLocalBroadcast(intent);
        else
            mLocalBroadcastManager.sendBroadcast(intent);
    }

}
