package com.zenchn.electrombile.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;

/**
 * 地图工具类
 *
 * @author WangRui
 */

/**
 * 各地图API坐标系统比较与转换;
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
 * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
 * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
 * 谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系;
 * BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
 * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。
 */
public class MapUtils {

    private static final double EARTH_RADIUS = 6378137.0;
    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;

    /**
     * 计算两点间距离
     *
     * @param sLatLng
     * @param eLatLng
     * @return
     */
    public static double getDistanceJustGPS(LatLng sLatLng, LatLng eLatLng) {
        return getDistanceJustGPS(sLatLng.latitude, sLatLng.longitude, eLatLng.latitude, eLatLng.longitude);
    }

    /**
     * 计算两点间距离
     *
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    public static double getDistanceJustGPS(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 将GCJ-02坐标转换成百度坐标
     * (GCJ-02坐标:google中国地图、soso地图、aliyun地图、mapabc地图和amap地图 )
     *
     * @param sourceLatLng
     * @return
     */
    public static LatLng transformFromGCJToBD(LatLng sourceLatLng) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordType.COMMON);
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    /**
     * 将百度坐标转换成GCJ-02坐标（待验证）
     *
     * @param sourceLatLng
     * @return
     */
    public static LatLng transformFromBDToGCJ(LatLng sourceLatLng) {
        double bd_lat = sourceLatLng.latitude;
        double bd_lon = sourceLatLng.longitude;

        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new LatLng(gg_lat, gg_lon);
    }

    /**
     * 将GPS设备采集的原始GPS坐标(WGS84坐标)转换成百度坐标
     *
     * @param sourceLatLng
     * @return
     */
    public static LatLng transformFromWGToBD(LatLng sourceLatLng) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordType.GPS);
        // sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    /**
     * WGS84坐标转换为高德地图能够识别的GCJ-02坐标
     *
     * @param wgLoc
     * @return
     */
    public static LatLng transformFromWGSToGCJ(LatLng wgLoc) {

        // 如果在国外，则默认不进行转换
        if (outOfChina(wgLoc.latitude, wgLoc.longitude)) {
            return new LatLng(wgLoc.latitude, wgLoc.longitude);
        }
        double dLat = transformLat(wgLoc.longitude - 105.0, wgLoc.latitude - 35.0);
        double dLon = transformLon(wgLoc.longitude - 105.0, wgLoc.latitude - 35.0);
        double radLat = wgLoc.latitude / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);

        return new LatLng(wgLoc.latitude + dLat, wgLoc.longitude + dLon);
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(x > 0 ? x : -x);
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(x > 0 ? x : -x);
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * @param lat
     * @param lon
     * @return
     */
    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }
}