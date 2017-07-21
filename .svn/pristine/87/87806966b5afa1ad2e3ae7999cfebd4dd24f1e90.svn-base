package com.zenchn.electrombile.widget.PickerView.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.widget.PickerView.adapter.AreaAdapter;
import com.zenchn.electrombile.widget.PickerView.lib.WheelView;
import com.zenchn.electrombile.widget.PickerView.listener.OnItemSelectedListener;
import com.zenchn.electrombile.widget.PickerView.model.AreaModel;

import java.util.Map;

/**
 * 作    者：wangr on 2017/3/10 16:59
 * 描    述：
 * 修订记录：
 */
public class WheelArea {

    private Context mContext;
    private View view;
    private WheelView wvProvince;
    private WheelView wvCity;
    private WheelView wvDistrict;

    private AreaAdapter mProvinceAdapter;
    private AreaAdapter mCityAdapter;
    private AreaAdapter mDistrictAdapter;

    Map<String, String[]> mProvinceMap = null;
    Map<String, String[]> mCityMap = null;

    private String[] mProvinceGroup;
    private String[] mCityGroup;
    private String[] mDistrictGroup;

    private String mProvinceName;
    private String mCityName;
    private String mDistrictName;

    private boolean cyclic = true;

    public WheelArea(View view) {
        super();
        this.view = view;
        this.mContext = view.getContext();
        this.mProvinceName = mContext.getString(R.string.pickerView_default_province);
        this.mCityName = mContext.getString(R.string.pickerView_default_city);
        this.mDistrictName = mContext.getString(R.string.pickerView_default_district);
    }

    public WheelArea(View view, String mProvinceName, String mCityName, String mDistrictName) {
        super();
        this.view = view;
        this.mContext = view.getContext();
        boolean isNonNull = isNonNull(mProvinceName) && isNonNull(mCityName) && isNonNull(mDistrictName);
        this.mProvinceName = isNonNull ? mProvinceName : mContext.getString(R.string.pickerView_default_province);
        this.mCityName = isNonNull ? mCityName : mContext.getString(R.string.pickerView_default_city);
        this.mDistrictName = isNonNull ? mDistrictName : mContext.getString(R.string.pickerView_default_district);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setDefaultSeat(String mProvinceName) {
        setDefaultSeat(mProvinceName, null, null);
    }

    public void setDefaultSeat(String mProvinceName, String mCityName) {
        setDefaultSeat(mProvinceName, mCityName, null);
    }

    public void setDefaultSeat(String mProvinceName, String mCityName, String mDistrictName) {
        if (isNonNull(mProvinceName)) {
            this.mProvinceName = mProvinceName;
            if (isNonNull(mCityName)) {
                this.mCityName = mCityName;
                if (isNonNull(mDistrictName))
                    this.mDistrictName = mDistrictName;
            }
        } else {
            this.mProvinceName = mContext.getString(R.string.pickerView_default_province);
            this.mCityName = mContext.getString(R.string.pickerView_default_city);
            this.mDistrictName = mContext.getString(R.string.pickerView_default_district);
        }

    }

    public void setPicker(String[] mProvinceGroup, Map<String, String[]> mProvinceMap, Map<String, String[]> mCityMap) {
        this.mCityMap = mCityMap;
        this.mProvinceMap = mProvinceMap;
        this.mProvinceGroup = mProvinceGroup;
        this.mCityGroup = mProvinceMap.get(mProvinceName);
        this.mDistrictGroup = mCityMap.get(mCityName);
        initPick();
    }

    private void initPick() {

        if (checkArea()) {
            // 省
            wvProvince = (WheelView) view.findViewById(R.id.wv_province);
//        wvProvince.setLabel(mContext.getString(R.string.pickerView_province));
            wvProvince.setAdapter(mProvinceAdapter = new AreaAdapter(mProvinceGroup));
            wvProvince.setCurrentItem(mProvinceAdapter.indexOf(mProvinceName));
            wvProvince.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(int index) {
                    mProvinceName = mProvinceGroup[index];

                    mCityGroup = mProvinceMap.get(mProvinceName);
                    mCityName = mProvinceGroup[0];
                    wvCity.setCurrentItem(0);
                    wvCity.setAdapter(mCityAdapter = new AreaAdapter(mCityGroup));

                    if (cyclic)
                        wvCity.setCyclic(mCityGroup.length > 1);

                    mDistrictGroup = mCityMap.get(mCityGroup[0]);
                    mDistrictName = mDistrictGroup[0];
                    wvDistrict.setCurrentItem(0);
                    wvDistrict.setAdapter(mDistrictAdapter = new AreaAdapter(mDistrictGroup));
                }
            });

            // 市
            wvCity = (WheelView) view.findViewById(R.id.wv_city);
//        wvCity.setLabel(mContext.getString(R.string.pickerView_city));
            wvCity.setAdapter(mCityAdapter = new AreaAdapter(mCityGroup));
            wvCity.setCurrentItem(mCityAdapter.indexOf(mCityName));
            wvCity.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    mCityName = mCityGroup[index];
                    mDistrictGroup = mCityMap.get(mCityName);
                    mDistrictName = mDistrictGroup[0];
                    wvDistrict.setCurrentItem(0);
                    wvDistrict.setAdapter(mDistrictAdapter = new AreaAdapter(mDistrictGroup));
                }
            });

            // 区
            wvDistrict = (WheelView) view.findViewById(R.id.wv_district);
//        wvDistrict.setLabel(mContext.getString(R.string.pickerView_district));
            wvDistrict.setAdapter(mDistrictAdapter = new AreaAdapter(mDistrictGroup));
            wvDistrict.setCurrentItem(mDistrictAdapter.indexOf(mDistrictName));
            wvDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(int index) {
                    mDistrictName = mDistrictGroup[index];
                }
            });
        } else {
            throw new IllegalArgumentException("Urban areas do not match !");
        }
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
        wvProvince.setCyclic(cyclic);
        wvCity.setCyclic(cyclic);
        wvDistrict.setCyclic(cyclic);
    }

    /**
     * 获取选择的地理位置
     *
     * @return
     */
    public AreaModel getArea() {
        return new AreaModel(mProvinceName, mCityName, mDistrictName);
    }

    /**
     * 判断一个字符串有内容(非空且非空字符串)
     *
     * @param character
     * @return
     */
    private boolean isNonNull(String character) {
        return null != character && !TextUtils.isEmpty(character.trim());
    }

    /**
     * 检查地理位置是否正确
     *
     * @return
     */
    private boolean checkArea() {
        return contains(mProvinceGroup, mProvinceName) && contains(mCityGroup, mCityName) && contains(mDistrictGroup, mDistrictName);
    }

    /**
     * 判断数组中是否包含
     *
     * @param src
     * @param o
     * @return
     */
    private boolean contains(String[] src, String o) {
        if (null == o)
            return false;
        int length = src.length;
        for (int i = 0; i < length; i++) {
            if (o.equals(src[i]))
                return true;
        }
        return false;
    }

}
