package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.VehicleInfoApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.entity.VehicleRecordInfo;
import com.zenchn.electrombile.presenter.VehicleArchivesPresenter;
import com.zenchn.electrombile.ui.view.VehicleArchivesView;

/**
 * 作    者：wangr on 2017/2/28 17:32
 * 描    述：
 * 修订记录：
 */
public class VehicleArchivesPresenterImpl implements VehicleArchivesPresenter, ApiCallback<VehicleRecordInfo> {

    private VehicleArchivesView vehicleArchivesView;

    public VehicleArchivesPresenterImpl(VehicleArchivesView vehicleArchivesView) {
        this.vehicleArchivesView = vehicleArchivesView;
    }

    @Override
    public void getVehicleRecordInfo(String accessToken, String serialNumber) {
        if (vehicleArchivesView != null) {
            vehicleArchivesView.showProgress();
            VehicleInfoApi
                    .getInstance()
                    .getVehicleRecord(accessToken, serialNumber, this);
        }
    }

    @Override
    public void onDestroy() {
        vehicleArchivesView = null;
    }

    @Override
    public void onSuccess(VehicleRecordInfo vehicleRecordInfo) {
        if (vehicleArchivesView != null) {
            vehicleArchivesView.hideProgress();
            vehicleArchivesView.showVehicleRecordInfo(vehicleRecordInfo);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (vehicleArchivesView != null) {
            vehicleArchivesView.hideProgress();
            vehicleArchivesView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (vehicleArchivesView != null) {
            vehicleArchivesView.hideProgress();
            vehicleArchivesView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (vehicleArchivesView != null) {
            vehicleArchivesView.hideProgress();
            vehicleArchivesView.onGrantRefuse();
        }
    }

}
