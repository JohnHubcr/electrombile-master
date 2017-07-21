package com.zenchn.electrombile.entity;

/**
 * 作    者：wangr on 2017/3/7 14:55
 * 描    述：保险套餐的描述类
 * 修订记录：
 */

public class InsuranceProductInfo {

    private String id;// 商品ID
    private String name;// 商品名
    private String remark;// 商品描述
    private String price;// 商品价格
    private String oldPrice;// 保险原价
    private String validity;// 详细

    public InsuranceProductInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "InsurancePackageInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", price='" + price + '\'' +
                ", oldPrice='" + oldPrice + '\'' +
                ", validity='" + validity + '\'' +
                '}';
    }
}
