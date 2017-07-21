package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.RevPwdApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.presenter.RevPwdPresenter;
import com.zenchn.electrombile.ui.view.RevPwdView;
import com.zenchn.mlibrary.utils.EncryptUtils;

/**
 * 作    者：wangr on 2017/2/22 16:06
 * 描    述：
 * 修订记录：
 */
public class RevPwdPresenterImpl implements RevPwdPresenter, ApiCallback<String> {

    private RevPwdView revPwdView;

    public RevPwdPresenterImpl(RevPwdView revPwdView) {
        this.revPwdView = revPwdView;
    }

    @Override
    public void revPwd(String accessToken, String password, String oldPwd) {
        if (revPwdView != null) {
            revPwdView.showProgress();
            RevPwdApi
                    .getInstance()
                    .revPwd(accessToken, EncryptUtils.MD5Encrypt(password), EncryptUtils.MD5Encrypt(oldPwd), this);
        }
    }

    @Override
    public void onDestroy() {
        revPwdView = null;
    }

    @Override
    public void onSuccess(String encryptNewPassword) {
        if (revPwdView != null) {
            revPwdView.hideProgress();
            revPwdView.modifySuccess(encryptNewPassword);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (revPwdView != null) {
            revPwdView.hideProgress();
            revPwdView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (revPwdView != null) {
            revPwdView.hideProgress();
            revPwdView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (revPwdView != null) {
            revPwdView.hideProgress();
            revPwdView.onGrantRefuse();
        }
    }

}
