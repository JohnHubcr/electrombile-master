package com.zenchn.electrombile.entity;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/3/8 11:22
 * 描    述：申请激活保险信息描述类
 * 修订记录：
 */
public class InsuranceActivateInfo implements Serializable {

    private String name;// 姓名
    private String idCard;// 身份证号
    private String mobilePhone;// 联系方式
    private String frameNumber;// 车架编号
    private String machineNumber;// 电机编号

    public InsuranceActivateInfo() {
    }

    public InsuranceActivateInfo(String name, String idCard, String mobilePhone, String frameNumber, String machineNumber) {
        this.name = name;
        this.idCard = idCard;
        this.mobilePhone = mobilePhone;
        this.frameNumber = frameNumber;
        this.machineNumber = machineNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public String toString() {
        return "ApplyProtectionInfo{" +
                "machineNumber='" + machineNumber + '\'' +
                ", frameNumber='" + frameNumber + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
