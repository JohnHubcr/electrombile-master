package com.zenchn.electrombile.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.entity.BaseStationInfo;
import com.zenchn.electrombile.entity.ChargingStationInfo;
import com.zenchn.electrombile.entity.RepairStationInfo;
import com.zenchn.electrombile.presenter.ServiceMapPresenter;
import com.zenchn.electrombile.ui.view.ServiceMapView;
import com.zenchn.electrombile.utils.MapAppUtils;
import com.zenchn.electrombile.utils.MapUtils;
import com.zenchn.electrombile.wrapper.BDLocationWrapper;
import com.zenchn.electrombile.wrapper.callback.BDLocationCallback;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/4 12:27
 * 描    述：
 * 修订记录：
 */
public class ServiceMapPresenterImpl implements ServiceMapPresenter, BaiduMap.OnMarkerClickListener, BDLocationCallback {

    private ServiceMapView serviceMapView;
    private BaiduMap mBaiduMap;

    private BitmapDescriptor stationLocation;
    private BitmapDescriptor stationLocationOther;
    private BitmapDescriptor myLocation = BitmapDescriptorFactory.fromResource(R.drawable.location_map);
    private Marker preMarker;
    private int type;

    private BaseStationInfo baseStationInfo;
    private BDLocationWrapper bdLocationWrapper;

    private LatLng myLatLng;
    private float myRadius;

    public ServiceMapPresenterImpl(ServiceMapView serviceMapView) {
        this.serviceMapView = serviceMapView;
    }

    @Override
    public void onDestroy() {
        serviceMapView = null;
        if (bdLocationWrapper != null)
            bdLocationWrapper.onLocationFinish();
    }

    @Override
    public void initMap(BaiduMap mBaiduMap) {
        this.mBaiduMap = mBaiduMap;

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);// 缩放比率
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMarkerClickListener(this);

