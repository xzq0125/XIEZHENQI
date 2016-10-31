package com.xiezhenqi.request;

import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求任务
 *
 * @author Mofer
 */
public class RequestTask extends
        AsyncTask<RequestHelper, Object, RequestResult> {

    private final WeakReference<OnTaskListener> mTaskListener;
    private final WeakReference<OnPreTaskListener> mPreTaskListener;
    private final WeakReference<OnIntegralTaskListener> mIntegralTaskListener;

    public RequestTask() {
        this(null);
    }

    public RequestTask(OnTaskListener listener) {
        if (listener == null) {
            mTaskListener = null;
            mPreTaskListener = null;
            mIntegralTaskListener = null;
        } else if (listener instanceof OnIntegralTaskListener) {
            mTaskListener = null;
            mPreTaskListener = null;
            mIntegralTaskListener = new WeakReference<>(
                    (OnIntegralTaskListener) listener);
        } else if (listener instanceof OnPreTaskListener) {
            mTaskListener = null;
            mPreTaskListener = new WeakReference<>(
                    (OnPreTaskListener) listener);
            mIntegralTaskListener = null;
        } else {
            mTaskListener = new WeakReference<>(listener);
            mPreTaskListener = null;
            mIntegralTaskListener = null;
        }

    }

    @Override
    protected void onPreExecute() {
        notifyPreExecute();
        super.onPreExecute();
    }

    @Override
    protected void onCancelled() {
        notifyCancelled();
        super.onCancelled();
        notifyFinished();
    }

    @Override
    protected RequestResult doInBackground(RequestHelper... params) {
        if (params == null || params.length == 0) {
            return null;
        }
        if (isCancelled()) {
            return null;
        }
        if (params.length == 1) {
            RequestHelper requestHelper = params[0];
            requestHelper.onReady();
            notifyStartExecute();
            // 优先使用缓存
            try {
                OkHttpClient client = new OkHttpClient();
                Request request;
                if (requestHelper.isPost()) {
                    Request.Builder builder = new Request.Builder().url(requestHelper.getUrl());
                    builder.post(requestHelper.getRequestBody());
                    requestHelper.onPostRequestBuild(builder);
                    request = builder.build();
                } else {
                    request = new Request.Builder().url(requestHelper.getUrl()).build();
                }
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Object result = requestHelper.doParse(response.body().byteStream());
                    notifyPrePost(result, requestHelper.getTag());
                    return new RequestResult(result, requestHelper.getTag());
                } else {
                    throw new RequestException(RequestError.Error.CODE_0102);
                }
            } catch (IOException e) {
                // 网络失败使用缓存
                RequestError error = RequestError.toError(
                        new RequestException(RequestError.Error.CODE_0102),
                        requestHelper.getTag());
                notifyPreFailure(error);
                return new RequestResult(error);
            } catch (RequestException e) {
                RequestError error = RequestError.toError(e, requestHelper.getTag());
                notifyPreFailure(error);
                return new RequestResult(error);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(RequestResult result) {
        super.onPostExecute(result);
        if (result != null) {
            if (result.isError()) {
                notifyFailed(result.getInfo());
            } else {
                notifySucceed(result.getResult(), result.getTag());
            }
        }
        notifyFinished();
    }

    private void notifyCancelled() {
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onCancelled();
        }
    }

    private void notifyPreFailure(RequestError info) {
        if (!isWeakReferenceNull(mPreTaskListener)) {
            mPreTaskListener.get().onPreFailure(info);
        }
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onPreFailure(info);
        }
    }

    private void notifyFailed(RequestError info) {
        if (!isWeakReferenceNull(mTaskListener)) {
            mTaskListener.get().onFailed(info);
        }
        if (!isWeakReferenceNull(mPreTaskListener)) {
            mPreTaskListener.get().onFailed(info);
        }
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onFailed(info);
        }

    }

    private void notifyFinished() {
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onFinished();
        }
    }

    private void notifyPreExecute() {
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onPreExecute();
        }
    }

    private void notifyStartExecute() {
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onStartExecute();
        }
    }

    private void notifyPrePost(Object dto, Object tag) {
        if (!isWeakReferenceNull(mPreTaskListener)) {
            mPreTaskListener.get().onPrePost(dto, tag);
        }
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onPrePost(dto, tag);
        }
    }

    private void notifySucceed(Object dto, Object tag) {
        if (!isWeakReferenceNull(mTaskListener)) {
            mTaskListener.get().onSucceed(dto, tag);
        }
        if (!isWeakReferenceNull(mPreTaskListener)) {
            mPreTaskListener.get().onSucceed(dto, tag);
        }
        if (!isWeakReferenceNull(mIntegralTaskListener)) {
            mIntegralTaskListener.get().onSucceed(dto, tag);
        }

    }

    private boolean isWeakReferenceNull(WeakReference<?> wp) {
        return wp == null || wp.get() == null;
    }

    /**
     * 请求监听
     *
     * @author Mofer
     */
    public interface OnIntegralTaskListener extends OnPreTaskListener {

        /**
         * 准备执行
         */
        void onPreExecute();

        /**
         * 开始执行
         * <p/>
         * 异步线程中
         */
        void onStartExecute();

        /**
         * 取消执行
         */
        void onCancelled();

        /**
         * 结束请求
         */
        void onFinished();
    }

    /**
     * 准备请求监听
     *
     * @author Mofer
     */
    public interface OnPreTaskListener extends OnTaskListener {


        /**
         * 处理失败结果
         * <p/>
         * 异步线程中
         */
        void onPreFailure(RequestError error);

        /**
         * 处理结果
         * <p/>
         * 异步线程中
         *
         * @param dto 请求结果
         * @param tag 请求标记
         */
        void onPrePost(Object dto, Object tag);
    }


    /**
     * 简单请求监听
     *
     * @author Alex
     */
    public interface OnTaskListener {

        /**
         * 请求失败
         *
         * @param error 错误信息
         */
        void onFailed(RequestError error);

        /**
         * 请求成功
         *
         * @param dto 请求结果
         * @param tag 请求标记
         */
        void onSucceed(Object dto, Object tag);

    }
}
