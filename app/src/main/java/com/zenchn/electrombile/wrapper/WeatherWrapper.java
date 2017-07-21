package com.zenchn.electrombile.wrapper;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.api.WeatherApi;
import com.zenchn.electrombile.api.callback.WeatherCallback;
import com.zenchn.electrombile.kit.LogKit;
import com.zenchn.mlibrary.log.LogUtils;

/**
 * 作    者：wangr on 2017/2/24 22:19
 * 描    述：对获取本地天气进行封装
 * 修订记录：
 */
public class WeatherWrapper implements BDLocationListener {

    private WeatherCallback callback;
    private LocationClient mLocClient;
    private int errorCount;

    private WeatherWrapper() {
    }

    public static WeatherWrapper getInstance() {
        return new WeatherWrapper();
    }

    public void getWeather(Context mContext, WeatherCallback callback) {
        this.callback = callback;
        getBDLocation(mContext);
    }

    /**
     * 调用百度地图定位
     *
     * @param mContext (必须要是getApplicationContext（），否则无法正常定位)
     */
    public void getBDLocation(Context mContext) {
        mLocClient = new LocationClient(mContext.getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setScanSpan(5000);//定位间隔
        option.setCoorType("bd09ll"); // 设置坐标类型
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
        if (bdLocation != null && !TextUtils.isEmpty(bdLocation.getCity())) {
            LogKit.success("定位成功", "我的位置：" + "省份：" + bdLocation.getProvince() + "\n城市：" + bdLocation.getCity() + "\n街道：" + bdLocation.getStreet());
            LogUtils.printCustomLog(BuildConf.LogTags.WEATHER_TAG, "我的位置：" + "省份：" + bdLocation.getProvince() + "\n城市：" + bdLocation.getCity() + "\n街道：" + bdLocation.getStreet());
            if (callback != null)
                callback.onGetBDLocationSuccess(bdLocation);
            getJHWeather(bdLocation.getProvince(), bdLocation.getCity());
            mLocClient.stop();
            errorCount = 0;//重置错误计数
        } else {
            errorCount++;
            LogKit.success("定位失败", "失败计数：" + errorCount + "次数");
            if (errorCount < 5)
                mLocClient.start();
            else
                errorCount = 0;//重置错误计数
        }
    }

    /**
     * 调用聚合天气
     *
     * @param province
     * @param city
     */
    private void getJHWeather(String province, String city) {
        if (callback != null)
            WeatherApi
                    .getInstance()
                    .getWeatherInfoByCity(province, city, callback);
    }

}
