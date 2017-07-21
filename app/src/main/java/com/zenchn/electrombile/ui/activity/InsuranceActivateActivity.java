package com.zenchn.electrombile.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.InsuranceActivateInfo;
import com.zenchn.electrombile.entity.model.InsuranceStatusEnum;
import com.zenchn.electrombile.presenter.InsuranceActivatePresenter;
import com.zenchn.electrombile.presenter.impl.InsuranceActivatePresenterImpl;
import com.zenchn.electrombile.ui.view.InsuranceActivateView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/3/8 13:30
 * 描    述：保险激活的界面
 * 修订记录：
 */
public class InsuranceActivateActivity extends BaseActivity implements InsuranceActivateView, CommonDialogFactory.OnCommitListener<InsuranceActivateInfo>, TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_idCard)
    EditText etIdCard;
    @BindView(R.id.et_mobile_phone)
    EditText etMobilePhone;
    @BindView(R.id.et_frame_number)
    EditText etFrameNumber;
    @BindView(R.id.et_machine_number)
    EditText etMachineNumber;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.iv_choose_contact)
    ImageView ivChooseContact;

    private int protectionStatus;
    private int operateType;

    private InsuranceActivatePresenter presenter;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        removeListener();
        super.onDestroy();
    }

    public InsuranceActivateActivity() {
        this.presenter = new InsuranceActivatePresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    protected void addListener() {
        etMobilePhone.addTextChangedListener(this);
    }

    protected void removeListener() {
        etMobilePhone.removeTextChangedListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.title_insurance_activate);
        etMobilePhone.setText(loginInfo.getLoginName());
        Intent intent = getIntent();
        operateType = intent.getIntExtra("operateType", Constants.INSURANCE.INSURANCE_ACTIVATE_ADD);
        if (Constants.INSURANCE.INSURANCE_ACTIVATE_EDIT == operateType) {// 编辑
            protectionStatus = getIntent().getIntExtra("protectionStatus", InsuranceStatusEnum.审核未通过.ordinal());
        } else {
            protectionStatus = getIntent().getIntExtra("protectionStatus", InsuranceStatusEnum.未启用.ordinal());
        }
        renewTemp();//恢复上次保存的内容
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_insurance_activate;
    }

    /**
     * 恢复用户填写的内容
     */
    private void renewTemp() {

        InsuranceActivateInfo insuranceActivateInfo = ACacheWrapper.getInsuranceActivateInfo();

        if (insuranceActivateInfo != null) {

            String name = insuranceActivateInfo.getName();
            if (CommonUtils.isNonNull(name))
                etName.setText(name);

            String machineNumber = insuranceActivateInfo.getMachineNumber();
            if (CommonUtils.isNonNull(machineNumber))
                etMachineNumber.setText(machineNumber);

            String mobilePhone = insuranceActivateInfo.getMobilePhone();
            if (CommonUtils.isNonNull(mobilePhone))
                etMobilePhone.setText(mobilePhone);

            String frameNumber = insuranceActivateInfo.getFrameNumber();
            if (CommonUtils.isNonNull(frameNumber))
                etFrameNumber.setText(frameNumber);

            String idCard = insuranceActivateInfo.getIdCard();
            if (CommonUtils.isNonNull(idCard))
                etIdCard.setText(idCard);

        }
    }

    @OnClick({R.id.ll_back, R.id.ll_commit, R.id.iv_choose_contact, R.id.iv_clear})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_choose_contact:
                startActivityForResult(new Intent(this, ChooseContactsActivity.class), BuildConf.RequestResultCode.CHOOSE_CONTACTS_REQUEST);
                break;

            case R.id.ll_back:
                onBackPressed();
                break;

            case R.id.ll_commit:
                InsuranceActivateInfo insuranceActivateInfo = getInsuranceActivateInfo();
                if (checkInsuranceActivateInfo(insuranceActivateInfo)) {
                    CommonDialogFactory.createActivateConfirmDialog(this, insuranceActivateInfo, this).show();
                }
                break;

            case R.id.iv_clear:
                etMobilePhone.setText("");
                break;

        }
    }

    /**
     * 获取申请保险的信息
     */
    private InsuranceActivateInfo getInsuranceActivateInfo() {
        return new InsuranceActivateInfo(
                etName.getText().toString().trim(),
                etIdCard.getText().toString().trim(),
                etMobilePhone.getText().toString().trim(),
                etFrameNumber.getText().toString().trim(),
                etMachineNumber.getText().toString().trim());
    }

    /**
     * 检测申请保险的信息
     */
    private boolean checkInsuranceActivateInfo(InsuranceActivateInfo insuranceActivateInfo) {

        String name = insuranceActivateInfo.getName();
        if (CommonUtils.isEmpty(name)) {
            showResMessage(R.string.activate_insurance_no_name_desc);
            return false;
        }

        String idCard = insuranceActivateInfo.getIdCard();
        if (CommonUtils.isEmpty(idCard)) {
            showResMessage(R.string.activate_insurance_no_id_card_desc);
            return false;
        } else if (idCard.length() != 18 && idCard.length() != 15) {
            showResMessage(R.string.activate_insurance_error_id_card_desc);
            return false;
        }

        String mobilePhone = insuranceActivateInfo.getMobilePhone();
        if (CommonUtils.isEmpty(mobilePhone)) {
            showResMessage(R.string.activate_insurance_no_mobile_phone_desc);
            return false;
        }

        String frameNumber = insuranceActivateInfo.getFrameNumber();
        if (CommonUtils.isEmpty(frameNumber)) {
            showResMessage(R.string.activate_insurance_no_frame_number_desc);
            return false;
        }

        String machineNumber = insuranceActivateInfo.getMachineNumber();
        if (CommonUtils.isEmpty(machineNumber)) {
            showResMessage(R.string.activate_insurance_no_machine_number_desc);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        saveTemp();
        setResult(protectionStatus);
        finish();
    }

    /**
     * 保存内容
     */
    private void saveTemp() {
        InsuranceActivateInfo insuranceActivateInfo = getInsuranceActivateInfo();
        ACacheWrapper.saveInsuranceActivateInfo(insuranceActivateInfo);
    }

    @Override
    public void onCommit(InsuranceActivateInfo insuranceActivateInfo) {
        if (insuranceActivateInfo != null)
            presenter.submitApplyProtectionInfo(loginInfo.getAccessToken(), loginInfo.getSerialNumber(), insuranceActivateInfo);
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onSubmitCompleted(Boolean result) {
        if (result) {
            showResMessage(R.string.activate_insurance_success);
            Intent intent = new Intent(this, InsuranceApplyStatusActivity.class);
            intent.putExtra("protectionStatus", protectionStatus = InsuranceStatusEnum.审核中.ordinal());
            intent.putExtra("operateType", operateType);
            startActivityForResult(intent, BuildConf.RequestResultCode.ADD_INSURANCE_REQUEST);
        } else {
            showResMessage(R.string.activate_insurance_failure);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BuildConf.RequestResultCode.ADD_INSURANCE_REQUEST == requestCode) {
            setResult(resultCode);
            finish();
        } else if (BuildConf.RequestResultCode.CHOOSE_CONTACTS_REQUEST == requestCode && RESULT_OK == resultCode) {
            String mobilePhone = data.getStringExtra("mobilePhone");
            if (CommonUtils.isNonNull(mobilePhone)) {
                etMobilePhone.setText(mobilePhone);
                etMobilePhone.setSelection(mobilePhone.length());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable edt) {
        String number = edt.toString();
        if (CommonUtils.isNonNull(number)) {
            ivChooseContact.setVisibility(View.GONE);
            ivClear.setVisibility(View.VISIBLE);
        } else {
            ivClear.setVisibility(View.GONE);
            ivChooseContact.setVisibility(View.VISIBLE);
        }
    }

}
