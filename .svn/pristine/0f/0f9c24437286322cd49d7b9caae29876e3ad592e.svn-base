package com.zenchn.electrombile.presenter;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/3/3 23:15
 * 描    述：
 * 修订记录：
 */

public interface ChargingOutletsPresenter extends BasePresenter {

    /**
     * 初始化定位
     *
     * @param applicationContext
     */
    void getBDLocation(Context applicationContext);

    /**
     * 车辆售后点查询
     *
     * @param accessToken
     * @param bdLocation
     * @param pageNo
     * @param pageSize
     * @param serviceType
     * @param isRefresh
     */
    void searchServiceStation(String accessToken, BDLocation bdLocation, int pageNo, int pageSize, int serviceType, boolean isRefresh);

}
