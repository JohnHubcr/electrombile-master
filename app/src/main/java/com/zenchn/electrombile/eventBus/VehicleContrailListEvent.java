package com.zenchn.electrombile.eventBus;

import com.zenchn.electrombile.entity.VehicleLocationInfo;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/1 21:12
 * 描    述：轨迹信息（集合）的事件
 * 修订记录：
 */
public class VehicleContrailListEvent {

    private List<VehicleLocationInfo> vehicleLocationInfoList;

    public VehicleContrailListEvent() {
    }

    public VehicleContrailListEvent(List<VehicleLocationInfo> vehicleLocationInfoList) {
        this.vehicleLocationInfoList = vehicleLocationInfoList;
    }

    public List<VehicleLocationInfo> getVehicleLocationInfoList() {
        return vehicleLocationInfoList;
    }

}
