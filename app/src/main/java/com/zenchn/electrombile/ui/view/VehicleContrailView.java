package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.entity.VehicleLocationInfo;

import java.util.List;

/**
 * 作    者：wangr on 2017/2/24 15:39
 * 描    述：
 * 修订记录：
 */

public interface VehicleContrailView extends BaseView {

    /**
     * 展示车辆轨迹
     *
     * @param vehicleLocationInfoList
     */
    void showVehicleContrail(List<VehicleLocationInfo> vehicleLocationInfoList);
}
