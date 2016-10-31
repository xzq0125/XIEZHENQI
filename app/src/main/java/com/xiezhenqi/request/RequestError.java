package com.xiezhenqi.request;

/**
 * 请求错误
 * Created by Alex on 2015/8/22.
 */
public class RequestError {
    public enum Error {
        CODE_0105(-105, "服务器返回错误数据"),
        CODE_0104(-104, "服务器返回数据解析出错"),
        CODE_0103(-103, "服务器返回数据读取出错"),
        CODE_0102(-102, "服务器访问异常"),
        CODE_0101(-101, "请求链接创建失败"),

        CODE_10(10, "参数错误"),
        CODE_80(80, "用户口令失效");

        private int code;
        private String msg;

        Error(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            if (code == -102)
                return "网络连接失败";
            return msg;
        }
    }

    private int code;
    private String msg;
    private Object tag;

    private RequestError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * Exception 转 Error
     *
     * @param exception 异常
     * @param tag       标记
     * @return 错误
     */
    public static RequestError toError(RequestException exception, Object tag) {

        RequestError error = new RequestError(exception.getCode(), exception.getMessage());
        if (tag != null)
            error.setTag(tag);
        return error;
    }

    /**
     * Exception 转 Error
     *
     * @param exception 异常
     * @return 错误
     */
    public static RequestError toError(RequestException exception) {
        return toError(exception, null);
    }
}
