package com.xiezhenqi.request;

/**
 * 请求Task返回
 * Created by Alex on 2015/10/27.
 */
public class RequestResult {
    private boolean error = false;
    private Object result;
    private Object tag;
    private RequestError info;

    public RequestResult(RequestError info){
        setError(true);
        setInfo(info);
    }

    public RequestResult(Object result, Object tag){
        setResult(result);
        setTag(tag);
    }


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public RequestError getInfo() {
        return info;
    }

    public void setInfo(RequestError info) {
        this.info = info;
    }
}
