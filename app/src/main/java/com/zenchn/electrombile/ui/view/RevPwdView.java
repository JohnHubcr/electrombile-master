package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;

/**
 * 作    者：wangr on 2017/2/22 16:01
 * 描    述：
 * 修订记录：
 */
public interface RevPwdView extends BaseView {

    /**
     * 提醒修改密码成功
     *
     * @param encryptNewPassword
     */
    void modifySuccess(String encryptNewPassword);

}
