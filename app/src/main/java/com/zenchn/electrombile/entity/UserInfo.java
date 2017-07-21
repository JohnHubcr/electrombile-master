package com.zenchn.electrombile.entity;


import com.zenchn.mlibrary.utils.EncryptUtils;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/2/21 21:42
 * 描    述：用户信息描述类
 * 修订记录：
 */

public class UserInfo implements Serializable {

    private String clientId;
    private String clientSecret;
    private String grantType;
    private String username;
    private String password;
    private String deviceId;
    private String deviceName;
    private String deviceType;

    public UserInfo() {
    }

    public UserInfo(String clientId, String clientSecret, String grantType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
    }

    public UserInfo(String clientId, String clientSecret, String grantType, String deviceId, String deviceName, String deviceType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
    }

    public UserInfo(String clientId, String clientSecret, String grantType, String username, String password, String deviceId, String deviceName, String deviceType) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.username = username;
        this.password = EncryptUtils.MD5Encrypt(password);
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = EncryptUtils.MD5Encrypt(password);
    }

    public void setEncryptPassword(String encryptPassword) {
        this.password = encryptPassword;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public String toString() {
        return "ClientInfo{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", grantType='" + grantType + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
