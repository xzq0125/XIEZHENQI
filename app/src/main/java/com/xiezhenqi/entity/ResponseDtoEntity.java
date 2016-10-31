package com.xiezhenqi.entity;

/**
 * 请求返回实体
 *
 * @author Alex
 */
public class ResponseDtoEntity<T> extends ResponseDto {

    public T showapi_res_body;// 返回数据

    @Override
    public Object getObject() {
        return showapi_res_body;
    }
}
