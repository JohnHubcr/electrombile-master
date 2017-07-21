package com.zenchn.electrombile.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class InsuranceOrderInfo implements Parcelable {

    String name;// 商品名
    String remark;// 商品描述
    String price;// 商品价格
    String oldPrice;// 保险原价
    String category;// 保险公司名称
    String insuranceValidity;// 保险有效期(月)
    String phone;// 服务热线

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInsuranceValidity() {
        return insuranceValidity;
    }

    public void setInsuranceValidity(String insuranceValidity) {
        this.insuranceValidity = insuranceValidity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", price='" + price + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", category='" + category + '\'' +
                ", insuranceValidity='" + insuranceValidity + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.remark);
        dest.writeString(this.price);
        dest.writeString(this.oldPrice);
        dest.writeString(this.category);
        dest.writeString(this.insuranceValidity);
        dest.writeString(this.phone);
    }

    public InsuranceOrderInfo() {
    }

    protected InsuranceOrderInfo(Parcel in) {
        this.name = in.readString();
        this.remark = in.readString();
        this.price = in.readString();
        this.oldPrice = in.readString();
        this.category = in.readString();
        this.insuranceValidity = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<InsuranceOrderInfo> CREATOR = new Creator<InsuranceOrderInfo>() {
        @Override
        public InsuranceOrderInfo createFromParcel(Parcel source) {
            return new InsuranceOrderInfo(source);
        }

        @Override
        public InsuranceOrderInfo[] newArray(int size) {
            return new InsuranceOrderInfo[size];
        }
    };
}
