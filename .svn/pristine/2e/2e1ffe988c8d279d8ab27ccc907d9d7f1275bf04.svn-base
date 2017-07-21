package com.zenchn.electrombile.presenter.impl;

import android.app.Activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.entity.VehicleLocationInfo;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.presenter.VehicleTrackingPresenter;
import com.zenchn.electrombile.ui.view.VehicleTrackingView;
import com.zenchn.electrombile.utils.MapAppUtils;
import com.zenchn.electrombile.utils.MapUtils;

/**
 * 作    者：wangr on 2017/2/28 11:14
 * 描    述：
 * 修订记录：
 */
public class VehicleTrackingPresenterImpl implements VehicleTrackingPresenter, BDLocationListener, BaiduMap.OnMapLoadedCallback {

    private VehicleTrackingView vehicleTrackingView;

    private BaiduMap mBaiduMap;

    private LatLng myLatLng;//我的位置（GCJ-02坐标）
    private LatLng myBdLatLng;//我的位置（BD09ll坐标）

    private LatLng vehicleRawLatLng;//车的GPS位置（WGS84坐标）
    private LatLng vehicleBdLatLng;//车的百度位置（BD09ll坐标）
    private LatLng vehiclePreBdLatLng;//车的上一次百度位置

    private int onlineStatus;// 车辆在线状态

    private BitmapDescriptor vehicleIoc = BitmapDescriptorFactory.fromResource(R.drawable.moto_location);
    private BitmapDescriptor motorOfflineIoc = BitmapDescriptorFactory.fromResource(R.drawable.moto_location_gray);
    private BitmapDescriptor myLocation = BitmapDescriptorFactory.fromResource(R.drawable.location_map);

    private MarkerOptions onlineMarker;
    private MarkerOptions offlineMarker;

    private LocationClient mLocClient;
    private Marker mMarkerD;
    private boolean isMyLocationOn;//是否以人的定位为中心点

    public VehicleTrackingPresenterImpl(VehicleTrackingView vehicleTrackingView) {
        this.vehicleTrackingView = vehicleTrackingView;
    }

    @Override
    public void onDestroy() {
        if (mLocClient != null) {
            mLocClient.stop();
            mLocClient.unRegisterLocationListener(this);
            mLocClient = null;
        }
        vehicleTrackingView = null;
    }

    @Override
    public void onLocationPause() {
        if (mLocClient != null) {
            mLocClient.stop();
        }
    }

    @Override
    public void onLocationResume() {
        if (mLocClient != null) {
            mLocClient.start();
        }
    }

    @Override
    public void showMyLocation(boolean isLocationOn) {
        if (vehicleTrackingView != null) {
            this.isMyLocationOn = isLocationOn;
            if (isLocationOn) {
                if (myLatLng == null) {
                    vehicleTrackingView.showWaitForLocation();
                } else {
                    MyLocationData locData = new MyLocationData.Builder().direction(100).latitude(myBdLatLng.latitude).longitude(myBdLatLng.longitude).build();
                    mBaiduMap.setMyLocationData(locData);
                    mapStatusUpdate(myBdLatLng);
                }
            } else {
                if (vehicleBdLatLng != null)
                    mapStatusUpdate(vehicleBdLatLng);
            }
        }
    }

    @Override
    public void initMap(BaiduMap mBaiduMap) {
        this.mBaiduMap = mBaiduMap;

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);// 缩放比率
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMyLocationEnabled(true);

