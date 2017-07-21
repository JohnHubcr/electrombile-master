package com.zenchn.electrombile.api.callback;

import com.zenchn.electrombile.entity.VehicleCheckRawForJKInfo;
import com.zenchn.electrombile.entity.VehicleCheckRawForSTInfo;

/**
 * 作    者：wangr on 2017/3/1 11:32
 * 描    述：
 * 修订记录：
 */

public interface VehicleCheckCallback extends GrantCallback{

    void onGetVehicleCheckSuccess(VehicleCheckRawForSTInfo vehicleCheckRawForSTInfo);

    void onGetVehicleCheckSuccess(VehicleCheckRawForJKInfo vehicleCheckRawForJKInfo);

    void onGetVehicleCheckFailure();

    void onGetVehicleCheckResponseError(String error_msg);

}
