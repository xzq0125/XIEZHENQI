package com.xiezhenqi.api;

import com.xiezhenqi.XZQApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * BaseGenerator
 * Created by sean on 2017/3/30.
 */

public abstract class BaseGenerator<Service> {

    private final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
    private Retrofit.Builder builder = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());
    private String url;

    protected BaseGenerator(String domain, boolean secure) {
        this.url = (secure ? "https://" : "http://") + domain;
    }

    public Service getService() {
        return createService();
    }

    protected abstract Class<Service> getServiceType();

    protected void addInterceptor(OkHttpClient.Builder clientBuilder) {
    }

    private Service createService() {
        httpBuilder.interceptors().clear();
        addInterceptor(httpBuilder);
        //Debug模式下打印请求log日志
        if (XZQApplication.isDebuggable()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message != null && message.startsWith("{") && message.endsWith("}")) {
                        Platform.get().log(Platform.INFO, message, null);//此处debug时可以打断点，copy json
                    } else {
                        Platform.get().log(Platform.INFO, message, null);
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpBuilder.addInterceptor(loggingInterceptor);
        }
        Retrofit retrofit = builder.baseUrl(url).client(httpBuilder
                .writeTimeout(15L, TimeUnit.SECONDS)
                .readTimeout(15L, TimeUnit.SECONDS)
                .connectTimeout(15L, TimeUnit.SECONDS)
                .build()).build();
        return retrofit.create(getServiceType());
    }
}
