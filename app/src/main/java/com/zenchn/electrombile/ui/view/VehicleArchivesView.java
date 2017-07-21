package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.entity.VehicleRecordInfo;

/**
 * 作    者：wangr on 2017/2/28 17:33
 * 描    述：
 * 修订记录：
 */
public interface VehicleArchivesView extends BaseView{

    /**
     * 显示车辆信息
     *
     * @param vehicleRecordInfo
     */
    void showVehicleRecordInfo(VehicleRecordInfo vehicleRecordInfo);

}
