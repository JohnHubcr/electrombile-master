package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/3/13 13:53
 * 描    述：
 * 修订记录：
 */
public interface VehicleCheckPresenter extends BasePresenter {

    /**
     * 获取车辆自检信息
     *
     * @param accessToken
     * @param serialNumber
     * @param equModel
     */
    void getVehicleStatus(String accessToken, String serialNumber, int equModel);

}
