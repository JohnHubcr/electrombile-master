package com.zenchn.electrombile.presenter.impl;

import android.os.Handler;

import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.api.LogoutApi;
import com.zenchn.electrombile.api.callback.LogoutCallback;
import com.zenchn.electrombile.presenter.HomePagePresenter;
import com.zenchn.electrombile.ui.view.HomePageView;

/**
 * 作    者：wangr on 2017/2/24 15:21
 * 描    述：
 * 修订记录：
 */
public class HomePagePresenterImpl implements HomePagePresenter, LogoutCallback {

    private HomePageView homePageView;
    private boolean isExit;

    public HomePagePresenterImpl(HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    @Override
    public void onDestroy() {
        homePageView = null;
    }

    @Override
    public void exit(Handler handler) {
        if (!isExit) {
            isExit = true;
            if (homePageView != null)
                homePageView.showExitMessage();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    isExit = false;
                }
            }, Constants.BACK_EXIT_DELAY_TIME);
        } else {
            if (homePageView != null) {
                homePageView.logout();
            }
        }
    }

    @Override
    public void logout(String loginName, String accessToken) {
        if (homePageView != null) {
            homePageView.showProgress();
            LogoutApi
                    .getInstance()
                    .Logout(loginName, accessToken, this);
        }
    }

    @Override
    public void onLogoutSuccess() {
        if (homePageView != null) {
            homePageView.hideProgress();
            homePageView.exit();
        }
    }

    @Override
    public void onLogoutFailure() {
        if (homePageView != null) {
            homePageView.hideProgress();
            homePageView.exit();
        }
    }

}
