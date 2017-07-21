package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.BinderApi;
import com.zenchn.electrombile.api.VehicleInfoApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.api.callback.SetCommonCallback;
import com.zenchn.electrombile.api.callback.UnbindCallback;
import com.zenchn.electrombile.entity.VehicleRecordInfo;
import com.zenchn.electrombile.presenter.VehicleListPresenter;
import com.zenchn.electrombile.ui.view.VehicleListView;

import java.util.List;

/**
 * 作    者：wangr on 2017/2/28 16:04
 * 描    述：
 * 修订记录：
 */
public class VehicleListPresenterImpl implements VehicleListPresenter, ApiCallback<List<VehicleRecordInfo>>, SetCommonCallback, UnbindCallback {

    private VehicleListView vehicleListView;

    public VehicleListPresenterImpl(VehicleListView vehicleListView) {
        this.vehicleListView = vehicleListView;
    }

    @Override
    public void getVehicleList(String accessToken) {
        if (vehicleListView != null) {
            vehicleListView.showProgress();
            VehicleInfoApi
                    .getInstance()
                    .getVehicleList(accessToken, this);
        }
    }

    @Override
    public void setCommonVehicle(String accessToken, String id, String oldId) {
        if (vehicleListView != null) {
            vehicleListView.showProgress();
            VehicleInfoApi
                    .getInstance()
                    .setCommonVehicle(accessToken, id, oldId, this);
        }
    }

    @Override
    public void unBindEquipment(String accessToken, VehicleRecordInfo vehicleRecordInfo) {
        if (vehicleListView != null) {
            vehicleListView.showProgress();
            BinderApi
                    .getInstance()
                    .unBindEquipment(accessToken, vehicleRecordInfo, this);
        }
    }

    @Override
    public void onDestroy() {
        vehicleListView = null;
    }

    @Override
    public void onSuccess(List<VehicleRecordInfo> data) {
        if (vehicleListView != null) {
            vehicleListView.hideProgress();
            vehicleListView.showVehicleList(data);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (vehicleListView != null) {
            vehicleListView.hideProgress();
            vehicleListView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (vehicleListView != null) {
            vehicleListView.hideProgress();
            vehicleListView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (vehicleListView != null) {
            vehicleListView.hideProgress();
            vehicleListView.onGrantRefuse();
        }
    }

    @Override
    public void onSetCommonResponse(Boolean result) {
        if (vehicleListView != null) {
            vehicleListView.hideProgress();
            if (result == null || !result)
                vehicleListView.onSetCommonVehicleResult(false);
            else
                vehicleListView.onSetCommonVehicleResult(true);
        }
    }

    @Override
    public void onUnbindResponse(Boolean result) {
        if (vehicleListView != null) {
            vehicleListView.hideProgress();
            if (result == null || !result)
                vehicleListView.onUnbindResponse(false);
            else
                vehicleListView.onUnbindResponse(true);
        }
    }
}
