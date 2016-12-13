package com.xiezhenqi.widget.pulldownrefresh;

/**
 * HeaderHelper
 * Created by sean on 2016/12/13.
 */

public class HeaderHelper {


    /**
     * 初始化一个简单的下拉刷新头
     *
     * @param refreshLayout     下拉刷新控件
     * @param onRefreshListener 刷新回调监听器
     */
    public static void initSampleHeader(RefreshLayout refreshLayout,
                                        RefreshLayout.OnRefreshListener onRefreshListener) {
        RefreshLayoutHeader header = new RefreshLayoutHeader(refreshLayout.getContext());
        refreshLayout.addOnRefreshListener(header);
        refreshLayout.addOnRefreshListener(onRefreshListener);
        refreshLayout.setRefreshHeader(header);
    }
}
