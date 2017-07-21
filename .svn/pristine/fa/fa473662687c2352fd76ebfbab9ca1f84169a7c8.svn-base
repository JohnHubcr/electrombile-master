package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.VehicleInfoApi;
import com.zenchn.electrombile.api.callback.VehicleCheckCallback;
import com.zenchn.electrombile.engine.HardwareParserEngine;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForJKInfo;
import com.zenchn.electrombile.engine.bean.VehicleCheckResultForSTInfo;
import com.zenchn.electrombile.engine.callback.VehicleParserCallback;
import com.zenchn.electrombile.entity.VehicleCheckRawForJKInfo;
import com.zenchn.electrombile.entity.VehicleCheckRawForSTInfo;
import com.zenchn.electrombile.presenter.VehicleCheckPresenter;
import com.zenchn.electrombile.ui.view.VehicleCheckView;

/**
 * 作    者：wangr on 2017/3/13 13:53
 * 描    述：
 * 修订记录：
 */
public class VehicleCheckPresenterImpl implements VehicleCheckPresenter, VehicleCheckCallback, VehicleParserCallback {

    private VehicleCheckView vehicleCheckView;

    public VehicleCheckPresenterImpl(VehicleCheckView vehicleCheckView) {
        this.vehicleCheckView = vehicleCheckView;
    }

    @Override
    public void onDestroy() {
        vehicleCheckView = null;
    }

    @Override
    public void getVehicleStatus(String accessToken, String serialNumber, int equModel) {
        if (vehicleCheckView != null) {
            vehicleCheckView.showProgress();
            VehicleInfoApi
                    .getInstance()
                    .getVehicleStatus(accessToken, serialNumber, equModel, this);
        }
    }

    @Override
    public void onGetVehicleCheckSuccess(VehicleCheckRawForSTInfo vehicleCheckRawForSTInfo) {
        if (vehicleCheckView != null) {
            HardwareParserEngine
                    .getInstance()
                    .analyticalData(vehicleCheckRawForSTInfo, this);
        }
    }

    @Override
    public void onParserSuccess(VehicleCheckResultForSTInfo vehicleCheckResultForSTInfo) {
        if (vehicleCheckView != null) {
            vehicleCheckView.hideProgress();
            vehicleCheckView.onParserVehicleCheckSuccess(vehicleCheckResultForSTInfo);
        }
    }

    @Override
    public void onGetVehicleCheckSuccess(VehicleCheckRawForJKInfo vehicleCheckRawForJKInfo) {
        if (vehicleCheckView != null) {
            HardwareParserEngine
                    .getInstance()
                    .analyticalData(vehicleCheckRawForJKInfo, this);
        }
    }

    @Override
    public void onParserSuccess(VehicleCheckResultForJKInfo vehicleCheckResultForJKInfo) {
        if (vehicleCheckView != null) {
            vehicleCheckView.hideProgress();
            vehicleCheckView.onParserVehicleCheckSuccess(vehicleCheckResultForJKInfo);
        }
    }

    @Override
    public void onGetVehicleCheckFailure() {
        if (vehicleCheckView != null) {
            vehicleCheckView.setCheckCompleted();
            vehicleCheckView.hideProgress();
            vehicleCheckView.onFailure();
        }
    }

    @Override
    public void onGetVehicleCheckResponseError(String error_msg) {
        if (vehicleCheckView != null) {
            vehicleCheckView.hideProgress();
            vehicleCheckView.setCheckCompleted();
            vehicleCheckView.onResponseError(error_msg);
        }
    }

    @Override
    public void onGrantRefuse() {
        if (vehicleCheckView != null) {
            vehicleCheckView.setCheckCompleted();
            vehicleCheckView.hideProgress();
            vehicleCheckView.onGrantRefuse();
        }
    }

}
