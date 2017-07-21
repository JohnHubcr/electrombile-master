package com.zenchn.electrombile.entity;

import java.io.Serializable;

/**
 * 申请保险理赔信息
 */
public class ApplyClaimInfo implements Serializable {

    private float motorCost;// 购车价格
    private String motorBuyDate;// 购车日期
    private String address;// 通信地址
    private String providerName;// 经销商名称
    private String providerAddress;// 经销商地址

//    private File idCardFrontFile;// 身份证正面图片
//    private File idCardBackFile;// 身份证背面图片
//    private File invoiceFile;// 发票图片
//    private File motorPhotoFile;// 车辆照片

    private String idCardFrontPath;//身份证正面图片路径
    private String idCardBackPath;//身份证背面图片路径
    private String motorPhotoPath;//车辆图片路径
    private String invoicePath;//发票图片路径
    private String certificatePath;//合格证图片路径

    public ApplyClaimInfo() {
    }

    public ApplyClaimInfo(float motorCost, String motorBuyDate, String address, String providerName, String providerAddress) {
        this.motorCost = motorCost;
        this.motorBuyDate = motorBuyDate;
        this.address = address;
        this.providerName = providerName;
        this.providerAddress = providerAddress;
    }

    public ApplyClaimInfo(float motorCost, String motorBuyDate, String address, String providerName, String providerAddress, String idCardFrontPath, String idCardBackPath, String motorPhotoPath, String invoicePath, String certificatePath) {
        this.motorCost = motorCost;
        this.motorBuyDate = motorBuyDate;
        this.address = address;
        this.providerName = providerName;
        this.providerAddress = providerAddress;
        this.idCardFrontPath = idCardFrontPath;
        this.idCardBackPath = idCardBackPath;
        this.motorPhotoPath = motorPhotoPath;
        this.invoicePath = invoicePath;
        this.certificatePath = certificatePath;
    }

    public float getMotorCost() {
        return motorCost;
    }

    public void setMotorCost(float motorCost) {
        this.motorCost = motorCost;
    }

    public String getMotorBuyDate() {
        return motorBuyDate;
    }

    public void setMotorBuyDate(String motorBuyDate) {
        this.motorBuyDate = motorBuyDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public String getIdCardFrontPath() {
        return idCardFrontPath;
    }

    public void setIdCardFrontPath(String idCardFrontPath) {
        this.idCardFrontPath = idCardFrontPath;
    }

    public String getIdCardBackPath() {
        return idCardBackPath;
    }

    public void setIdCardBackPath(String idCardBackPath) {
        this.idCardBackPath = idCardBackPath;
    }

    public String getMotorPhotoPath() {
        return motorPhotoPath;
    }

    public void setMotorPhotoPath(String motorPhotoPath) {
        this.motorPhotoPath = motorPhotoPath;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    @Override
    public String toString() {
        return "ApplyClaimInfo{" +
                "motorCost='" + motorCost + '\'' +
                ", motorBuyDate='" + motorBuyDate + '\'' +
                ", address='" + address + '\'' +
                ", providerName='" + providerName + '\'' +
                ", providerAddress='" + providerAddress + '\'' +
                ", idCardFrontPath='" + idCardFrontPath + '\'' +
                ", idCardBackPath='" + idCardBackPath + '\'' +
                ", motorPhotoPath='" + motorPhotoPath + '\'' +
                ", invoicePath='" + invoicePath + '\'' +
                ", certificatePath='" + certificatePath + '\'' +
                '}';
    }
}
