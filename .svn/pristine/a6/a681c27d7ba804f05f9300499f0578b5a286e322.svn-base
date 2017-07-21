package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.ResetPwdApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.presenter.ResetPwdPresenter;
import com.zenchn.electrombile.ui.view.ResetPwdView;
import com.zenchn.mlibrary.utils.EncryptUtils;

/**
 * 作    者：wangr on 2017/2/22 17:20
 * 描    述：
 * 修订记录：
 */
public class ResetPwdPresenterImpl implements ResetPwdPresenter, ApiCallback<String> {

    private ResetPwdView resetPwdView;

    public ResetPwdPresenterImpl(ResetPwdView resetPwdView) {
        this.resetPwdView = resetPwdView;
    }

    @Override
    public void onDestroy() {
        resetPwdView = null;
    }

    @Override
    public void onSuccess(String data) {
        if (resetPwdView != null) {
            resetPwdView.hideProgress();
            resetPwdView.onRestPwdSuccess();
        }
    }

    @Override
    public void onResponseError(String msg) {
        if (resetPwdView != null) {
            resetPwdView.onResponseError(msg);
            resetPwdView.hideProgress();
        }
    }

    @Override
    public void onFailure() {
        if (resetPwdView != null) {
            resetPwdView.hideProgress();
            resetPwdView.onFailure();
        }
    }

    @Override
    public void resetPwd(String mobilePhone, String newPassword, String authCode) {
        if (resetPwdView != null) {
            resetPwdView.showProgress();
            ResetPwdApi
                    .getInstance()
                    .resetPwd(mobilePhone, EncryptUtils.MD5Encrypt(newPassword), authCode, this);
        }
    }

    @Override
    public void onGrantRefuse() {
        if (resetPwdView != null) {
            resetPwdView.hideProgress();
            resetPwdView.onGrantRefuse();
        }
    }
}
