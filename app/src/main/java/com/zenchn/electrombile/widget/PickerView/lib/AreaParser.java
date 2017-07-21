package com.zenchn.electrombile.widget.PickerView.lib;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangr on 2017/3/11 0011.
 */
public class AreaParser {

    public static final String COUNTRY_KEY = "countryMap";
    public static final String PROVINCE_KEY = "provinceMap";
    public static final String CITY_KEY = "cityMap";
    public static final String COUNTRY_ID = "CHINA";

    public static HashMap<String, Map<String, String[]>> getAreaMap(Context context) {

        Map<String, String[]> mCountryMap = null;
        Map<String, String[]> mProvinceMap = null;
        Map<String, String[]> mCityMap = null;
        HashMap<String, Map<String, String[]>> mArea = null;

        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = context.getAssets().open("data/area.json");
            int len;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "GBK"));
            }
            is.close();

            JSONObject jsonObject = JSON.parseObject(sb.toString());
            JSONArray provinceArray = jsonObject.getJSONArray("province");

            if (isNonNull(provinceArray)) {

                int provinceSize = provinceArray.size();
                String[] mProvinceGroup = new String[provinceSize];

                mArea = new LinkedHashMap<>();
                mCountryMap = new LinkedHashMap<>();
                mProvinceMap = new LinkedHashMap<>();
                mCityMap = new LinkedHashMap<>();

                for (int i = 0; i < provinceSize; i++) {

                    JSONObject provinceObj = provinceArray.getJSONObject(i);
                    String mProvinceName = provinceObj.getString("name");
                    mProvinceGroup[i] = mProvinceName;

                    JSONArray cityArray = provinceObj.getJSONArray("city");
                    if (isNonNull(cityArray)) {

                        int citySize = cityArray.size();
                        String[] mCityGroup = new String[citySize];

                        for (int j = 0; j < citySize; j++) {

                            JSONObject cityObj = cityArray.getJSONObject(j);
                            String mCityName = cityObj.getString("name");
                            mCityGroup[j] = mCityName;

                            JSONArray districtArray = cityObj.getJSONArray("district");
                            if (isNonNull(districtArray)) {

                                int districtSize = districtArray.size();
                                String[] mDistrictGroup = new String[districtSize];

                                for (int k = 0; k < districtSize; k++) {

                                    JSONObject districtObj = districtArray.getJSONObject(k);
                                    String mDistrictName = districtObj.getString("name");

                                    mDistrictGroup[k] = mDistrictName;
                                }
                                mCityMap.put(mCityName, mDistrictGroup);
                            }
                        }
                        mProvinceMap.put(mProvinceName, mCityGroup);
                    }
                }
                mCountryMap.put(COUNTRY_ID, mProvinceGroup);

                mArea.put(COUNTRY_KEY, mCountryMap);
                mArea.put(PROVINCE_KEY, mProvinceMap);
                mArea.put(CITY_KEY, mCityMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mArea;
    }

    public static boolean isNonNull(JSONArray jsonArray) {
        return null != jsonArray && !jsonArray.isEmpty();
    }

}
