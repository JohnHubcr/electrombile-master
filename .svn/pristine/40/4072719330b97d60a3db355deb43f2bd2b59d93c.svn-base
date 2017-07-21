package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.LoginApi;
import com.zenchn.electrombile.api.callback.LoginCallback;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.presenter.MainPresenter;
import com.zenchn.electrombile.ui.view.MainView;

/**
 * 作    者：wangr on 2017/2/22 14:17
 * 描    述：
 * 修订记录：
 */

public class MainPresenterImpl implements MainPresenter, LoginCallback {

    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void login(UserInfo userInfo) {
        if (mainView != null) {
            LoginApi
                    .getInstance()
                    .getToken(userInfo, this);
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo, LoginInfo loginInfo) {
        if (mainView != null) {
            mainView.stopAnimation();
            mainView.onAutoLoginSuccess(userInfo, loginInfo);
        } else {
            navigateToLogin();
        }
    }

    @Override
    public void onLoginFailure() {
        navigateToLogin();
    }

    @Override
    public void onLoginResponseError(String msg) {
        navigateToLogin();
    }

    public void navigateToLogin() {
        if (mainView != null) {
            mainView.stopAnimation();
            mainView.onAutoLoginFail();
        }
    }

}
