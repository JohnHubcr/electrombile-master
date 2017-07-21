package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.InsuranceApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.entity.InsurancePolicyInfo;
import com.zenchn.electrombile.presenter.InsuranceProtectPresenter;
import com.zenchn.electrombile.ui.view.InsuranceProtectView;

/**
 * 作    者：wangr on 2017/3/9 13:50
 * 描    述：
 * 修订记录：
 */
public class InsuranceProtectPresenterImpl implements InsuranceProtectPresenter, ApiCallback<InsurancePolicyInfo> {

    private InsuranceProtectView insuranceProtectView;

    public InsuranceProtectPresenterImpl(InsuranceProtectView insuranceProtectView) {
        this.insuranceProtectView = insuranceProtectView;
    }

    @Override
    public void onDestroy() {
        insuranceProtectView = null;
    }

    @Override
    public void getInsurancePolicyInfo(String accessToken, String serialNumber) {
        if (insuranceProtectView != null) {
            insuranceProtectView.showProgress();
            InsuranceApi
                    .getInstance()
                    .getUserVehiclePolicy(accessToken, serialNumber, this);
        }
    }

    @Override
    public void onSuccess(InsurancePolicyInfo insurancePolicyInfo) {
        if (insuranceProtectView != null) {
            insuranceProtectView.hideProgress();
            insuranceProtectView.showInsurancePolicyInfo(insurancePolicyInfo);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (insuranceProtectView != null) {
            insuranceProtectView.hideProgress();
            insuranceProtectView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (insuranceProtectView != null) {
            insuranceProtectView.hideProgress();
            insuranceProtectView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (insuranceProtectView != null) {
            insuranceProtectView.hideProgress();
            insuranceProtectView.onGrantRefuse();
        }
    }
}
