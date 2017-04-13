package com.xiezhenqi.api;

import com.xiezhenqi.entity.ResponseDto2;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 基础回调
 * Created by Alex on 2017/1/20.
 */
@SuppressWarnings("all")
public class ResponseCallback<T> implements Callback<ResponseDto2<T>> {
    public static final int CODE_DEFAULT = -100;// 请求失败
    public static final int CODE_80 = 80;// 用户口令失效
    private final OnResponseListener<T> mListener;
    private final WeakReference<OnResponseListener<T>> mWeakListener;
    private Object tag;
    private Map<Call<?>, Object> callTags = new HashMap<>();

    public ResponseCallback(OnResponseListener<T> listener) {
        this(listener, null);
    }

    public ResponseCallback(OnResponseListener<T> listener, OnResponseListener<T> weakListener) {
        mListener = listener;
        if (weakListener == null)
            mWeakListener = null;
        else
            mWeakListener = new WeakReference<>(weakListener);
    }

    @Override
    public void onResponse(Call<ResponseDto2<T>> call, Response<ResponseDto2<T>> response) {
        if (response.isSuccessful()) {
            if (response.body() == null) {
                notifyFailed(call, CODE_DEFAULT, "no data");
            } else {
                if (response.body().isSuccess()) {
                    notifySucceed(call, response.body().getData());
                } else {
                    notifyFailed(call, response.body().getCode(), null);
                }
            }
        } else {
            notifyFailed(call, response.code(), response.message());
        }
    }

    @Override
    public void onFailure(Call<ResponseDto2<T>> call, Throwable t) {
        notifyFailed(call, CODE_DEFAULT, t.getMessage());
    }


    private boolean isWeakReferenceNull(WeakReference<?> wp) {
        return wp == null || wp.get() == null;
    }

    private void notifySucceed(Call<ResponseDto2<T>> call, T result) {
        if (mListener != null)
            mListener.onSucceed(call, this, result);
        if (!isWeakReferenceNull(mWeakListener)) {
            mWeakListener.get().onSucceed(call, this, result);
        }
    }

    private void notifyFailed(Call<ResponseDto2<T>> call, int code, String message) {
        if (mListener != null)
            mListener.onFailed(call, this, code, message);
        if (!isWeakReferenceNull(mWeakListener)) {
            mWeakListener.get().onFailed(call, this, code, message);
        }
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public void putCallTag(Call<?> kay, Object tag) {
        callTags.put(kay, tag);
    }

    public Object removeCallTag(Call<?> kay) {
        return callTags.remove(kay);
    }

    /**
     * 响应监听
     *
     * @author Alex
     */
    public interface OnResponseListener<T> {

        void onSucceed(Call<ResponseDto2<T>> call, ResponseCallback<T> callback, T result);

        void onFailed(Call<ResponseDto2<T>> call, ResponseCallback<T> callback,
                      int code, String message);
    }
}
