package com.xiezhenqi.entity;

import java.util.List;

/**
 * 请求返回集合
 *
 * @author Alex
 */
public class ResponseDtoList<T> extends ResponseDto {
    public List<T> object;// 返回数据

    @Override
    public Object getObject() {
        return object;
    }
}
