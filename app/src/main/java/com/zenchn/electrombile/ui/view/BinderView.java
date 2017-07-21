package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;

/**
 * 作    者：wangr on 2017/2/22 14:24
 * 描    述：
 * 修订记录：
 */
public interface BinderView extends BaseView {

    /**
     * 询问验证
     *
     * @param encryptSerialNumber
     * @param mobilePhone
     */
    void askValidate(String encryptSerialNumber, String mobilePhone);

    /**
     * 绑定设备
     *
     * @param encryptSerialNumber
     */
    void bindEquipment(String encryptSerialNumber);

    /**
     * 绑定结果
     *
     * @param result
     * @param msg
     */
    void onBindResult(boolean result, String msg);

}
