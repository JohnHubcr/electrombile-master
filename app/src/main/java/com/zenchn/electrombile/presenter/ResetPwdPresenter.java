package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/2/22 17:19
 * 描    述：
 * 修订记录：
 */
public interface ResetPwdPresenter extends BasePresenter {

    /**
     * 重设密码
     *
     * @param mobilePhone
     * @param newPassword
     * @param authCode
     */
    void resetPwd(String mobilePhone, String newPassword, String authCode);
}
