package com.zenchn.electrombile.widget.PickerView.model;

/**
 * Created by wangr on 2017/3/11 0011.
 */
public class AreaModel {
    private String mProvince;
    private String mCity;
    private String mDistrict;

    public AreaModel() {
    }

    public AreaModel(String mProvince, String mCity, String mDistrict) {
        this.mProvince = mProvince;
        this.mCity = mCity;
        this.mDistrict = mDistrict;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        this.mProvince = province;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        this.mDistrict = district;
    }

    @Override
    public String toString() {
        return "AreaModel{" +
                "mProvince='" + mProvince + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mDistrict='" + mDistrict + '\'' +
                '}';
    }
}

