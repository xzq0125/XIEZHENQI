package com.xiezhenqi;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xiezhenqi.utils.Constant;
import com.xiezhenqi.utils.StringUtils;

import java.io.File;

/**
 * XZQApplication
 * Created by Tse on 2016/9/11.
 */
public class XZQApplication extends Application {

    private static Context mContext;
    private static LocalBroadcastManager mLocalBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Fresco.initialize(mContext);
        File file = new File(Constant.lyricPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        // 单例应用内广播
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter filter = new IntentFilter();
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, filter);
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 应用内部广播接收器
     */
    private final BroadcastReceiver mLocalReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    /**
     * 发送本地广播
     *
     * @param action 要发送的广播动作
     */
    public static void sendLocalBroadcast(String action) {
        if (!StringUtils.isNullOrEmpty(action))
            sendLocalBroadcast(new Intent(action));
    }

    /**
     * 发送本地广播
     *
     * @param intent Intent
     */
    public static void sendLocalBroadcast(Intent intent) {
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void onTerminate() {
        // 注销应用内广播
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        super.onTerminate();
    }
}
