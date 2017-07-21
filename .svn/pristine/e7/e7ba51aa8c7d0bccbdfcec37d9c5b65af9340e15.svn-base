package com.zenchn.electrombile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/2/28 18:32
 * 描    述：
 * 修订记录：
 */
public class VehicleCheckForJKInfo implements Serializable, Parcelable {

    private String meterTroubleStatus;// 仪表故障状态
    private String chargerTrouble;// 充电器故障
    private String bmsTrouble;// 电池故障
    private String controlTroubleStatus1;// 控制器故障
    private String controlTroubleStatus2;// 控制器故障
    private String ecuTrouble;// ecu故障
    private String ecuOnlineCheck;// ecu在线状态
    private String gyroscopeStatus;// 陀螺仪状态

    public VehicleCheckForJKInfo() {
    }

    public VehicleCheckForJKInfo(String meterTroubleStatus, String chargerTrouble, String bmsTrouble, String controlTroubleStatus1, String controlTroubleStatus2, String ecuTrouble, String ecuOnlineCheck, String gyroscopeStatus) {
        this.meterTroubleStatus = meterTroubleStatus;
        this.chargerTrouble = chargerTrouble;
        this.bmsTrouble = bmsTrouble;
        this.controlTroubleStatus1 = controlTroubleStatus1;
        this.controlTroubleStatus2 = controlTroubleStatus2;
        this.ecuTrouble = ecuTrouble;
        this.ecuOnlineCheck = ecuOnlineCheck;
        this.gyroscopeStatus = gyroscopeStatus;
    }

    public String getMeterTroubleStatus() {
        return meterTroubleStatus;
    }

    public void setMeterTroubleStatus(String meterTroubleStatus) {
        this.meterTroubleStatus = meterTroubleStatus;
    }

    public String getChargerTrouble() {
        return chargerTrouble;
    }

    public void setChargerTrouble(String chargerTrouble) {
        this.chargerTrouble = chargerTrouble;
    }

    public String getBmsTrouble() {
        return bmsTrouble;
    }

    public void setBmsTrouble(String bmsTrouble) {
        this.bmsTrouble = bmsTrouble;
    }

    public String getControlTroubleStatus1() {
        return controlTroubleStatus1;
    }

    public void setControlTroubleStatus1(String controlTroubleStatus1) {
        this.controlTroubleStatus1 = controlTroubleStatus1;
    }

    public String getControlTroubleStatus2() {
        return controlTroubleStatus2;
    }

    public void setControlTroubleStatus2(String controlTroubleStatus2) {
        this.controlTroubleStatus2 = controlTroubleStatus2;
    }

    public String getEcuTrouble() {
        return ecuTrouble;
    }

    public void setEcuTrouble(String ecuTrouble) {
        this.ecuTrouble = ecuTrouble;
    }

    public String getEcuOnlineCheck() {
        return ecuOnlineCheck;
    }

    public void setEcuOnlineCheck(String ecuOnlineCheck) {
        this.ecuOnlineCheck = ecuOnlineCheck;
    }

    public String getGyroscopeStatus() {
        return gyroscopeStatus;
    }

    public void setGyroscopeStatus(String gyroscopeStatus) {
        this.gyroscopeStatus = gyroscopeStatus;
    }

    @Override
    public String toString() {
        return "VehicleCheckForJKInfo{" +
                "meterTroubleStatus='" + meterTroubleStatus + '\'' +
                ", chargerTrouble='" + chargerTrouble + '\'' +
                ", bmsTrouble='" + bmsTrouble + '\'' +
                ", controlTroubleStatus1='" + controlTroubleStatus1 + '\'' +
                ", controlTroubleStatus2='" + controlTroubleStatus2 + '\'' +
                ", ecuTrouble='" + ecuTrouble + '\'' +
                ", ecuOnlineCheck='" + ecuOnlineCheck + '\'' +
                ", gyroscopeStatus='" + gyroscopeStatus + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.meterTroubleStatus);
        dest.writeString(this.chargerTrouble);
        dest.writeString(this.bmsTrouble);
        dest.writeString(this.controlTroubleStatus1);
        dest.writeString(this.controlTroubleStatus2);
        dest.writeString(this.ecuTrouble);
        dest.writeString(this.ecuOnlineCheck);
        dest.writeString(this.gyroscopeStatus);
    }

    protected VehicleCheckForJKInfo(Parcel in) {
        this.meterTroubleStatus = in.readString();
        this.chargerTrouble = in.readString();
        this.bmsTrouble = in.readString();
        this.controlTroubleStatus1 = in.readString();
        this.controlTroubleStatus2 = in.readString();
        this.ecuTrouble = in.readString();
        this.ecuOnlineCheck = in.readString();
        this.gyroscopeStatus = in.readString();
    }

    public static final Creator<VehicleCheckForJKInfo> CREATOR = new Creator<VehicleCheckForJKInfo>() {
        @Override
        public VehicleCheckForJKInfo createFromParcel(Parcel source) {
            return new VehicleCheckForJKInfo(source);
        }

        @Override
        public VehicleCheckForJKInfo[] newArray(int size) {
            return new VehicleCheckForJKInfo[size];
        }
    };
}

