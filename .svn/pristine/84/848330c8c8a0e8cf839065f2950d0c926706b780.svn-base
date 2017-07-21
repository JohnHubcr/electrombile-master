package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForJKInfo;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForSTInfo;

/**
 * 作    者：wangr on 2017/3/13 13:54
 * 描    述：
 * 修订记录：
 */
public interface VehicleCheckView extends BaseView {

    /**
     * 车辆检测完成
     */
    void setCheckCompleted();

    /**
     * 解析车辆自检信息成功
     *
     * @param vehicleCheckResultForJKInfo
     */
    void onParserVehicleCheckSuccess(VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo);

    /**
     * 解析车辆自检信息成功
     *
     * @param vehicleCheckResultForSTInfo
     */
    void onParserVehicleCheckSuccess(VehicleCheckResultForSTInfo vehicleCheckResultForSTInfo);

    /**
     * 当前扫描的条目标题
     *
     * @param title
     */
    void setCurrentScanTitle(String title);

    /**
     * 当前扫描出现的故障数
     *
     * @param troubleCount
     */
    void setMotorGrade(int troubleCount);
}
