package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.LogoutApi;
import com.zenchn.electrombile.api.callback.LogoutCallback;
import com.zenchn.electrombile.presenter.SettingPresenter;
import com.zenchn.electrombile.ui.view.SettingView;

/**
 * 作    者：wangr on 2017/2/22 14:49
 * 描    述：
 * 修订记录：
 */
public class SettingPresenterImpl implements SettingPresenter, LogoutCallback {

    private SettingView settingView;

    public SettingPresenterImpl(SettingView settingView) {
        this.settingView = settingView;
    }

    @Override
    public void logout(String loginName, String accessToken) {
        if (settingView != null) {
            settingView.showProgress();
            LogoutApi
                    .getInstance()
                    .Logout(loginName, accessToken, this);
        }
    }

    @Override
    public void onLogoutSuccess() {
        if (settingView != null) {
            settingView.hideProgress();
            settingView.clearLoginCache();
            settingView.navigateToLogin();
        }
    }

    @Override
    public void onLogoutFailure() {
        if (settingView != null) {
            settingView.hideProgress();
            settingView.clearLoginCache();
            settingView.navigateToLogin();
        }
    }

    @Override
    public void onDestroy() {
        settingView = null;
    }
}
