package com.zenchn.electrombile.presenter.impl;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.zenchn.electrombile.api.ListApi;
import com.zenchn.electrombile.api.callback.ApiListCallback;
import com.zenchn.electrombile.entity.RepairStationInfo;
import com.zenchn.electrombile.presenter.RepairOutletsPresenter;
import com.zenchn.electrombile.ui.view.RepairOutletsView;
import com.zenchn.electrombile.wrapper.BDLocationWrapper;
import com.zenchn.electrombile.wrapper.callback.BDLocationCallback;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/4 11:21
 * 描    述：
 * 修订记录：
 */
public class RepairOutletsPresenterImpl implements RepairOutletsPresenter, BDLocationCallback, ApiListCallback<List<RepairStationInfo>> {

    private RepairOutletsView repairOutletsView;
    private boolean isRefresh;

    public RepairOutletsPresenterImpl(RepairOutletsView repairOutletsView) {
        this.repairOutletsView = repairOutletsView;
    }

    @Override
    public void getBDLocation(Context mContext) {
        if (repairOutletsView != null) {
            BDLocationWrapper
                    .getInstance()
                    .setCallback(this)
                    .getBDLocation(mContext);
        }
    }

    @Override
    public void searchServiceStation(String accessToken, BDLocation bdLocation, int pageNo, int pageSize, int serviceType, boolean isRefresh) {
        if (repairOutletsView != null) {
            this.isRefresh = isRefresh;
            repairOutletsView.showProgress();
            ListApi
                    .getInstance()
                    .getRepairServiceStationList(accessToken, bdLocation, pageNo, pageSize, serviceType, this);
        }
    }

    @Override
    public void onDestroy() {
        repairOutletsView = null;
    }

    @Override
    public void onGetBDLocationSuccess(BDLocation bdLocation) {
        if (repairOutletsView != null) {
            repairOutletsView.getBDLocationSuccess(bdLocation);
        }
    }

    @Override
    public void onGetBDLocationFailure() {
        if (repairOutletsView != null) {
            repairOutletsView.showLocationFailure();
        }
    }

    @Override
    public void onSuccess(List<RepairStationInfo> data, int pageNumber, int totalPages) {
        if (repairOutletsView != null) {
            repairOutletsView.hideProgress();
            if (isRefresh)
                repairOutletsView.onRefreshCompleted();
            repairOutletsView.onSearchSuccess(data, pageNumber, totalPages);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (repairOutletsView != null) {
            repairOutletsView.hideProgress();
            if (isRefresh)
                repairOutletsView.onRefreshCompleted();
            repairOutletsView.onLoadError();
            repairOutletsView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (repairOutletsView != null) {
            repairOutletsView.hideProgress();
            if (isRefresh)
                repairOutletsView.onRefreshCompleted();
            repairOutletsView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (repairOutletsView != null) {
            repairOutletsView.onGrantRefuse();
        }
    }

}
