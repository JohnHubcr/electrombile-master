package com.zenchn.electrombile.eventBus;

import com.zenchn.electrombile.entity.VehicleLocationInfo;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/1 21:18
 * 描    述：轨迹结果的事件
 * 修订记录：
 */
public class VehicleContrailQueryListEvent {

    private List<VehicleLocationInfo> vehicleLocationInfoList;

    public VehicleContrailQueryListEvent() {
    }

    public VehicleContrailQueryListEvent(List<VehicleLocationInfo> vehicleLocationInfoList) {
        this.vehicleLocationInfoList = vehicleLocationInfoList;
    }

    public List<VehicleLocationInfo> getVehicleLocationInfoList() {
        return vehicleLocationInfoList;
    }

}
