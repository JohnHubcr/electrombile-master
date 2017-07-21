package com.zenchn.electrombile.api.callback;

/**
 * 作    者：wangr on 2017/3/2 18:44
 * 描    述：Api基本的回调（适用于分页加载）
 * 修订记录：
 */
public interface ApiListCallback<T> extends GrantCallback {

    // 请求数据成功
    void onSuccess(T data, int pageNumber, int totalPages);

    // 请求数据错误
    void onResponseError(String err_msg);

    // 网络请求失败
    void onFailure();
}
