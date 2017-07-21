package com.zenchn.electrombile.presenter;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.zenchn.electrombile.base.BasePresenter;
import com.zenchn.electrombile.entity.VehicleLocationInfo;

/**
 * 作    者：wangr on 2017/2/28 11:05
 * 描    述：
 * 修订记录：
 */
public interface VehicleTrackingPresenter extends BasePresenter {

    /**
     * 初始化百度地图
     *
     * @param mBaiduMap
     */
    void initMap(BaiduMap mBaiduMap);

    /**
     * 初始化百度定位
     *
     * @param locationClient
     */
    void initBDLocation(LocationClient locationClient);

    /**
     * 更新车辆定位信息
     *
     * @param vehicleLocationInfo
     */
    void updateVehicleLocation(VehicleLocationInfo vehicleLocationInfo);

    /**
     * 开启地图导航
     *
     * @param mapName
     */
    void toNavigation(String mapName);

    /**
     * 设置中心点
     *
     * @param centerlatLng
     */
    void mapStatusUpdate(LatLng centerlatLng);

    /**
     * 暂停定位
     */
    void onLocationPause();

    /**
     * 恢复定位
     */
    void onLocationResume();

    /**
     * 是否显示我的位置
     *
     * @param isLocationOn
     */
    void showMyLocation(boolean isLocationOn);

}
