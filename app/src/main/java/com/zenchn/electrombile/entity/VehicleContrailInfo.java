package com.zenchn.electrombile.entity;

/**
 * 作    者：wangr on 2017/3/1 16:20
 * 描    述：轨迹信息描述类
 * 修订记录：
 */
public class VehicleContrailInfo {

    private double totalMileage;
    private long totalTime;

    private VehicleLocationInfo lastVehicleLocationInfo;

    public VehicleContrailInfo() {
    }

    public VehicleContrailInfo(VehicleLocationInfo lastVehicleLocationInfo) {
        this.lastVehicleLocationInfo = lastVehicleLocationInfo;
    }

    public VehicleContrailInfo(double totalMileage, long totalTime) {
        this.totalMileage = totalMileage;
        this.totalTime = totalTime;
    }

    public VehicleContrailInfo(double totalMileage, long totalTime, VehicleLocationInfo lastVehicleLocationInfo) {
        this.totalMileage = totalMileage;
        this.totalTime = totalTime;
        this.lastVehicleLocationInfo = lastVehicleLocationInfo;
    }

    public double getTotalMileage() {
        return totalMileage;
    }

    public VehicleContrailInfo setTotalMileage(double totalMileage) {
        this.totalMileage = totalMileage;
        return this;
    }

    public VehicleContrailInfo addTotalMileage(double totalMileage) {
        this.totalMileage += totalMileage;
        return this;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public VehicleContrailInfo setTotalTime(long totalTime) {
        this.totalTime = totalTime;
        return this;
    }

    public VehicleContrailInfo addTotalTime(long totalTime) {
        this.totalTime += totalTime;
        return this;
    }

    public VehicleLocationInfo getLastVehicleLocationInfo() {
        return lastVehicleLocationInfo;
    }

    public VehicleContrailInfo setLastVehicleLocationInfo(VehicleLocationInfo lastVehicleLocationInfo) {
        this.lastVehicleLocationInfo = lastVehicleLocationInfo;
        return this;
    }

    @Override
    public String toString() {
        return "VehicleContrailInfo{" +
                "totalMileage=" + totalMileage +
                ", totalTime=" + totalTime +
                ", lastVehicleLocationInfo=" + lastVehicleLocationInfo.toString() +
                '}';
    }
}
