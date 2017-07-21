package com.zenchn.electrombile;

import android.test.InstrumentationTestCase;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by wangr on 2016/11/25.
 */
public class MyTest extends InstrumentationTestCase {

    private static final double EARTH_RADIUS = 6378137.0;

    public void testDistance() {

        LatLng sLatlng = new LatLng(29.105308713905d, 119.621093940008d);
        LatLng elatlng = new LatLng(29.1055331368687d, 119.619947878872d);

        double distanceJustGPS = getDistanceJustGPS(sLatlng, elatlng);

    }

    public static double getDistanceJustGPS(LatLng sLatLng, LatLng eLatLng) {
        return getDistanceJustGPS(sLatLng.latitude, sLatLng.longitude, eLatLng.latitude, eLatLng.longitude);
    }

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
}
