package com.zenchn.electrombile.entity;

import com.zenchn.electrombile.Constants;

import java.io.Serializable;

/**
 * 作    者：wangr on 2017/2/28 12:16
 * 描    述：车辆档案信息描述类
 * 修订记录：
 */
public class VehicleRecordInfo implements Serializable {

    private String serialNumber;// 设备编号
    private String vehicleAlias;// 车辆别名

    private String vehicleBrand;// 车辆品牌
    private String vehicleModel;// 车辆型号
    private String machineNumber;// 电机编号
    private String motorNo;// 车架编号
    private String payTime;// 购买时间（图片文件）

    private String id;//主键ID
    private String useId;//用户ID
    private String equipmentId;//equipmentId

    private boolean whetherCommon;//是否常用
    private int userLevel;// 1:主; 2:从

    public VehicleRecordInfo() {
    }

    public VehicleRecordInfo(String serialNumber, String vehicleAlias, boolean whetherCommon, int userLevel) {
        this.serialNumber = serialNumber;
        this.vehicleAlias = vehicleAlias;
        this.whetherCommon = whetherCommon;
        this.userLevel = userLevel;
    }

    public VehicleRecordInfo(String serialNumber, String vehicleAlias, int whetherCommon, int userLevel) {
        this.serialNumber = serialNumber;
        this.vehicleAlias = vehicleAlias;
        this.whetherCommon = whetherCommon == Constants.VEHICLE.COMMON_VEHICLE;
        this.userLevel = userLevel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVehicleAlias() {
        return vehicleAlias;
    }

    public void setVehicleAlias(String vehicleAlias) {
        this.vehicleAlias = vehicleAlias;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getMotorNo() {
        return motorNo;
    }

    public void setMotorNo(String motorNo) {
        this.motorNo = motorNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public boolean isWhetherCommon() {
        return whetherCommon;
    }

    public void setWhetherCommon(boolean whetherCommon) {
        this.whetherCommon = whetherCommon;
    }

    public void setWhetherCommon(int whetherCommon) {
        this.whetherCommon = whetherCommon == Constants.VEHICLE.COMMON_VEHICLE;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "VehicleRecordInfo{" +
                "serialNumber='" + serialNumber + '\'' +
                ", vehicleAlias='" + vehicleAlias + '\'' +
                ", vehicleBrand='" + vehicleBrand + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", machineNumber='" + machineNumber + '\'' +
                ", motorNo='" + motorNo + '\'' +
                ", payTime='" + payTime + '\'' +
                ", id='" + id + '\'' +
                ", useId='" + useId + '\'' +
                ", equipmentId='" + equipmentId + '\'' +
                ", whetherCommon=" + whetherCommon +
                ", userLevel=" + userLevel +
                '}';
    }
}
