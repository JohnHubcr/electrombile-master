package com.zenchn.electrombile.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseMapFragment extends BaseFragment {

    private MapView mMapView;
    protected BaiduMap mBaiduMap;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMap();//加载地图配置
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if (mMapView != null)
            mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if (mMapView != null)
            mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mMapView != null)
            mMapView.onDestroy();
    }

    protected void loadMap() {
        mMapView = getMapView();
        if (mMapView != null) {
            // 去除放大缩小按钮
            mMapView.showZoomControls(false);
            // 缩放比率、默认中心点
            mBaiduMap = mMapView.getMap();
            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(16.0f);// 缩放比率
            mBaiduMap.setMapStatus(msu);
            initMapData();
        }
    }

    //引入子类的地图对象
    protected abstract MapView getMapView();

    //初始化地图数据
    protected abstract void initMapData();
}
