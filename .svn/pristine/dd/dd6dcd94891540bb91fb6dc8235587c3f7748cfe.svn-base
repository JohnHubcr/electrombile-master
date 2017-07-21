package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.entity.UserInfo;

/**
 * 作    者：wangr on 2017/2/22 16:33
 * 描    述：
 * 修订记录：
 */

public interface ValidateView extends BaseView {

    /**
     * 验证码发送失败（显示重新发送）
     */
    void onGetAuthCodeError();

    /**
     * 验证码发送失败
     */
    void onShowGetAuthCodeError();

    /**
     * 验证码发送成功（启动倒计时）
     */
    void onGetAuthCodeSuccess();

    /**
     * 注册成功跳转首页
     *
     * @param userInfo
     */
    void navigateToHome(UserInfo userInfo);

    /**
     * 注册失败（访问异常）跳转注册页面
     */
    void navigateToRegister();

    /**
     * 返回上级界面
     */
    void onBack();

    /**
     * 账号变更成功跳转到登录界面
     *
     * @param mobilePhone
     */
    void navigateToLogin(String mobilePhone);

    /**
     * 获取绑定结果后返回的设备绑定界面
     *
     * @param bindResult
     * @param bindMsg
     */
    void backToBind(boolean bindResult, String bindMsg);
}
