package com.zenchn.electrombile.alipay;


import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.zenchn.electrombile.wrapper.AliPayWrapper;
import com.zenchn.electrombile.wrapper.callback.AliPayCallback;
import com.zenchn.mlibrary.base.UiHandler;

import java.util.Map;

public class AliPayPresenterImpl implements AliPayPresenter, AliPayCallback {

    private AliPayView alipayView;

    public AliPayPresenterImpl(AliPayView alipayView) {
        this.alipayView = alipayView;
    }

    @Override
    public void onDestroy() {
        alipayView = null;
    }

    @Override
    public void payV2(final PayTask payTask, final String orderInfo, final UiHandler mHandler) {
        new Thread(new Runnable() {//将支付过程放在子线程处理
            @Override
            public void run() {
                Map<String, String> resultMap = payTask.payV2(orderInfo, true);
                PayResult payResult = new PayResult(resultMap);
                String resultStatus = payResult.getResultStatus();
                final boolean success = TextUtils.equals(resultStatus, "9000");
                mHandler.post(new Runnable() {//将支付结果带回到主线程处理
                    @Override
                    public void run() {
                        if (success)
                            alipayView.getPayResult();
                        else
                            alipayView.onPayFailure();
                    }
                });
            }
        }).start();
    }


    /**
     * 获取支付结果
     */
    @Override
    public void getPayResult(String accessToken, String outTradeNo) {
        if (alipayView != null) {
            alipayView.showProgress();
            AliPayWrapper
                    .getInstance()
                    .getPayResult(accessToken, outTradeNo, this);
        }
    }

    @Override
    public void onPaySuccess() {
        if (alipayView != null) {
            alipayView.hideProgress();
            alipayView.onPaySuccess();
        }
    }

    @Override
    public void onPayFailure() {
        if (alipayView != null) {
            alipayView.hideProgress();
            alipayView.onPayFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (alipayView != null) {
            alipayView.hideProgress();
            alipayView.onGrantRefuse();
        }
    }
}
