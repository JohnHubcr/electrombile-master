package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.VehicleCoreApi;
import com.zenchn.electrombile.api.callback.VehicleContrailCallback;
import com.zenchn.electrombile.entity.VehicleLocationInfo;
import com.zenchn.electrombile.presenter.VehicleContrailPresenter;
import com.zenchn.electrombile.ui.view.VehicleContrailView;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/1 20:57
 * 描    述：
 * 修订记录：
 */
public class VehicleContrailPresenterImpl implements VehicleContrailPresenter, VehicleContrailCallback {

    private VehicleContrailView vehicleContrailView;

    public VehicleContrailPresenterImpl(VehicleContrailView vehicleContrailView) {
        this.vehicleContrailView = vehicleContrailView;
    }

    @Override
    public void onDestroy() {
        vehicleContrailView = null;
    }

    @Override
    public void getVehicleContrail(String accessToken, String serialNumber, int equModel, long beginTime, long endTime) {
        if (vehicleContrailView != null) {
            vehicleContrailView.showProgress();
            VehicleCoreApi
                    .getInstance()
                    .getLocationList(accessToken, serialNumber, equModel, beginTime, endTime, this);
        }
    }

    @Override
    public void onGetVehicleContrailSuccess(List<VehicleLocationInfo> vehicleLocationInfoList) {
        if (vehicleContrailView != null) {
            vehicleContrailView.hideProgress();
            vehicleContrailView.showVehicleContrail(vehicleLocationInfoList);
        }
    }

    @Override
    public void onGetVehicleContrailFailure() {
        if (vehicleContrailView != null) {
            vehicleContrailView.hideProgress();
            vehicleContrailView.onFailure();
        }
    }

    @Override
    public void onGetVehicleContrailResponseError(String error_msg) {
        if (vehicleContrailView != null) {
            vehicleContrailView.hideProgress();
            vehicleContrailView.onResponseError(error_msg);
        }
    }

    @Override
    public void onGrantRefuse() {
        if (vehicleContrailView != null) {
            vehicleContrailView.hideProgress();
            vehicleContrailView.onGrantRefuse();
        }
    }
}
