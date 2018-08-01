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
    private int count;//服务端定义数组返回形式总页数

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

    public boolean hasNextPage(int mPage) {
        return mPage < count;
    }
}
