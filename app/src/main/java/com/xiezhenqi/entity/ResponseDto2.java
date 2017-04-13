package com.xiezhenqi.entity;

/**
 * 请求返回实体
 *
 * @author Alex
 */
public class ResponseDto2<T> {
    private int error;// 错误code
    private T data;// 返回数据

    public boolean isSuccess() {
        return error == 0;
    }

    public int getCode() {
        return error;
    }

    public T getData() {
        return data;
    }
}
