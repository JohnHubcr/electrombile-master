package com.zenchn.electrombile.api.callback;

/**
 * 作    者：wangr on 2017/2/21 16:28
 * 描    述：Api基本的回调
 * 修订记录：
 */
public interface ApiCallback<T> extends GrantCallback {

    // 请求数据成功
    void onSuccess(T data);

    // 请求数据错误
    void onResponseError(String err_msg);

    // 网络请求失败
    void onFailure();

}
