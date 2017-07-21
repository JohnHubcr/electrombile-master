package com.zenchn.electrombile.widget.PickerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.widget.PickerView.lib.AreaParser;
import com.zenchn.electrombile.widget.PickerView.model.AreaModel;
import com.zenchn.electrombile.widget.PickerView.view.BasePickerView;
import com.zenchn.electrombile.widget.PickerView.view.WheelArea;

import java.util.HashMap;
import java.util.Map;

/**
 * 作    者：wangr on 2017/3/10 15:48
 * 描    述：
 * 修订记录：
 */
public class AreaPickerView extends BasePickerView implements View.OnClickListener {

    private WheelArea wheelArea;
    private TextView tvTitle;
    private OnAreaSelectListener onAreaSelectListener;

    public AreaPickerView(Context context) {

        super(context);

        LayoutInflater.from(context).inflate(R.layout.pickerview_area, contentContainer);

        // -----确定和取消按钮
        findViewById(R.id.btnSubmit).setOnClickListener(this);
        findViewById(R.id.btnCancel).setOnClickListener(this);

        // 顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        // ----省市区转轮
        View areaPickerView = findViewById(R.id.ll_area_picker);
        wheelArea = new WheelArea(areaPickerView);

        // 初始化数据
        HashMap<String, Map<String, String[]>> areaMap = AreaParser.getAreaMap(context);
        Map<String, String[]> mCountryMap = areaMap.get(AreaParser.COUNTRY_KEY);
        String[] mProvinceGroup = mCountryMap.get(AreaParser.COUNTRY_ID);
        Map<String, String[]> mProvinceMap = areaMap.get(AreaParser.PROVINCE_KEY);
        Map<String, String[]> mCityMap = areaMap.get(AreaParser.CITY_KEY);
        wheelArea.setPicker(mProvinceGroup, mProvinceMap, mCityMap);

    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public AreaPickerView setCyclic(boolean cyclic) {
        wheelArea.setCyclic(cyclic);
        return this;
    }

    /**
     * 设置默认显示位置
     *
     * @param mProvinceName
     * @param mCityName
     * @return
     */
    public AreaPickerView setDefaultSeat(String mProvinceName, String mCityName, String mDistrictName) {
        wheelArea.setDefaultSeat(mProvinceName, mCityName, mDistrictName);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSubmit:
                if (onAreaSelectListener != null)
                    onAreaSelectListener.onAreaSelect(wheelArea.getArea());
                dismiss();
                break;

            case R.id.btnCancel:
                dismiss();
                break;
        }
    }

    public interface OnAreaSelectListener {

        void onAreaSelect(AreaModel areaModel);

    }

    public AreaPickerView setOnAreaSelectListener(OnAreaSelectListener onAreaSelectListener) {
        this.onAreaSelectListener = onAreaSelectListener;
        return this;
    }

    public AreaPickerView setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

}
