package com.zenchn.electrombile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/2/22 21:08
 * 描    述：用户登录信息描述类
 * 修订记录：
 */
public class LoginInfo implements Serializable, Parcelable {

    private long loginTime;// 登录时间
    private String loginName;// 用户名
    private String accessToken;// 访问令牌
    private String refreshToken;// 访问令牌
    private long expiresIn;// 令牌到期时间

    private int equModel;// 硬件标示(0未绑定；1大众；2 金开一期；3 金开二期；4 神腾)
    private String serialNumber;// 硬件终端车架号 serialNumber
    private String authorizationPsw;//用户调用指令获取token的密码

    private long registerDate;//用户注册日期 如：2016-10-10 16:30:00

    private String otherPhone;//紧急联系人 如：15313161278

    private int protectionStatus;//保险状态 如：0未启用,审核中1,审核未通过2,保障中3,已过期4,待缴费5
    private String statusDesc;//审核未通过的原因 如：不好看
    private int perfectStatus;//0未完善；1已完善

    private Boolean isMainAccount;//是否是主账号

    public LoginInfo() {
    }

    public LoginInfo(String serialNumber, String authorizationPsw) {
        this.serialNumber = serialNumber;
        this.authorizationPsw = authorizationPsw;
    }

    public LoginInfo(long loginTime, String loginName, String accessToken, String refreshToken, long expiresIn) {
        this.loginTime = loginTime;
        this.loginName = loginName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public LoginInfo(int equModel, String serialNumber, String authorizationPsw, long registerDate, String otherPhone, int protectionStatus, String statusDesc, int perfectStatus) {
        this.equModel = equModel;
        this.serialNumber = serialNumber;
        this.authorizationPsw = authorizationPsw;
        this.registerDate = registerDate;
        this.otherPhone = otherPhone;
        this.protectionStatus = protectionStatus;
        this.statusDesc = statusDesc;
        this.perfectStatus = perfectStatus;
    }

    public LoginInfo(long loginTime, String loginName, String accessToken, String refreshToken, long expiresIn, int equModel, String serialNumber, String authorizationPsw, long registerDate, String otherPhone, int protectionStatus, String statusDesc, int perfectStatus, Boolean isMainAccount) {
        this.loginTime = loginTime;
        this.loginName = loginName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.equModel = equModel;
        this.serialNumber = serialNumber;
        this.authorizationPsw = authorizationPsw;
        this.registerDate = registerDate;
        this.otherPhone = otherPhone;
        this.protectionStatus = protectionStatus;
        this.statusDesc = statusDesc;
        this.perfectStatus = perfectStatus;
        this.isMainAccount = isMainAccount;
    }

    public LoginInfo(long loginTime, String loginName, String accessToken, String refreshToken, long expiresIn, int equModel, String serialNumber, String authorizationPsw, long registerDate, String otherPhone, int protectionStatus, String statusDesc, int perfectStatus) {
        this.loginTime = loginTime;
        this.loginName = loginName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.equModel = equModel;
        this.serialNumber = serialNumber;
        this.authorizationPsw = authorizationPsw;
        this.registerDate = registerDate;
        this.otherPhone = otherPhone;
        this.protectionStatus = protectionStatus;
        this.statusDesc = statusDesc;
        this.perfectStatus = perfectStatus;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public LoginInfo setLoginTime(long loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public LoginInfo setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public LoginInfo setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public LoginInfo setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginInfo setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public int getEquModel() {
        return equModel;
    }

    public LoginInfo setEquModel(int equModel) {
        this.equModel = equModel;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public LoginInfo setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getAuthorizationPsw() {
        return authorizationPsw;
    }

    public LoginInfo setAuthorizationPsw(String authorizationPsw) {
        this.authorizationPsw = authorizationPsw;
        return this;
    }

    public long getRegisterDate() {
        return registerDate;
    }

    public LoginInfo setRegisterDate(long registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public LoginInfo setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
        return this;
    }

    public int getProtectionStatus() {
        return protectionStatus;
    }

    public LoginInfo setProtectionStatus(int protectionStatus) {
        this.protectionStatus = protectionStatus;
        return this;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public LoginInfo setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
        return this;
    }

    public int getPerfectStatus() {
        return perfectStatus;
    }

    public LoginInfo setPerfectStatus(int perfectStatus) {
        this.perfectStatus = perfectStatus;
        return this;
    }

    public Boolean getIsMainAccount() {
        return isMainAccount;
    }

    public LoginInfo setIsMainAccount(Boolean isMainAccount) {
        this.isMainAccount = isMainAccount;
        return this;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "loginTime=" + loginTime +
                ", loginName='" + loginName + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", equModel=" + equModel +
                ", serialNumber='" + serialNumber + '\'' +
                ", authorizationPsw='" + authorizationPsw + '\'' +
                ", registerDate=" + registerDate +
                ", otherPhone='" + otherPhone + '\'' +
                ", protectionStatus=" + protectionStatus +
                ", statusDesc='" + statusDesc + '\'' +
                ", perfectStatus=" + perfectStatus +
                ", isMainAccount=" + isMainAccount +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.loginTime);
        dest.writeString(this.loginName);
        dest.writeString(this.accessToken);
        dest.writeString(this.refreshToken);
        dest.writeLong(this.expiresIn);
        dest.writeInt(this.equModel);
        dest.writeString(this.serialNumber);
        dest.writeString(this.authorizationPsw);
        dest.writeLong(this.registerDate);
        dest.writeString(this.otherPhone);
        dest.writeInt(this.protectionStatus);
        dest.writeString(this.statusDesc);
        dest.writeInt(this.perfectStatus);
        dest.writeValue(this.isMainAccount);
    }

    protected LoginInfo(Parcel in) {
        this.loginTime = in.readLong();
        this.loginName = in.readString();
        this.accessToken = in.readString();
        this.refreshToken = in.readString();
        this.expiresIn = in.readLong();
        this.equModel = in.readInt();
        this.serialNumber = in.readString();
        this.authorizationPsw = in.readString();
        this.registerDate = in.readLong();
        this.otherPhone = in.readString();
        this.protectionStatus = in.readInt();
        this.statusDesc = in.readString();
        this.perfectStatus = in.readInt();
        this.isMainAccount = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };
}