        if (serviceMapView != null)
            serviceMapView.showProgress();

        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
    }

    /**
     * 默认中心点
     *
     * @param latLng
     */
    public void mapStatusUpdate(LatLng latLng) {
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(u);
    }

    @Override
    public void initBDLocation(Context applicationContext) {
        bdLocationWrapper = BDLocationWrapper
                .getInstance()
                .getBDLocationRepeat(applicationContext, this);
    }

    @Override
    public void initData(Intent data) {
        type = data.getIntExtra("type", Constants.ServiceType.repair);
        if (Constants.ServiceType.repair == type) {
            stationLocation = BitmapDescriptorFactory.fromResource(R.drawable.maintain_location);
            stationLocationOther = BitmapDescriptorFactory.fromResource(R.drawable.maintain_location_other);
        } else {
            stationLocation = BitmapDescriptorFactory.fromResource(R.drawable.charge_location);
            stationLocationOther = BitmapDescriptorFactory.fromResource(R.drawable.charge_location_other);
        }

        List<BaseStationInfo> stationInfoList = (List<BaseStationInfo>) data.getSerializableExtra("stationInfoList");
        if (stationInfoList != null && !stationInfoList.isEmpty()) {
            int position = data.getIntExtra("position", 0);
            Marker mainMarker = setAllRepairStation(stationInfoList, position);//设置所有覆盖物并返回用户选中的覆盖物
            onMarkerClick(mainMarker);//设置用户选择的覆盖物进行显示
        }
    }

    @Override
    public void onLocationPause() {
        if (bdLocationWrapper != null)
            bdLocationWrapper.onLocationPause();
    }

    @Override
    public void onLocationResume() {
        if (bdLocationWrapper != null)
            bdLocationWrapper.onLocationResume();
    }

    @Override
    public void showMyLocation(boolean isLocationOn) {
        if (serviceMapView != null) {
            if (isLocationOn) {
                if (myLatLng == null) {
                    serviceMapView.showNotLocationMessage();
                } else {
                    LatLng myBdLatLng = setLocationIcon(myLatLng);
                    mapStatusUpdate(myBdLatLng);
                }
            } else {
                mapStatusUpdate(baseStationInfo.getLatLng());
            }
        }
    }

    @Override
    public void toNavigation(String mapName) {
        if (serviceMapView != null) {
            if (MapAppUtils.BAIDU_MAP_NAME.equals(mapName)) {
                if (myLatLng == null || baseStationInfo.getLatLng() == null) {
                    serviceMapView.showNotLocationMessage();
                } else {
                    LatLng myBdLatLng = MapUtils.transformFromGCJToBD(myLatLng);//GCJ坐标转换成百度坐标
                    MapAppUtils.openBaiDuMap((Activity) serviceMapView, myBdLatLng, baseStationInfo.getLatLng());
                }
            } else if (MapAppUtils.GAODE_MAP_NAME.equals(mapName)) {
                if (myLatLng == null || baseStationInfo.getLatLng() == null) {
                    serviceMapView.showNotLocationMessage();
                } else {
                    LatLng stationLatLng = MapUtils.transformFromBDToGCJ(baseStationInfo.getLatLng());//将WG84坐标转换成GCJ坐标
                    MapAppUtils.openGaoDeMap((Activity) serviceMapView, myLatLng, stationLatLng);
                }
            } else if (MapAppUtils.BROWSER_DOWNLOAD.equals(mapName)) {
                MapAppUtils.openBrowser((Activity) serviceMapView, MapAppUtils.DOWN_LOAD_URL);
            }
        }
    }

    /**
     * 设置我的定位点
     *
     * @param myLatLng
     */

    private LatLng setLocationIcon(LatLng myLatLng) {

        LatLng myBdLatLng = MapUtils.transformFromGCJToBD(myLatLng);//将手机定位的GCJ-02坐标转为bd09ll坐标

        MyLocationConfiguration locConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, myLocation);
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(myRadius)// 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100)
                .latitude(myBdLatLng.latitude)
                .longitude(myBdLatLng.longitude)
                .build();
        mBaiduMap.setMyLocationData(locData);
        mBaiduMap.setMyLocationConfigeration(locConfiguration);

        return myBdLatLng;
    }

    /**
     * 设置所有维修点的信息并返回position对应的覆盖物
     *
     * @param stationInfoList
     * @param position
     */
    private Marker setAllRepairStation(List<BaseStationInfo> stationInfoList, int position) {
        Marker mainMarker = null;
        int size = stationInfoList.size();
        for (int i = 0; i < size; i++) {
            BaseStationInfo baseStationInfo = stationInfoList.get(i);
            if (baseStationInfo != null) {
                Marker marker = builderMarker(baseStationInfo);
                if (i == position) {//设置position对应的覆盖物
                    mainMarker = marker;
                }
            }
        }
        return mainMarker;
    }

    /**
     * 设置维修点的位置
     *
     * @param baseStationInfo
     */
    public Marker builderMarker(BaseStationInfo baseStationInfo) {
        MarkerOptions stationLocationMarks = new MarkerOptions().position(baseStationInfo.getLatLng()).icon(stationLocationOther).zIndex(0);
        stationLocationMarks.animateType(MarkerOptions.MarkerAnimateType.grow);

        //使用marker携带信息，当点击事件的时候可以通过marker获得info信息
        Bundle bundle = new Bundle();
        bundle.putParcelable("baseStationInfo", baseStationInfo);
        Marker mMarkerD = (Marker) (mBaiduMap.addOverlay(stationLocationMarks));
        mMarkerD.setExtraInfo(bundle);
        return mMarkerD;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (serviceMapView != null) {
            if (preMarker != null) {
                preMarker.setIcon(stationLocationOther);
            }
            preMarker = marker;
            Bundle extraInfo = marker.getExtraInfo();
            baseStationInfo = extraInfo.getParcelable("baseStationInfo");

            if (baseStationInfo != null) {

                double distanceValue = 0;
                if (Constants.ServiceType.repair == type) {
                    distanceValue = ((RepairStationInfo) baseStationInfo).getDistance();
                } else if (Constants.ServiceType.charge == type) {
                    distanceValue = ((ChargingStationInfo) baseStationInfo).getDistance();
                }
                serviceMapView.updateMainStationDetailsInfo(baseStationInfo, distanceValue);
                marker.setIcon(stationLocation);
                mapStatusUpdate(baseStationInfo.getLatLng());
            }
        }
        return false;
    }

    @Override
    public void onGetBDLocationSuccess(BDLocation bdLocation) {
        if (bdLocation != null) {
            this.myLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            this.myRadius = bdLocation.getRadius();
        }
    }

    @Override
    public void onGetBDLocationFailure() {

    }
}
