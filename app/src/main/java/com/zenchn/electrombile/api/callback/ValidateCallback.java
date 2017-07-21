package com.zenchn.electrombile.api.callback;

import com.zenchn.electrombile.entity.UserInfo;

/**
 * 作    者：wangr on 2017/2/23 16:17
 * 描    述：
 * 修订记录：
 */

public interface ValidateCallback {

    void onGetAuthCodeSuccess();

    void onGetAuthCodeError();

    void onGetAuthCodeResponseError(String msg);

    void onCheckCodeResult(boolean result);

    void onRegisterSuccess(UserInfo userInfo);

    void onRegisterError();

    void onRegisterResponseError(String msg);

    void onModifyAccountSuccess(String mobilePhone);

    void onModifyAccountError();

    void onModifyAccountResponseError(String msg);

    void onGrantRefuse();

}
