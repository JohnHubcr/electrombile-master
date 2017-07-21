package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/2/22 16:03
 * 描    述：
 * 修订记录：
 */

public interface RevPwdPresenter extends BasePresenter {

    /**
     * 修改密码
     *
     * @param accessToken
     * @param password
     * @param oldPwd
     */
    void revPwd(String accessToken, String password, String oldPwd);
}
