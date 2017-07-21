package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/3/1 20:57
 * 描    述：
 * 修订记录：
 */
public interface VehicleContrailPresenter extends BasePresenter {

//    /**
//     * 查询最后一次ACC开启到关闭的轨迹（一期设备为撤防到设防）
//     *
//     * @param accessToken
//     * @param serialNumber
//     * @param equModel
//     */
//    void getVehicleLatestContrail(String accessToken, String serialNumber, int equModel);

    /**
     * 按时间段查询轨迹
     *
     * @param accessToken
     * @param serialNumber
     * @param equModel
     * @param endTime
     */
    void getVehicleContrail(String accessToken, String serialNumber, int equModel, long beginTime, long endTime);


}
