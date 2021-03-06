package com.zenchn.electrombile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * 作    者：wangr on 2017/3/3 19:13
 * 描    述：售后信息描述基类
 * 修订记录：
 */
public class BaseStationInfo implements Parcelable{

    protected String name;// 名称
    protected String province;//省
    protected String city;//市
    protected String district;//区
    protected String area;//区域信息
    protected String address;// 详细地址
    protected String phone;// 联系电话
    protected String img;// 展示图片
    protected String desc;//描述信息
    protected LatLng latLng;//经纬度信息


    public BaseStationInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "BaseStationInfo{" +
                "name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", img='" + img + '\'' +
                ", desc='" + desc + '\'' +
                ", latLng=" + latLng +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.area);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.img);
        dest.writeString(this.desc);
        dest.writeParcelable(this.latLng, flags);
    }

    protected BaseStationInfo(Parcel in) {
        this.name = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.area = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.img = in.readString();
        this.desc = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<BaseStationInfo> CREATOR = new Creator<BaseStationInfo>() {
        @Override
        public BaseStationInfo createFromParcel(Parcel source) {
            return new BaseStationInfo(source);
        }

        @Override
        public BaseStationInfo[] newArray(int size) {
            return new BaseStationInfo[size];
        }
    };
}
