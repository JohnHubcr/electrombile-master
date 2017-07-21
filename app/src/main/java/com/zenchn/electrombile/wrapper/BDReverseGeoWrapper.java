package com.zenchn.electrombile.wrapper;

import android.support.annotation.NonNull;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.wrapper.callback.BDReverseGeoCallback;

/**
 * 作    者：wangr on 2017/3/1 13:44
 * 描    述： 对百度地图解析地理位置的封装
 * 修订记录：
 */
public class BDReverseGeoWrapper implements OnGetGeoCoderResultListener {

    private LatLng mLatLng;
    private BDReverseGeoCallback callback;
    private int errorCount;

    private BDReverseGeoWrapper() {
    }

    private static class SingletonInstance {
        private static final BDReverseGeoWrapper INSTANCE = new BDReverseGeoWrapper();
    }

    public static BDReverseGeoWrapper getInstance() {
        return SingletonInstance.INSTANCE;
    }


    /**
     * 地理反向解析(latLng-->地理位置)
     *
     * @param mLatLng
     * @param callback
     */
    public void getGeoCode(@NonNull LatLng mLatLng, BDReverseGeoCallback callback) {
        if (mLatLng != null) {
            this.mLatLng = mLatLng;
            this.callback = callback;

            LogKit.success(getClass().getSimpleName(), ":" + "isGetGeoCode……");
            // 初始化搜索模块，注册事件监听
            GeoCoder mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(this);
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(mLatLng));// 反Geo搜索
        } else {
            if (callback != null) {
                callback.onGetBDGeoCodeFailure();
                LogKit.exception(getClass().getSimpleName(), ":" + "latLng is a null object");
            }
        }
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            errorCount++;
            if (errorCount < 3)
                getGeoCode(mLatLng, callback);
            else {
                if (callback != null) {
                    callback.onGetBDGeoCodeFailure();
                    errorCount = 0;//重置错误计数
                }
            }
        } else {
            if (callback != null) {
                callback.onGetBDGeoCodeSuccess(reverseGeoCodeResult);
                errorCount = 0;//重置错误计数
            }
        }
    }
}
