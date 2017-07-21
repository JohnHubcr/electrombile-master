package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/2/22 16:32
 * 描    述：
 * 修订记录：
 */
public interface ValidatePresenter extends BasePresenter {

    /**
     * 注册
     *
     * @param mobilePhone     手机号
     * @param encryptPassword 加密后的密码
     * @param authCode        验证码
     */
    void register(String mobilePhone, String encryptPassword, String authCode);

    /**
     * 修改账号
     *
     * @param accessToken
     * @param newMobilePhone
     * @param encryptPassword
     * @param authCode
     */
    void modifyAccount(String accessToken, String newMobilePhone, String encryptPassword, String authCode);


    /**
     * 根据手机号获取短信验证码
     *
     * @param mobilePhone 手机号
     * @param type        类型
     */
    void getAuthCode(String mobilePhone, String type);

    /**
     * 绑定设备的验证
     *
     * @param accessToken
     * @param encryptSerialNumber
     * @param mobilePhone
     * @param authCode
     */
    void bindEquipment(String accessToken, String encryptSerialNumber, String mobilePhone, String authCode);

    /**
     * 检验注册信息有效性
     *
     * @param mobilePhone 手机号
     * @param authCode    验证码
     */
    void verifyAuthCode(String mobilePhone, String authCode);

}
