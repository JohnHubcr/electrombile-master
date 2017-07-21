package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.UserInfo;

/**
 * 作    者：wangr on 2017/2/22 14:24
 * 描    述：
 * 修订记录：
 */
public interface LoginView extends BaseView {

    /**
     * 获取用户信息
     *
     * @param userInfo
     * @param loginInfo
     */
    void onGetTokenSuccess(UserInfo userInfo, LoginInfo loginInfo);

    /**
     * 提醒是否退出
     */
    void showExitMessage();

    /**
     * 退出程序
     */
    void exit();

}
