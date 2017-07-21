package com.zenchn.electrombile.eventBus;

import com.zenchn.electrombile.engine.bean.VehicleCheckResultForJKInfo;

/**
 * 作    者：wangr on 2017/3/14 13:14
 * 描    述：
 * 修订记录：
 */

public class VehicleCheckResultForJKInfoEvent {

    private VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo;

    public VehicleCheckResultForJKInfoEvent(VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo) {
        this.vehicleCheckResultForJKInfo = vehicleCheckResultForJKInfo;
    }

    public VehicleCheckResultForJKInfo getVehicleCheckResultForJKInfo() {
        return vehicleCheckResultForJKInfo;
    }

    public void setVehicleCheckResultForJKInfo(VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo) {
        this.vehicleCheckResultForJKInfo = vehicleCheckResultForJKInfo;
    }
}
