package com.zenchn.electrombile.alipay;

import com.zenchn.electrombile.base.BaseView;

public interface AliPayView extends BaseView {

    void onPaySuccess();

    void getPayResult();

    void onPayFailure();

}
