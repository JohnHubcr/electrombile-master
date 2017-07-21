package com.zenchn.electrombile.presenter;

import android.content.Context;
import android.content.Intent;

import com.baidu.mapapi.map.BaiduMap;
import com.zenchn.electrombile.base.BasePresenter;

/**
 * 作    者：wangr on 2017/3/4 12:26
 * 描    述：
 * 修订记录：
 */
public interface ServiceMapPresenter extends BasePresenter {

    /**
     * 初始化百度地图
     *
     * @param mBaiduMap
     */
    void initMap(BaiduMap mBaiduMap);

    /**
     * 初始化定位
     *
     * @param applicationContext
     */
    void initBDLocation(Context applicationContext);

    /**
     * 初始化数据
     *
     * @param data
     */
    void initData(Intent data);

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

    /**
     * 开启导航
     * @param mapName
     */
    void toNavigation(String mapName);
}
