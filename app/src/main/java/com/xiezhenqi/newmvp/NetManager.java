package com.xiezhenqi.newmvp;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xiezhenqi.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *  创建者:   ddz
 *  创建时间:  2017/12/23 15:16
 *  描述：    TODO
 */
public class NetManager {

    private static Retrofit retrofit;

    static {

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

        //Debug模式下打印请求log日志
        if (BuildConfig.DEBUG) {
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

        OkHttpClient client = httpBuilder
                .readTimeout(15L, TimeUnit.SECONDS)
                .writeTimeout(15L, TimeUnit.SECONDS)
                .connectTimeout(15L, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://api.vipbendi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static Retrofit retrofit() {
        return retrofit;
    }

}
