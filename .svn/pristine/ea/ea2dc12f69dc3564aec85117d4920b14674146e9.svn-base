package com.zenchn.electrombile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/2/22 19:39
 * 描    述：客户端信息描述类
 * 修订记录：
 */
public class ClientInfo implements Serializable, Parcelable {

    private String serialNumber;//设备序列号（定位终端）
    private String systemType;//手机序列号
    private String appSpecification;//手机型号
    private String appVersion;//app版本
    private String vehicleIMEI;//手机串号
    private String token;//信鸽token
    private long quitTime;//app上次退出时间

    public ClientInfo() {
    }

    public ClientInfo(String systemType, String vehicleIMEI, String token) {
        this.systemType = systemType;
        this.vehicleIMEI = vehicleIMEI;
        this.token = token;
    }

    public ClientInfo(String systemType, String appSpecification, String appVersion, String vehicleIMEI, String token) {
        this.systemType = systemType;
        this.appSpecification = appSpecification;
        this.appVersion = appVersion;
        this.vehicleIMEI = vehicleIMEI;
        this.token = token;
    }

    public ClientInfo(String systemType, String appSpecification, String appVersion, String vehicleIMEI, String token, long quitTime) {
        this.systemType = systemType;
        this.appSpecification = appSpecification;
        this.appVersion = appVersion;
        this.vehicleIMEI = vehicleIMEI;
        this.token = token;
        this.quitTime = quitTime;
    }

    public ClientInfo(String serialNumber, String systemType, String appSpecification, String appVersion, String vehicleIMEI, String token, long quitTime) {
        this.serialNumber = serialNumber;
        this.systemType = systemType;
        this.appSpecification = appSpecification;
        this.appVersion = appVersion;
        this.vehicleIMEI = vehicleIMEI;
        this.token = token;
        this.quitTime = quitTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getAppSpecification() {
        return appSpecification;
    }

    public void setAppSpecification(String appSpecification) {
        this.appSpecification = appSpecification;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getVehicleIMEI() {
        return vehicleIMEI;
    }

    public void setVehicleIMEI(String vehicleIMEI) {
        this.vehicleIMEI = vehicleIMEI;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(long quitTime) {
        this.quitTime = quitTime;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "serialNumber='" + serialNumber + '\'' +
                ", systemType='" + systemType + '\'' +
                ", appSpecification='" + appSpecification + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", vehicleIMEI='" + vehicleIMEI + '\'' +
                ", token='" + token + '\'' +
                ", quitTime=" + quitTime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.serialNumber);
        dest.writeString(this.systemType);
        dest.writeString(this.appSpecification);
        dest.writeString(this.appVersion);
        dest.writeString(this.vehicleIMEI);
        dest.writeString(this.token);
        dest.writeLong(this.quitTime);
    }

    protected ClientInfo(Parcel in) {
        this.serialNumber = in.readString();
        this.systemType = in.readString();
        this.appSpecification = in.readString();
        this.appVersion = in.readString();
        this.vehicleIMEI = in.readString();
        this.token = in.readString();
        this.quitTime = in.readLong();
    }

    public static final Creator<ClientInfo> CREATOR = new Creator<ClientInfo>() {
        @Override
        public ClientInfo createFromParcel(Parcel source) {
            return new ClientInfo(source);
        }

        @Override
        public ClientInfo[] newArray(int size) {
            return new ClientInfo[size];
        }
    };
}
