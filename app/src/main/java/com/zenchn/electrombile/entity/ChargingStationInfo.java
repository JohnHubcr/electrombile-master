package com.zenchn.electrombile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * 作    者：wangr on 2017/3/13 14:05
 * 描    述：充电服务站描述类
 * 修订记录：
 */
public class ChargingStationInfo extends BaseStationInfo implements Parcelable, Comparable<ChargingStationInfo> {

    private Double distance;// 距离服务点位置

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(ChargingStationInfo another) {
        return this.distance.compareTo(another.distance);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.distance);
        dest.writeString(this.name);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.img);
        dest.writeString(this.desc);
        dest.writeParcelable(this.latLng, flags);
    }

    public ChargingStationInfo() {
    }

    protected ChargingStationInfo(Parcel in) {
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
        this.name = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.img = in.readString();
        this.desc = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<ChargingStationInfo> CREATOR = new Creator<ChargingStationInfo>() {
        @Override
        public ChargingStationInfo createFromParcel(Parcel source) {
            return new ChargingStationInfo(source);
        }

        @Override
        public ChargingStationInfo[] newArray(int size) {
            return new ChargingStationInfo[size];
        }
    };
}