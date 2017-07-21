package com.zenchn.electrombile.entity;

import java.io.Serializable;


/**
 * 作    者：wangr on 2017/3/8 14:47
 * 描    述：保险保单描述类
 * 修订记录：
 */
public class InsurancePolicyInfo implements Serializable {

    private String content;// 保险名称
    private String remark;// 商品描述
    private String category;// 保险公司名称
    private String protectionStartTime;//保险生效时间
    private String protectionEndTime;//保险到期时间
    private String phone;// 服务热线

    public InsurancePolicyInfo() {
    }

    public InsurancePolicyInfo(String content, String remark, String category, String protectionStartTime, String protectionEndTime, String phone) {
        this.content = content;
        this.remark = remark;
        this.category = category;
        this.protectionStartTime = protectionStartTime;
        this.protectionEndTime = protectionEndTime;
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProtectionStartTime() {
        return protectionStartTime;
    }

    public void setProtectionStartTime(String protectionStartTime) {
        this.protectionStartTime = protectionStartTime;
    }

    public String getProtectionEndTime() {
        return protectionEndTime;
    }

    public void setProtectionEndTime(String protectionEndTime) {
        this.protectionEndTime = protectionEndTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "InsurancePolicyInfo{" +
                "content='" + content + '\'' +
                ", remark='" + remark + '\'' +
                ", category='" + category + '\'' +
                ", protectionStartTime='" + protectionStartTime + '\'' +
                ", protectionEndTime='" + protectionEndTime + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

