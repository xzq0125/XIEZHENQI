package com.xiezhenqi.api;

import com.xiezhenqi.entity.DataEntity;
import com.xiezhenqi.entity.ResponseDto2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * LiveService
 * Created by sean on 2017/4/13.
 */

public interface LiveService {

    @GET("/lapi/live/thirdPart/getPlay/{room_id}")
    @Headers({"aid: pcclient"})
    Call<ResponseDto2<DataEntity>> getPhoneLiveVideoInfo(@Header("auth") String auth,
                                                         @Header("time") String time,
                                                         @Path("room_id") String room_id,
                                                         @Query("rate") int rate);

}
