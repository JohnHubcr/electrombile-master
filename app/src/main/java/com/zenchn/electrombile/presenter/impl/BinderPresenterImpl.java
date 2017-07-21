package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.BinderApi;
import com.zenchn.electrombile.api.callback.BindCallback;
import com.zenchn.electrombile.presenter.BinderPresenter;
import com.zenchn.electrombile.ui.view.BinderView;

/**
 * 作    者：wangr on 2017/2/22 14:17
 * 描    述：
 * 修订记录：
 */
public class BinderPresenterImpl implements BinderPresenter, BindCallback {

    private BinderView binderView;

    public BinderPresenterImpl(BinderView binderView) {
        this.binderView = binderView;
    }

    @Override
    public void getBindStatus(String accessToken, String serialNumber) {
        if (binderView != null) {
            binderView.showProgress();
            BinderApi
                    .getInstance()
                    .getBindStatus(accessToken, serialNumber, this);
        }
    }

    @Override
    public void onEquipmentWithoutBind(String serialNumber) {
        if (binderView != null) {
            binderView.bindEquipment(serialNumber);
        }
    }

    @Override
    public void onEquipmentHasBind(String serialNumber, String mobilePhone) {
        if (binderView != null) {
            binderView.hideProgress();
            binderView.askValidate(serialNumber, mobilePhone);
        }
    }

    @Override
    public void bindEquipment(String accessToken, String encryptSerialNumber) {
        if (binderView != null) {
            binderView.showProgress();
            BinderApi
                    .getInstance()
                    .bindEquipment(accessToken, encryptSerialNumber, this);
        }
    }

    @Override
    public void onBindSuccess() {
        if (binderView != null) {
            binderView.hideProgress();
            binderView.onBindResult(true, null);
        }
    }

    @Override
    public void onBindFailure() {
        if (binderView != null) {
            binderView.hideProgress();
            binderView.onBindResult(false, null);
        }
    }

    @Override
    public void onBindFailure(String err_msg) {
        if (binderView != null) {
            binderView.hideProgress();
            binderView.onBindResult(false, err_msg);
        }
    }

    @Override
    public void onDestroy() {
        binderView = null;
    }

    @Override
    public void onGrantRefuse() {
        if (binderView != null) {
            binderView.hideProgress();
            binderView.onGrantRefuse();
        }
    }

}
