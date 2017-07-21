package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/2/28 17:31
 * 描    述：
 * 修订记录：
 */
public interface VehicleArchivesPresenter extends BasePresenter {

    /**
     * 获取车辆档案信息
     *
     * @param accessToken
     * @param serialNumber
     */
    void getVehicleRecordInfo(String accessToken, String serialNumber);

}
