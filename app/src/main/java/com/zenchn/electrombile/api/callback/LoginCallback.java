package com.zenchn.electrombile.api.callback;

import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.UserInfo;


/**
 * 作    者：wangr on 2017/2/21 16:27
 * 描    述：
 * 修订记录：
 */
public interface LoginCallback {

    void onLoginSuccess(UserInfo userInfo, LoginInfo loginInfo);

    void onLoginFailure();

    void onLoginResponseError(String msg);
}
