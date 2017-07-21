package com.zenchn.electrombile.eventBus;

import com.zenchn.electrombile.entity.VehicleLocationInfo;

/**
 * 作    者：wangr on 2017/3/1 14:25
 * 描    述：
 * 修订记录：
 */

public class VehicleLocationEvent {

    private VehicleLocationInfo vehicleLocationInfo;

    public VehicleLocationEvent(VehicleLocationInfo vehicleLocationInfo) {
        this.vehicleLocationInfo = vehicleLocationInfo;
    }

    public VehicleLocationInfo getVehicleLocationInfo() {
        return vehicleLocationInfo;
    }
}
