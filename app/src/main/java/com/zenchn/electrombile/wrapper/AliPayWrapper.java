package com.zenchn.electrombile.wrapper;

import android.os.Handler;
import android.os.Looper;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.InsuranceApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.wrapper.callback.AliPayCallback;

/**
 * 作    者：wangr on 2017/3/7 17:13
 * 描    述：查询阿里支付结果的封装
 * 修订记录：
 */
public class AliPayWrapper implements ApiCallback<Integer> {

    private Handler mHandler;
    private int errorCount;
    private Runnable runnable;
    private AliPayCallback callback;

    private AliPayWrapper() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static AliPayWrapper getInstance() {
        return new AliPayWrapper();
    }

    /**
     * @param accessToken
     * @param outTradeNo
     */
    public void getPayResult(final String accessToken, final String outTradeNo, AliPayCallback callback) {
        this.callback = callback;
        runnable = new Runnable() {

            @Override
            public void run() {
                InsuranceApi
                        .getInstance()
                        .getPayStatus(accessToken, outTradeNo, AliPayWrapper.this);
            }
        };
        mHandler.post(runnable);
    }


    @Override
    public void onSuccess(Integer data) {
        if (BuildConf.AliPay.PAY_STATUS_OK == data) {
            if (callback != null)
                callback.onPaySuccess();
        } else {
            onFailure();
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        onFailure();
    }

    @Override
    public void onFailure() {
        errorCount++;
        if (errorCount < 10)
            mHandler.postDelayed(runnable, 200);
        else {
            if (callback != null)
                callback.onPayFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (callback != null)
            callback.onGrantRefuse();
    }
}
