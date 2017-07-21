package com.zenchn.electrombile.ui.fragment;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseHomePageFragment;
import com.zenchn.electrombile.entity.VehicleRecordInfo;
import com.zenchn.electrombile.entity.model.InsuranceStatusEnum;
import com.zenchn.electrombile.ui.activity.VehicleListActivity;
import com.zenchn.electrombile.ui.activity.SettingActivity;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import butterknife.BindView;
import butterknife.OnClick;


public class PersonalFragment extends BaseHomePageFragment {

    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_motor_info)
    TextView tvMotorInfo;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.tv_motor_model)
    TextView tvMotorModel;
    @BindView(R.id.tv_insurance_status)
    TextView tvInsuranceStatus;
    @BindView(R.id.iv_setting_point)
    ImageView ivSettingPoint;

    private InsuranceStatusEnum insuranceStatusEnum;
    private Intent intent;

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.fragment_title_setting);
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_personal;
    }


    @Override
    protected void initData() {
        initUserInfo();
        initSettingPoint();
        initInsuranceStatus();
        initMotorRecordInfo();
    }

    private void initSettingPoint() {
        ivSettingPoint.setVisibility(CommonUtils.isEmpty(loginInfo.getOtherPhone()) ? View.VISIBLE : View.GONE);
    }

    private void initMotorRecordInfo() {
        VehicleRecordInfo vehicleRecordInfo = ACacheWrapper.getVehicleRecordInfo();

        if (vehicleRecordInfo == null || CommonUtils.isEmpty(vehicleRecordInfo.getVehicleModel()) || CommonUtils.isEmpty(vehicleRecordInfo.getVehicleBrand())) {
            tvMotorInfo.setVisibility(View.VISIBLE);
            tvBrand.setVisibility(View.GONE);
            tvMotorModel.setVisibility(View.GONE);
        } else {
            tvMotorInfo.setVisibility(View.GONE);
            tvBrand.setVisibility(View.VISIBLE);
            tvMotorModel.setVisibility(View.VISIBLE);
            tvBrand.setText(vehicleRecordInfo.getVehicleBrand());
            tvMotorModel.setText(vehicleRecordInfo.getVehicleModel());
        }
    }

    private void initUserInfo() {
        tvPhone.setText(loginInfo.getLoginName());
    }

    private void initInsuranceStatus() {
        int protectionStatus = loginInfo.getProtectionStatus();
        insuranceStatusEnum = InsuranceStatusEnum.classifyInsuranceStatus(protectionStatus);
        tvInsuranceStatus.setText(insuranceStatusEnum.name());
    }

    @Override
    protected void handler(Message msg) {
    }

    @OnClick({R.id.ll_personal_info, R.id.ll_motor_archives, R.id.ll_exclusive_service, R.id.ll_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personal_info:
//                startActivity(new Intent(getActivity(), PersonalActivity.class));
                break;
            case R.id.ll_motor_archives:
                if (isHideFunction == null) {
                    CommonDialogFactory.createBindDialog(getActivity()).show();
                } else {
//                    startActivityForResult(new Intent(getActivity(), MotorRecordInfo.class), BuildConf.RequestResultCode.MOTOR_ARCHIVES_REQUEST);
                    startActivity(new Intent(getActivity(), VehicleListActivity.class));
                }
                break;
            case R.id.ll_exclusive_service:
                if (isHideFunction == null) {
                    CommonDialogFactory.createBindDialog(getActivity()).show();
                } else {
                    intent = new Intent(getActivity(), insuranceStatusEnum.getActivity());
                    if (insuranceStatusEnum.ordinal() == InsuranceStatusEnum.保障中.ordinal()) {
                        startActivity(intent);
                    } else {
                        intent.putExtra("protectionStatus", insuranceStatusEnum.ordinal());
                        startActivityForResult(intent, BuildConf.RequestResultCode.EXCLUSIVE_SERVICE_REQUEST);
                    }
                }
                break;
            case R.id.ll_setting:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivityForResult(intent, BuildConf.RequestResultCode.SETTING_REQUEST);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BuildConf.RequestResultCode.EXCLUSIVE_SERVICE_REQUEST == requestCode) {//把保险状态码当中返回码使用
            if (insuranceStatusEnum.ordinal() != resultCode) {
                insuranceStatusEnum = InsuranceStatusEnum.classifyInsuranceStatus(resultCode);
                tvInsuranceStatus.setText(insuranceStatusEnum.name());
                if (homePageView != null) {
                    homePageView.updateProtectionStatus(resultCode);
                }
            }
        } else if (BuildConf.RequestResultCode.SETTING_REQUEST == requestCode) {
            String urgentContact = data.getStringExtra("urgentContact");
            if (null != urgentContact && loginInfo.getOtherPhone() != (urgentContact)) {
                loginInfo.setOtherPhone(urgentContact);
                initSettingPoint();
                if (homePageView != null) {
                    homePageView.updateUrgentContact(urgentContact);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

