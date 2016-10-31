package com.xiezhenqi.entity;

/**
 * 请求返回
 */
public abstract class ResponseDto {

    public int showapi_res_code;// 返回代码
    public String showapi_res_error;// 错误信息

    public boolean isSuccessful() {
        return showapi_res_code == 0;
    }

    public abstract Object getObject();
}
