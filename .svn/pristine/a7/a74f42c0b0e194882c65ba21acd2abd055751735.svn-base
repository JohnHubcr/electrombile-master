package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.InsuranceApi;
import com.zenchn.electrombile.api.callback.SubmitCallback;
import com.zenchn.electrombile.entity.InsuranceActivateInfo;
import com.zenchn.electrombile.presenter.InsuranceActivatePresenter;
import com.zenchn.electrombile.ui.view.InsuranceActivateView;

/**
 * 作    者：wangr on 2017/3/8 11:51
 * 描    述：
 * 修订记录：
 */
public class InsuranceActivatePresenterImpl implements InsuranceActivatePresenter, SubmitCallback {

    private InsuranceActivateView insuranceActivateView;

    public InsuranceActivatePresenterImpl(InsuranceActivateView insuranceActivateView) {
        this.insuranceActivateView = insuranceActivateView;
    }

    @Override
    public void onDestroy() {
        insuranceActivateView = null;
    }

    @Override
    public void submitApplyProtectionInfo(String accessToken, String serialNumber, InsuranceActivateInfo insuranceActivateInfo) {
        if (insuranceActivateView != null) {
            insuranceActivateView.showProgress();
            InsuranceApi
                    .getInstance()
                    .submitInsuranceActivate(accessToken, serialNumber, insuranceActivateInfo, this);
        }
    }


    @Override
    public void onSuccess(boolean result) {
        if (insuranceActivateView != null) {
            insuranceActivateView.hideProgress();
            insuranceActivateView.onSubmitCompleted(result);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (insuranceActivateView != null) {
            insuranceActivateView.hideProgress();
            insuranceActivateView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (insuranceActivateView != null) {
            insuranceActivateView.hideProgress();
            insuranceActivateView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (insuranceActivateView != null) {
            insuranceActivateView.hideProgress();
            insuranceActivateView.onGrantRefuse();
        }
    }
}
