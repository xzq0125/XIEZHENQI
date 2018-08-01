package com.xiezhenqi.newmvp;

import io.reactivex.Observable;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

/**
 * ApiService
 * Created by Wesley on 2018/7/10.
 */
public interface ApiService {

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
    @HTTP(method = "GET", path = "/article/list/{page}/json", hasBody = false)
    Observable<NetBean<HomePageBean>> getWangAndroidHomePage(
            @Path("page") int page);
}
