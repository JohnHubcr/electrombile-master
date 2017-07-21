package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;
import com.zenchn.electrombile.entity.VehicleRecordInfo;

/**
 * 作    者：wangr on 2017/2/28 15:29
 * 描    述：
 * 修订记录：
 */
public interface VehicleListPresenter extends BasePresenter {


    /**
     * 获取车辆列表信息
     *
     * @param accessToken
     */
    void getVehicleList(String accessToken);

    /**
     * 设置常用车辆
     *
     * @param accessToken
     * @param id
     * @param oldId
     */
    void setCommonVehicle(String accessToken, String id, String oldId);

    /**
     * 设备解绑
     *
     * @param accessToken
     * @param vehicleRecordInfo
     */
    void unBindEquipment(String accessToken, VehicleRecordInfo vehicleRecordInfo);
}
