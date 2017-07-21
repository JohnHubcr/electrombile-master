package com.zenchn.electrombile.alipay;

import com.alipay.sdk.app.PayTask;
import com.zenchn.electrombile.base.BasePresenter;
import com.zenchn.mlibrary.base.UiHandler;

public interface AliPayPresenter extends BasePresenter {

    /**
     * 开启支付宝支付
     *  @param payTask
     * @param orderInfo
     * @param mHandler
     */
    void payV2(PayTask payTask, String orderInfo, UiHandler mHandler);

    /**
     * 获取只哦服状态
     *
     * @param accessToken
     * @param outTradeNo
     */
    void getPayResult(String accessToken, String outTradeNo);

}
