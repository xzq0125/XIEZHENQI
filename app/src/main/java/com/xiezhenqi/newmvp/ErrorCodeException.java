package com.xiezhenqi.newmvp;

/**
 * 数据层异常
 */

public class ErrorCodeException extends Exception {
    private final int errorCode;
    private Object data;

    public ErrorCodeException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCodeException(String message, int errorCode, Object data) {
        super(message);
        this.errorCode = errorCode;
        this.data = data;
    }

    public ErrorCodeException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCodeException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int errorCode() {
        return errorCode;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return super.toString() + ", errorCode=" + errorCode;
    }
}
