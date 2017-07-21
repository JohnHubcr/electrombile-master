package com.zenchn.electrombile.presenter;

import android.os.Handler;

import com.zenchn.electrombile.base.BasePresenter;

public interface HomePagePresenter extends BasePresenter {

    /**
     * 控制退出登录
     *
     * @param handler
     */
    void exit(Handler handler);

    /**
     * 退出登录
     *
     * @param loginName
     * @param accessToken
     */
    void logout(String loginName, String accessToken);

}
