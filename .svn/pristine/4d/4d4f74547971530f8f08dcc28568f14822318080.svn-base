package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.InsuranceApi;
import com.zenchn.electrombile.api.callback.AliPaySignCallback;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.entity.InsuranceProductInfo;
import com.zenchn.electrombile.presenter.InsurancePayPresenter;
import com.zenchn.electrombile.ui.view.InsurancePayView;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/7 15:16
 * 描    述：
 * 修订记录：
 */
public class InsurancePayPresenterImpl implements InsurancePayPresenter, ApiCallback<List<InsuranceProductInfo>>, AliPaySignCallback {

    private InsurancePayView insurancePayView;

    public InsurancePayPresenterImpl(InsurancePayView insurancePayView) {
        this.insurancePayView = insurancePayView;
    }

    @Override
    public void getProductInfo(String accessToken) {
        if (insurancePayView != null) {
            insurancePayView.showProgress();
            InsuranceApi
                    .getInstance()
                    .getProductInfo(accessToken, this);

        }
    }

    @Override
    public void createIndent(String accessToken, String productCombinationId, String serialNumber) {
        if (insurancePayView != null) {
            insurancePayView.showProgress();
            InsuranceApi
                    .getInstance()
                    .createIndent(accessToken, productCombinationId, serialNumber, this);

        }
    }

    @Override
    public void onDestroy() {
        insurancePayView = null;
    }

    @Override
    public void onSuccess(List<InsuranceProductInfo> data) {
        if (insurancePayView != null) {
            insurancePayView.hideProgress();
            insurancePayView.showInsuranceProductList(data);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (insurancePayView != null) {
            insurancePayView.hideProgress();
            insurancePayView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (insurancePayView != null) {
            insurancePayView.hideProgress();
            insurancePayView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (insurancePayView != null) {
            insurancePayView.hideProgress();
            insurancePayView.onGrantRefuse();
        }
    }

    @Override
    public void onSignSuccess(String sign, String outTradeNo) {
        if (insurancePayView != null) {
            insurancePayView.hideProgress();
            insurancePayView.onCreateIndentSuccess(sign, outTradeNo);
        }
    }

    @Override
    public void onSignFailure() {
        if (insurancePayView != null) {
            insurancePayView.hideProgress();
            insurancePayView.onCreateIndentFailure();
        }
    }
}
