package com.zenchn.electrombile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/3/13 14:01
 * 描    述：
 * 修订记录：
 */
public class VehicleCheckRawForSTInfo implements Serializable, Parcelable {

    private String vehicleConditionStatus;// 车况故障状态
    private String bmsTrouble;// 电池故障

    public VehicleCheckRawForSTInfo() {
    }

    public VehicleCheckRawForSTInfo(String vehicleConditionStatus, String bmsTrouble) {
        this.vehicleConditionStatus = vehicleConditionStatus;
        this.bmsTrouble = bmsTrouble;
    }

    public String getVehicleConditionStatus() {
        return vehicleConditionStatus;
    }

    public void setVehicleConditionStatus(String vehicleConditionStatus) {
        this.vehicleConditionStatus = vehicleConditionStatus;
    }

    public String getBmsTrouble() {
        return bmsTrouble;
    }

    public void setBmsTrouble(String bmsTrouble) {
        this.bmsTrouble = bmsTrouble;
    }

    @Override
    public String toString() {
        return "VehicleCheckForSTInfo{" +
                "vehicleConditionStatus='" + vehicleConditionStatus + '\'' +
                ", bmsTrouble='" + bmsTrouble + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vehicleConditionStatus);
        dest.writeString(this.bmsTrouble);
    }

    protected VehicleCheckRawForSTInfo(Parcel in) {
        this.vehicleConditionStatus = in.readString();
        this.bmsTrouble = in.readString();
    }

    public static final Creator<VehicleCheckRawForSTInfo> CREATOR = new Creator<VehicleCheckRawForSTInfo>() {
        @Override
        public VehicleCheckRawForSTInfo createFromParcel(Parcel source) {
            return new VehicleCheckRawForSTInfo(source);
        }

        @Override
        public VehicleCheckRawForSTInfo[] newArray(int size) {
            return new VehicleCheckRawForSTInfo[size];
        }
    };
}
