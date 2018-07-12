package com.xiezhenqi.newmvp;

import com.google.gson.JsonSyntaxException;
import com.xiezhenqi.base.mvp.ILoadingListView;
import com.xiezhenqi.base.mvp.ILoadingView;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.utils.ToastUtils;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.ResourceObserver;

/**
 * 网络响应基础回调
 * Created by Wesley on 2018/7/9.
 */

public abstract class NetCallback<Entity> extends ResourceObserver<NetBean<Entity>> {

    private static final int FIRST_PAGE_INDEX = 1;
    public static final int CODE_JSON = -125;
    public static final int CODE_TIMEOUT = -126;
    public static final int CODE_NET_BREAK = -127;
    public static final int CODE_FAILED = -128;
    private static final String DEF_LOADING_MSG = "加载中...";
    private ILoadingListView mLoadingListView;
    private ILoadingView mLoadingView;
    private String mLoadingMessage;
    private int mPage = FIRST_PAGE_INDEX;

    public NetCallback() {
        this(null, DEF_LOADING_MSG);
    }

    public NetCallback(String loadingMessage) {
        this(null, loadingMessage);
    }

    public NetCallback(ILoadingView loadingView) {
        this(loadingView, DEF_LOADING_MSG);
    }

    public NetCallback(ILoadingView loadingView, String loadingMessage) {
        this(loadingView, loadingMessage, FIRST_PAGE_INDEX);
    }

    public NetCallback(ILoadingView loadingView, int page) {
        this(loadingView, DEF_LOADING_MSG, page);
    }

    public NetCallback(ILoadingView loadingView, String loadingMessage, int page) {
        this.mLoadingView = loadingView;
        this.mLoadingMessage = loadingMessage;
        this.mPage = page;
        if (loadingView instanceof ILoadingListView)
            this.mLoadingListView = (ILoadingListView) loadingView;
    }

    @Override
    protected void onStart() {
        //首次加载
        if (isFirstPage() && mLoadingView != null) {
            mLoadingView.onShowLoading();
        }
    }

    @Override
    public void onComplete() {
        if (isFirstPage() && mLoadingView != null) {
            mLoadingView.onHideLoading();
        }
    }

    @Override
    public void onNext(@NonNull NetBean<Entity> netResponse) {
        onComplete();
        if (netResponse.isOk()) {
            Entity entity = netResponse.getData();
            onSuccess(entity);
            boolean isEmpty = entity == null || entity instanceof List && ((List) entity).isEmpty();
            if (isEmpty) {
                if (mLoadingListView != null) {
                    if (isFirstPage()) {
                        mLoadingListView.onShowEmpty();
                    } else {
                        mLoadingListView.onShowLoadMoreEmpty();
                    }
                } else if (mLoadingView != null) {
                    mLoadingView.onShowEmpty();
                }
            }
            mPage++;
        } else {
            onError(new Exception());
        }
    }

    @Override
    public void onError(Throwable e) {
        //返回错误信息
        String error;
        int code;
        if (e instanceof JsonSyntaxException) {
            error = "数据解析异常";
            code = CODE_JSON;
        } else if (e instanceof SocketTimeoutException) {
            error = "请求超时";
            code = CODE_TIMEOUT;
        } else if (e instanceof UnknownHostException) {
            error = "网络已断开";
            code = CODE_NET_BREAK;
        } else {
            error = "请求失败";
            code = CODE_FAILED;
            LogUtils.debug("NetCallback", e.getMessage());
        }

        onError(error, code);

        if (mLoadingListView != null) {
            if (isFirstPage()) {
                mLoadingListView.onShowError(error);
            } else {
                mLoadingListView.onShowLoadMoreError(error);
            }
        } else if (mLoadingView != null) {
            mLoadingView.onShowError(error);
        }
    }

    protected boolean isFirstPage() {
        return mPage == FIRST_PAGE_INDEX;
    }

    protected abstract void onSuccess(Entity data);

    protected void onError(String error, int code) {
        ToastUtils.show(error);
    }

}
