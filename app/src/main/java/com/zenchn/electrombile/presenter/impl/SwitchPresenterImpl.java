package com.zenchn.electrombile.presenter.impl;

import android.support.annotation.NonNull;

import com.zenchn.electrombile.api.LoginApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.ClientInfo;
import com.zenchn.electrombile.presenter.SwitchPresenter;
import com.zenchn.electrombile.ui.view.SwitchView;

/**
 * 作    者：wangr on 2017/2/24 9:51
 * 描    述：
 * 修订记录：
 */
public class SwitchPresenterImpl implements SwitchPresenter, ApiCallback<LoginInfo> {

    private SwitchView switchView;

    public SwitchPresenterImpl(SwitchView switchView) {
        this.switchView = switchView;
    }

    @Override
    public void getUserInfo(@NonNull ClientInfo clientInfo, @NonNull LoginInfo loginInfo) {
        if (switchView != null) {
            switchView.showAnimation();
            LoginApi
                    .getInstance()
                    .getUser(clientInfo, loginInfo, this);
        }
    }

    @Override
    public void onDestroy() {
        switchView = null;
    }

    @Override
    public void onSuccess(LoginInfo loginInfo) {
        if (switchView != null) {
            switchView.stopAnimation();
            switchView.onGetUserSuccess(loginInfo);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (switchView != null) {
            switchView.stopAnimation();
            switchView.onGetUserFailure(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (switchView != null) {
            switchView.stopAnimation();
            switchView.onGetUserFailure(null);
        }
    }

    @Override
    public void onGrantRefuse() {
        if (switchView != null) {
            switchView.hideProgress();
            switchView.onGrantRefuse();
        }
    }

}
