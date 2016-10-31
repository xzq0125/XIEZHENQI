package com.xiezhenqi.request;

/**
 * 请求异常
 * Created by Alex on 2015/8/21.
 */
public class RequestException extends Throwable {

    private int code;
    private String msg;

    public RequestException(RequestError.Error error) {
        this.code = error.getCode();
        this.msg = error.getMessage();
    }

    public RequestException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }


}
