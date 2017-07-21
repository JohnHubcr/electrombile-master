package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.api.LoginApi;
import com.zenchn.electrombile.api.callback.LoginCallback;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.presenter.LoginPresenter;
import com.zenchn.electrombile.ui.view.LoginView;
import com.zenchn.mlibrary.base.UiHandler;

/**
 * 作    者：wangr on 2017/2/22 14:17
 * 描    述：
 * 修订记录：
 */
public class LoginPresenterImpl implements LoginPresenter, LoginCallback {

    private LoginView loginView;
    private boolean isExit;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(UserInfo userInfo) {
        if (loginView != null) {
            loginView.showProgress();
            LoginApi
                    .getInstance()
                    .getToken(userInfo, this);
        }
    }

    @Override
    public void exit(UiHandler handler) {
        if (!isExit) {
            isExit = true;
            if (loginView != null)
                loginView.showExitMessage();
            // 利用handler延迟发送更改状态信息
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    isExit = false;
                }
            }, Constants.BACK_EXIT_DELAY_TIME);
        } else {
            if (loginView != null) {
                loginView.exit();
            }
        }
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo, LoginInfo loginInfo) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.onGetTokenSuccess(userInfo, loginInfo);
        }
    }


    @Override
    public void onLoginFailure() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.onFailure();
        }
    }

    @Override
    public void onLoginResponseError(String err_msg) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.onResponseError(err_msg);
        }
    }
}
