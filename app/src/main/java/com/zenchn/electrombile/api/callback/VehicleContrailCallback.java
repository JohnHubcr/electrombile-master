package com.zenchn.electrombile.api.callback;

import com.zenchn.electrombile.entity.VehicleLocationInfo;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/1 11:32
 * 描    述：
 * 修订记录：
 */

public interface VehicleContrailCallback extends GrantCallback{

    void onGetVehicleContrailSuccess(List<VehicleLocationInfo> vehicleLocationInfoList);

    void onGetVehicleContrailFailure();

    void onGetVehicleContrailResponseError(String error_msg);

}
