package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.VehicleRecordInfo;
import com.zenchn.electrombile.entity.model.InsuranceStatusEnum;
import com.zenchn.electrombile.presenter.VehicleArchivesPresenter;
import com.zenchn.electrombile.presenter.impl.VehicleArchivesPresenterImpl;
import com.zenchn.electrombile.ui.view.VehicleArchivesView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/24 15:38
 * 描    述：
 * 修订记录：
 */

public class VehicleArchivesActivity extends BaseActivity implements VehicleArchivesView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_vehicle_brand)
    TextView tvVehicleBrand;
    @BindView(R.id.tv_vehicle_model)
    TextView tvVehicleModel;
    @BindView(R.id.tv_machine_number)
    TextView tvMachineNumber;
    @BindView(R.id.tv_motor_no)
    TextView tvMotorNo;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    private VehicleArchivesPresenter presenter;

    private boolean whetherCommonVehicle;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public VehicleArchivesActivity() {
        presenter = new VehicleArchivesPresenterImpl(this);
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_vehicle_archives));

        whetherCommonVehicle = getIntent().getBooleanExtra("whetherCommonVehicle", false);
        String serialNumber = getIntent().getStringExtra("serialNumber");
        int protectionStatus = loginInfo.getProtectionStatus();
        InsuranceStatusEnum insuranceStatusEnum = InsuranceStatusEnum.classifyInsuranceStatus(protectionStatus);
        tvStatus.setText(insuranceStatusEnum.name());

        presenter.getVehicleRecordInfo(loginInfo.getAccessToken(), serialNumber);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_vehicle_archives;
    }

    @OnClick({R.id.ll_back, R.id.ll_exclusive_service})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_back:
                onBackPressed();
                break;

            case R.id.ll_exclusive_service:

                break;
        }
    }

    @Override
    public void showVehicleRecordInfo(VehicleRecordInfo vehicleRecordInfo) {

        //缓存车辆（常用车辆）档案
        if (whetherCommonVehicle)
            ACacheWrapper.tempVehicleRecordInfo(vehicleRecordInfo);

        if (vehicleRecordInfo != null) {

            String machineNumber = vehicleRecordInfo.getMachineNumber();
            if (CommonUtils.isNonNull(machineNumber)) {
                tvMachineNumber.setText(machineNumber);
            }

            String motorNo = vehicleRecordInfo.getMotorNo();
            if (CommonUtils.isNonNull(motorNo)) {
                tvMotorNo.setText(motorNo);
            }

            String payTime = vehicleRecordInfo.getPayTime();
            if (CommonUtils.isNonNull(payTime)) {
                tvPayTime.setText(payTime);
            }

            String vehicleModel = vehicleRecordInfo.getVehicleModel();
            if (CommonUtils.isNonNull(vehicleModel)) {
                tvVehicleModel.setText(vehicleModel);
            }

            String vehicleBrand = vehicleRecordInfo.getVehicleBrand();
            if (CommonUtils.isNonNull(vehicleBrand)) {
                tvVehicleBrand.setText(vehicleBrand);
            }
        }
    }
}
