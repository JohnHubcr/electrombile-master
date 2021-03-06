package com.zenchn.electrombile.engine.bean;

/**
 * 作    者：wangr on 2017/3/13 14:01
 * 描    述：神腾协议解析结果
 * 修订记录：
 */
public class VehicleCheckResultForSTInfo {

    private ST_BMSTrouble STBmsTrouble;
    private ST_VehicleHardwareInfo STMotorHardwareInfo;

    public VehicleCheckResultForSTInfo() {
    }

    public VehicleCheckResultForSTInfo(ST_BMSTrouble STBmsTrouble, ST_VehicleHardwareInfo STMotorHardwareInfo) {
        this.STBmsTrouble = STBmsTrouble;
        this.STMotorHardwareInfo = STMotorHardwareInfo;
    }

    public ST_BMSTrouble getSTBmsTrouble() {
        return STBmsTrouble;
    }

    public void setSTBmsTrouble(ST_BMSTrouble STBmsTrouble) {
        this.STBmsTrouble = STBmsTrouble;
    }

    public ST_VehicleHardwareInfo getSTMotorHardwareInfo() {
        return STMotorHardwareInfo;
    }

    public void setSTMotorHardwareInfo(ST_VehicleHardwareInfo STMotorHardwareInfo) {
        this.STMotorHardwareInfo = STMotorHardwareInfo;
    }

    @Override
    public String toString() {
        return "VehicleCheckResultForSTInfo:" + "\n" +
                "STMotorHardwareInfo:" + (STMotorHardwareInfo != null ? STMotorHardwareInfo.toString() : "null") + "\n" +
                "STBmsTrouble:" + (STBmsTrouble != null ? STBmsTrouble.toString() : "null");
    }
}