        MyLocationConfiguration locConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, myLocation);
        mBaiduMap.setMyLocationConfigeration(locConfiguration);

        if (vehicleTrackingView != null)
            vehicleTrackingView.showProgress();

        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
    }

    @Override
    public void initBDLocation(LocationClient mLocClient) {
        // 定位初始化
        this.mLocClient = mLocClient;
        mLocClient.registerLocationListener(this);

        // 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(5000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(false);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(false);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void toNavigation(String mapName) {
        if (vehicleTrackingView != null) {
            if (MapAppUtils.BAIDU_MAP_NAME.equals(mapName)) {
                if (myLatLng == null || vehicleBdLatLng == null) {
                    vehicleTrackingView.showNotLocationMessage();
                } else {
                    LatLng myBdLatLng = MapUtils.transformFromGCJToBD(myLatLng);//GCJ坐标转换成百度坐标
                    MapAppUtils.openBaiDuMap((Activity) vehicleTrackingView, myBdLatLng, vehicleBdLatLng);
                }
            } else if (MapAppUtils.GAODE_MAP_NAME.equals(mapName)) {
                if (myLatLng == null || vehicleRawLatLng == null) {
                    vehicleTrackingView.showNotLocationMessage();
                } else {
                    LatLng motorLatLng = MapUtils.transformFromWGSToGCJ(vehicleRawLatLng);//讲WG84坐标转换成GCJ坐标
                    MapAppUtils.openGaoDeMap((Activity) vehicleTrackingView, myLatLng, motorLatLng);
                }
            } else if (MapAppUtils.BROWSER_DOWNLOAD.equals(mapName)) {
                MapAppUtils.openBrowser((Activity) vehicleTrackingView, MapAppUtils.DOWN_LOAD_URL);
            }
        }
    }

    @Override
    public void updateVehicleLocation(VehicleLocationInfo vehicleLocationInfo) {
        if (vehicleTrackingView != null) {
            if (vehicleLocationInfo != null) {
                vehicleBdLatLng = new LatLng(vehicleLocationInfo.getBdLatitude(), vehicleLocationInfo.getBdLongitude());
                vehicleRawLatLng = new LatLng(vehicleLocationInfo.getRawLatitude(), vehicleLocationInfo.getRawLongitude());
                LogKit.logLocation("车辆定位坐标(bd0911)：" + vehicleBdLatLng.toString());
                LogKit.logLocation("车辆定位坐标(gcj02):" + vehicleRawLatLng.toString());
                onlineStatus = vehicleLocationInfo.getOnlineStatus();
                refreshMarker(vehicleBdLatLng);// 刷新车辆位置
            }
        }
    }

    /**
     * 刷新车的位置
     *
     * @param vehicleBdLatLng
     */
    public void refreshMarker(LatLng vehicleBdLatLng) {

        if (vehicleBdLatLng != vehiclePreBdLatLng) {//车辆位置改变

            if (mMarkerD != null)
                mMarkerD.remove();
            if (mBaiduMap != null)
                mBaiduMap.clear();

            if (Constants.OFFLINE_STATUS == onlineStatus) {//车辆离线

                offlineMarker = new MarkerOptions().position(vehicleBdLatLng).icon(motorOfflineIoc).zIndex(0);
                offlineMarker.animateType(MarkerOptions.MarkerAnimateType.grow);

                mMarkerD = (Marker) (mBaiduMap.addOverlay(offlineMarker));
            } else {//车辆在线

                onlineMarker = new MarkerOptions().position(vehicleBdLatLng).icon(vehicleIoc).zIndex(0);
                onlineMarker.animateType(MarkerOptions.MarkerAnimateType.grow);

                mMarkerD = (Marker) (mBaiduMap.addOverlay(onlineMarker));
            }
            vehiclePreBdLatLng = vehicleBdLatLng;//更新最后一次车辆位置
            if (!isMyLocationOn)
                mapStatusUpdate(vehicleBdLatLng);
        }
    }

    /**
     * 默认中心点
     */
    @Override
    public void mapStatusUpdate(LatLng centerLatLng) {
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(centerLatLng);
        mBaiduMap.animateMapStatus(u);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        // map view 销毁后不在处理新接收的位置
        if (vehicleTrackingView != null) {
            if (bdLocation != null) {
                myLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                myBdLatLng = MapUtils.transformFromGCJToBD(myLatLng);//将手机定位的GCJ-02坐标转为bd09ll坐标

                LogKit.logLocation("百度定位坐标(gcj02)：" + myLatLng.toString());

                if (isMyLocationOn) {
                    mBaiduMap.setMyLocationData(new MyLocationData.Builder()
                            .accuracy(bdLocation.getRadius())
                            .direction(100)
                            .latitude(myBdLatLng.latitude)
                            .longitude(myBdLatLng.longitude)
                            .build());
                }

            }
        }
    }

    @Override
    public void onMapLoaded() {
        if (vehicleTrackingView != null)
            vehicleTrackingView.hideProgress();
    }
}
