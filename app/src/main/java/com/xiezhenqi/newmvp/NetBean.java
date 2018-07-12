package com.xiezhenqi.newmvp;

/**
 * 请求返回实体
 *
 * @author Alex
 */
public class NetBean<T> {
    private int code;// 错误code
    private String msg;// 错误code
    private T data;// 返回数据

    public boolean isOk() {
        return code == 0;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
