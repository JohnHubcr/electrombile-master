package com.zenchn.electrombile.entity;

/**
 * 作    者：wangr on 2017/2/22 20:40
 * 描    述：Api接口返回数据的描述类
 * 修订记录：
 */

public class ApiResult<T> {

    public Boolean success;
    public int statusCode;
    public String message;
    public T data;

    public ApiResult(Boolean success) {
        this.success = success;
    }

    public ApiResult(T data) {
        this.data = data;
    }

    public ApiResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ApiResult(Boolean success, int statusCode, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ApiResult(Boolean success, int statusCode, String message, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "success=" + success +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
