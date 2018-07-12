package com.xiezhenqi.newmvp;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * ApiService
 * Created by Wesley on 2018/7/10.
 */
public interface ApiService {

    @FormUrlEncoded
    @Headers("moduleName:testshop")
    @POST("/home/index/shopList")
    Observable<NetBean<ShopBean>> getShopList(
            @Field("page") int page,
            @Field("type") int type,
            @Field("city_id") int city_id,
            @Field("order") String number_desc);
}
