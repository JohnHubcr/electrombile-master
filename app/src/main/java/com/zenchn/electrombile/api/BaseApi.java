package com.zenchn.electrombile.api;


import com.zenchn.electrombile.kit.RetrofitKit;

/**
 * 作    者：wangr on 2017/2/21 14:41
 * 描    述：接口实现基类
 * 修订记录：
 */
public class BaseApi {

    protected ApiStore mApiStore;

    public BaseApi() {
        mApiStore = RetrofitKit.getInstance().create(ApiStore.class);
    }

    public static ApiStore getLongTimeoutRetrofit(long timeout) {
        return RetrofitKit.getLongTimeoutRetrofit(timeout).create(ApiStore.class);
    }

}
