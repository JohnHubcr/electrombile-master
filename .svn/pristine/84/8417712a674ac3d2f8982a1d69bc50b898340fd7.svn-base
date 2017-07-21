package com.zenchn.electrombile.engine;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.zenchn.electrombile.engine.callback.ContrailCallback;
import com.zenchn.electrombile.entity.VehicleContrailInfo;
import com.zenchn.electrombile.entity.VehicleLocationInfo;
import com.zenchn.electrombile.utils.MapUtils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * 作    者：wangr on 2017/3/1 14:18
 * 描    述：对轨迹信息进行解析的引擎
 * 修订记录：
 */
public class ContrailEngine {

    private ContrailEngine() {
    }

    private static class SingletonInstance {
        private static final ContrailEngine INSTANCE = new ContrailEngine();
    }

    public static ContrailEngine getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void analyzeContrail(List<VehicleLocationInfo> vehicleLocationInfoList, final ContrailCallback callback) {

        Observable.
                from(vehicleLocationInfoList)
                .map(new Func1<VehicleLocationInfo, VehicleContrailInfo>() {
                    @Override
                    public VehicleContrailInfo call(VehicleLocationInfo vehicleLocationInfo) {
                        return new VehicleContrailInfo(vehicleLocationInfo);
                    }
                })
                .scan(new Func2<VehicleContrailInfo, VehicleContrailInfo, VehicleContrailInfo>() {
                    @Override
                    public VehicleContrailInfo call(VehicleContrailInfo vehicleContrailInfo, VehicleContrailInfo vehicleContrailInfo2) {
                        return mergeVehicleContrailInfo(vehicleContrailInfo, vehicleContrailInfo2);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .last()
                .subscribe(new Action1<VehicleContrailInfo>() {
                    @Override
                    public void call(VehicleContrailInfo vehicleContrailInfo) {
                        if (callback != null)
                            callback.onAnalyzeCompleted(vehicleContrailInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (callback != null)
                            callback.onAnalyzeInterrupt();
                    }
                });
    }

    /**
     * 数据合并
     *
     * @param vehicleContrailInfo
     * @param vehicleContrailInfo2
     * @return
     */
    private VehicleContrailInfo mergeVehicleContrailInfo(VehicleContrailInfo vehicleContrailInfo, VehicleContrailInfo vehicleContrailInfo2) {

        VehicleLocationInfo lastVehicleLocationInfo = vehicleContrailInfo.getLastVehicleLocationInfo();
        VehicleLocationInfo lastVehicleLocationInfo1 = vehicleContrailInfo2.getLastVehicleLocationInfo();

        double distance = vehicleContrailInfo.getTotalMileage() + getDistance(lastVehicleLocationInfo, lastVehicleLocationInfo1);
        long time = vehicleContrailInfo.getTotalTime() + getTime(lastVehicleLocationInfo, lastVehicleLocationInfo1);

        return vehicleContrailInfo2.addTotalMileage(distance).addTotalTime(time);
    }

    /**
     * 根据两个轨迹点获取时间差
     *
     * @param vehicleContrailInfo
     * @param vehicleContrailInfo1
     * @return
     */
    private long getTime(VehicleLocationInfo vehicleContrailInfo, VehicleLocationInfo vehicleContrailInfo1) {
        return Math.abs(vehicleContrailInfo1.getUtcTime() - vehicleContrailInfo.getUtcTime());
    }

    /**
     * 根据两个轨迹点信息获取距离
     *
     * @param lastVehicleLocationInfo
     * @param lastVehicleLocationInfo1
     * @return
     */
    private double getDistance(VehicleLocationInfo lastVehicleLocationInfo, VehicleLocationInfo lastVehicleLocationInfo1) {
        return DistanceUtil.getDistance(getBDLatLng(lastVehicleLocationInfo), getBDLatLng(lastVehicleLocationInfo1));
    }

    /**
     * 根据两个轨迹点信息获取对应的百度坐标
     *
     * @param vehicleLocationInfo
     * @return
     */
    private LatLng getBDLatLng(VehicleLocationInfo vehicleLocationInfo) {
        return MapUtils.transformFromWGToBD(new LatLng(vehicleLocationInfo.getRawLatitude(), vehicleLocationInfo.getRawLongitude()));
    }


}
