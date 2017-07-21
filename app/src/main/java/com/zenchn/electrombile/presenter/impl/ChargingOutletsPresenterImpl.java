package com.zenchn.electrombile.presenter.impl;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.zenchn.electrombile.api.ListApi;
import com.zenchn.electrombile.api.callback.ApiListCallback;
import com.zenchn.electrombile.entity.ChargingStationInfo;
import com.zenchn.electrombile.presenter.ChargingOutletsPresenter;
import com.zenchn.electrombile.ui.view.ChargingOutletsView;
import com.zenchn.electrombile.wrapper.BDLocationWrapper;
import com.zenchn.electrombile.wrapper.callback.BDLocationCallback;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/3 23:16
 * 描    述：
 * 修订记录：
 */
public class ChargingOutletsPresenterImpl implements ChargingOutletsPresenter, BDLocationCallback, ApiListCallback<List<ChargingStationInfo>> {

    private ChargingOutletsView chargingOutletsView;
    private boolean isRefresh;

    public ChargingOutletsPresenterImpl(ChargingOutletsView chargingOutletsView) {
        this.chargingOutletsView = chargingOutletsView;
    }

    @Override
    public void getBDLocation(Context mContext) {
        if (chargingOutletsView != null) {
            BDLocationWrapper
                    .getInstance()
                    .setCallback(this)
                    .getBDLocation(mContext);
        }
    }

    @Override
    public void searchServiceStation(String accessToken, BDLocation bdLocation, int pageNo, int pageSize, int serviceType, boolean isRefresh) {
        if (chargingOutletsView != null) {
            this.isRefresh = isRefresh;
            chargingOutletsView.showProgress();
            ListApi
                    .getInstance()
                    .getChargeServiceStationList(accessToken, bdLocation, pageNo, pageSize, serviceType, this);
        }
    }

    @Override
    public void onDestroy() {
        chargingOutletsView = null;
    }

    @Override
    public void onGetBDLocationSuccess(BDLocation bdLocation) {
        if (chargingOutletsView != null) {
            chargingOutletsView.getBDLocationSuccess(bdLocation);
        }
    }

    @Override
    public void onGetBDLocationFailure() {
        if (chargingOutletsView != null) {
            chargingOutletsView.showLocationFailure();
        }
    }

    @Override
    public void onSuccess(List<ChargingStationInfo> data, int pageNumber, int totalPages) {
        if (chargingOutletsView != null) {
            chargingOutletsView.hideProgress();
            if (isRefresh)
                chargingOutletsView.onRefreshCompleted();
            chargingOutletsView.onSearchSuccess(data, pageNumber, totalPages);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (chargingOutletsView != null) {
            chargingOutletsView.hideProgress();
            if (isRefresh)
                chargingOutletsView.onRefreshCompleted();
            chargingOutletsView.onLoadError();
            chargingOutletsView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (chargingOutletsView != null) {
            chargingOutletsView.hideProgress();
            if (isRefresh)
                chargingOutletsView.onRefreshCompleted();
            chargingOutletsView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (chargingOutletsView != null) {
            chargingOutletsView.onGrantRefuse();
        }
    }
}
