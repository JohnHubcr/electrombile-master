package com.zenchn.electrombile.wrapper;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.electrombile.wrapper.callback.BDLocationCallback;
import com.zenchn.mlibrary.log.LogUtils;

/**
 * 作    者：wangr on 2017/3/3 23:02
 * 描    述：
 * 修订记录：
 */
public class BDLocationWrapper implements BDLocationListener {

    private LocationClient mLocClient;
    private BDLocationCallback callback;

    private boolean isRepeat;
    private int errorCount;

    private BDLocationWrapper() {
    }

    public BDLocationWrapper setCallback(BDLocationCallback callback) {
        this.callback = callback;
        return this;
    }

    public static BDLocationWrapper getInstance() {
        return new BDLocationWrapper();
    }

    /**
     * 调用百度地图定位（默认坐标：gcj02）
     *
     * @param mContext
     */
    public void getBDLocation(Context mContext) {
        getBDLocation(mContext, "gcj02", false, callback);
    }

    /**
     * 调用百度地图定位（默认坐标：gcj02）
     *
     * @param mContext
     */
    public void getBDLocationSingle(Context mContext, BDLocationCallback callback) {
        getBDLocation(mContext, "gcj02", false, callback);
    }

    /**
     * 调用百度地图定位（默认坐标：gcj02）
     *
     * @param mContext
     */
    public BDLocationWrapper getBDLocationRepeat(Context mContext, BDLocationCallback callback) {
        this.callback = callback;
        getBDLocation(mContext, "gcj02", true, callback);
        return this;
    }

    /**
     * 调用百度地图定位(指定坐标类型)
     *
     * @param mContext
     */
    public void getBDLocationSingle(Context mContext, String coorType, BDLocationCallback callback) {
        getBDLocation(mContext, coorType, false, callback);
    }

    /**
     * 调用百度地图定位(指定坐标类型)
     *
     * @param mContext
     */
    public void getBDLocationRepeat(Context mContext, String coorType, BDLocationCallback callback) {
        getBDLocation(mContext, coorType, true, callback);
    }

    /**
     * 调用百度地图定位(指定坐标类型)
     *
     * @param mContext
     */
    public void getBDLocation(Context mContext, String coorType, boolean isRepeat, BDLocationCallback callback) {
        this.callback = callback;
        this.isRepeat = isRepeat;
        getBDLocation(mContext, coorType);
    }

    /**
     * 调用百度地图定位
     *
     * @param mContext (必须要是getApplicationContext（），否则无法正常定位)
     */
    public void getBDLocation(Context mContext, String coorType) {
        mLocClient = new LocationClient(mContext.getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setScanSpan(5000);//定位间隔
        option.setCoorType(coorType); // 设置坐标类型
        option.setIsNeedAddress(true);//设置需要地址信息，不设置为默认不需要
        option.disableCache(true); // 禁用启用缓存定位数据
        // 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度定位
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(this);
        mLocClient.start();
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation != null) {
            LogKit.success("定位成功", "我的位置：" + "省份：" + bdLocation.getProvince() + "\n城市：" + bdLocation.getCity() + "\n街道：" + bdLocation.getStreet());
            LogUtils.printCustomLog(BuildConf.LogTags.WEATHER_TAG, "我的位置：" + "省份：" + bdLocation.getProvince() + "\n城市：" + bdLocation.getCity() + "\n街道：" + bdLocation.getStreet());
            if (callback != null)
                callback.onGetBDLocationSuccess(bdLocation);
            if (!isRepeat)
                mLocClient.stop();
            errorCount = 0;//重置错误计数
        } else {
            errorCount++;
            LogKit.success("定位失败", "失败计数：" + errorCount + "次数");
            if (errorCount < 5)
                mLocClient.start();
            else {
                mLocClient.stop();
                errorCount = 0;//重置错误计数
                if (callback != null)
                    callback.onGetBDLocationFailure();
            }
        }
    }

    /**
     * 配合activity的生命周期方法使用
     */
    public void onLocationPause() {
        if (mLocClient != null)
            mLocClient.stop();
    }

    /**
     * 配合activity的生命周期方法使用
     */
    public void onLocationResume() {
        if (mLocClient != null)
            mLocClient.start();
    }

    /**
     * 配合activity的生命周期方法使用
     */
    public void onLocationFinish() {
        if (mLocClient != null)
            mLocClient.stop();
    }
}
