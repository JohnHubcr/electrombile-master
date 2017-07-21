package com.zenchn.electrombile.ui.view;

import com.baidu.location.BDLocation;
import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.entity.RepairStationInfo;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/4 11:22
 * 描    述：
 * 修订记录：
 */

public interface RepairOutletsView extends BaseView{

    /**
     * 没有获取到定位数据
     */
    void showLocationFailure();

    /**
     * 获取服务点信息
     *
     * @param bdLocation
     */
    void getBDLocationSuccess(BDLocation bdLocation);

    /**
     * 获取服务点成功
     *
     * @param stationInfoList
     * @param pageNumber
     * @param totalPages
     */
    void onSearchSuccess(List<RepairStationInfo> stationInfoList, int pageNumber, int totalPages);

    /**
     * 刷新完成
     */
    void onRefreshCompleted();

    /**
     * 加载失败
     */
    void onLoadError();
}
