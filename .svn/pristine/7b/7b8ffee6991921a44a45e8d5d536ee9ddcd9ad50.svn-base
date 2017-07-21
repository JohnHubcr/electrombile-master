package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.BinderApi;
import com.zenchn.electrombile.api.ValidateApi;
import com.zenchn.electrombile.api.callback.BindCallback;
import com.zenchn.electrombile.api.callback.ValidateCallback;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.presenter.ValidatePresenter;
import com.zenchn.electrombile.ui.view.ValidateView;

/**
 * 作    者：wangr on 2017/2/22 16:33
 * 描    述：
 * 修订记录：
 */
public class ValidatePresenterImpl implements ValidatePresenter, ValidateCallback, BindCallback {

    private ValidateView validateView;

    public ValidatePresenterImpl(ValidateView validateView) {
        this.validateView = validateView;
    }

    @Override
    public void onDestroy() {
        validateView = null;
    }

    /**
     * 验证码发送
     * --------------------------------------------------------------------
     */

    @Override
    public void getAuthCode(String mobilePhone, String type) {
        if (validateView != null) {
            validateView.showProgress();
            ValidateApi
                    .getInstance()
                    .getAuthCode(mobilePhone, type, this);
        }
    }

    @Override
    public void onGetAuthCodeSuccess() {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.onGetAuthCodeSuccess();
        }
    }

    @Override
    public void onGetAuthCodeError() {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.onShowGetAuthCodeError();
            validateView.onGetAuthCodeError();
        }
    }

    @Override
    public void onGetAuthCodeResponseError(String msg) {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.showMessage(msg);
            validateView.onGetAuthCodeError();
        }
    }

    /**
     * 账号注册
     * --------------------------------------------------------------------
     */

    @Override
    public void register(String mobilePhone, String encryptPassword, String authCode) {
        if (validateView != null) {
            validateView.showProgress();
            ValidateApi
                    .getInstance()
                    .register(mobilePhone, encryptPassword, authCode, this);
        }
    }

    @Override
    public void onRegisterSuccess(UserInfo userInfo) {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.navigateToHome(userInfo);
        }
    }

    @Override
    public void onRegisterError() {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.navigateToRegister();
        }
    }

    @Override
    public void onRegisterResponseError(String msg) {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.onResponseError(msg);
        }
    }

    /**
     * 账号变更
     * --------------------------------------------------------------------
     */

    @Override
    public void modifyAccount(String accessToken, String newMobilePhone, String encryptPassword, String authCode) {
        if (validateView != null) {
            validateView.showProgress();
            ValidateApi
                    .getInstance()
                    .modifyAccount(accessToken, newMobilePhone, encryptPassword, authCode, this);
        }
    }


    @Override
    public void onModifyAccountSuccess(String mobilePhone) {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.navigateToLogin(mobilePhone);
        }
    }

    @Override
    public void onModifyAccountError() {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.onBack();
        }
    }

    @Override
    public void onModifyAccountResponseError(String msg) {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.onResponseError(msg);
        }
    }

    /**
     * 绑定设备
     * --------------------------------------------------------------------
     */

    @Override
    public void bindEquipment(String accessToken, String encryptSerialNumber, String mobilePhone, String authCode) {
        if (validateView != null) {
            validateView.showProgress();
            BinderApi
                    .getInstance()
                    .bindEquipment(accessToken, encryptSerialNumber, mobilePhone, authCode, this);
        }
    }

    @Override
    public void onEquipmentHasBind(String serialNumber, String mobilePhone) {
    }

    @Override
    public void onEquipmentWithoutBind(String serialNumber) {
    }

    @Override
    public void onBindSuccess() {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.backToBind(true, null);
        }
    }

    @Override
    public void onBindFailure() {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.backToBind(false, null);
        }
    }

    @Override
    public void onBindFailure(String err_msg) {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.backToBind(false, err_msg);
        }
    }

    /**
     * 检验验证码
     * --------------------------------------------------------------------
     */


    @Override
    public void verifyAuthCode(String mobilePhone, String authCode) {
        if (validateView != null) {
            validateView.showProgress();
            ValidateApi
                    .getInstance()
                    .verifyAuthCode(mobilePhone, authCode, this);
        }
    }

    @Override
    public void onCheckCodeResult(boolean result) {
        if (validateView != null) {
            validateView.hideProgress();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (validateView != null) {
            validateView.hideProgress();
            validateView.onGrantRefuse();
        }
    }

}
