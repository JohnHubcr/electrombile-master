package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/2/22 14:14
 * 描    述：
 * 修订记录：
 */

public interface BinderPresenter extends BasePresenter {

    /**
     * 绑定设备
     *
     * @param accessToken
     * @param encryptSerialNumber
     */
    void getBindStatus(String accessToken, String encryptSerialNumber);

    /**
     * 绑定设备
     *
     * @param accessToken
     * @param encryptSerialNumber
     */
    void bindEquipment(String accessToken, String encryptSerialNumber);

}
