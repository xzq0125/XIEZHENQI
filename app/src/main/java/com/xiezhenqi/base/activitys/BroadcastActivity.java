package com.xiezhenqi.base.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.xiezhenqi.XZQApplication;
import com.xiezhenqi.business.action.BroadcastAction;

/**
 * 广播基类Activity
 * Created by Tse on 2016/10/27.
 */
public abstract class BroadcastActivity extends BaseActivity {

    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        dispatchAction(filter);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, filter);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
        mLocalBroadcastManager = null;
        super.onDestroy();
    }

    private void dispatchAction(IntentFilter filter) {
        filter.addAction(BroadcastAction.ACTION_BAR_CLICK);
        filter.addAction(BroadcastAction.ACTION_MUSIC_START);
        filter.addAction(BroadcastAction.ACTION_MUSIC_PAUSE);
        onAddAction(filter);
    }

    /**
     * 添加本地广播监听意图
     *
     * @param filter 意图过滤器
     */
    protected void onAddAction(IntentFilter filter) {

    }

    /**
     * 广播接受器
     */
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            dispatchLocalBroadcastReceive(context, intent);
        }
    };

    private void dispatchLocalBroadcastReceive(Context context, Intent intent) {
        onLocalBroadcastReceive(context, intent);
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
        if (mLocalBroadcastManager == null) {
            XZQApplication.sendLocalBroadcast(intent);
            return;
        }
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}
