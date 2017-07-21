package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zbar.lib.CaptureActivity;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.presenter.BinderPresenter;
import com.zenchn.electrombile.presenter.impl.BinderPresenterImpl;
import com.zenchn.electrombile.ui.view.BinderView;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.mlibrary.utils.MobileUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/21 18:52
 * 描    述： 处理车辆绑定的类
 * 修订记录：
 */
public class BinderActivity extends BaseActivity implements BinderView, CommonDialogFactory.OnCommitListener<String> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.iv_result)
    ImageView ivResult;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_submit)
    LinearLayout llSubmit;
    @BindView(R.id.ll_result)
    LinearLayout llResult;
    @BindView(R.id.tv_retry)
    TextView tvRetry;

    private BinderPresenter presenter;
    private boolean isBindSuccess;

    private String encryptSerialNumber;
    private String mobilePhone;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public BinderActivity() {
        this.presenter = new BinderPresenterImpl(this);
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_bind_vehicle));
        String serialNumber = getIntent().getStringExtra("result");
        performBindEquipment(serialNumber);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_vehicle;
    }

    /**
     * 绑定设备
     *
     * @param serialNumber
     */
    private void performBindEquipment(String serialNumber) {
        if (CommonUtils.isEmpty(serialNumber)) {
            onBindResult(false, null);
        } else {
            presenter.getBindStatus(loginInfo.getAccessToken(), serialNumber);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.tv_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_submit:
                if (isBindSuccess) {
                    SwitchActivity.launch(this);
                    finish();
                } else {
                    CaptureActivity.launch(this);
                    finish();
                }
                break;
            case R.id.tv_retry:
                llResult.setVisibility(View.INVISIBLE);
                CommonDialogFactory
                        .createAskBindDialog(this, MobileUtils.getEncryptMobileNO(mobilePhone), this).show();
                break;
        }
    }

    @Override
    public void askValidate(String encryptSerialNumber, String mobilePhone) {
        this.encryptSerialNumber = encryptSerialNumber;
        this.mobilePhone = mobilePhone;
        CommonDialogFactory
                .createAskBindDialog(this, MobileUtils.getEncryptMobileNO(mobilePhone), this).show();
    }

    @Override
    public void bindEquipment(String encryptSerialNumber) {
        presenter.bindEquipment(loginInfo.getAccessToken(), encryptSerialNumber);
    }

    @Override
    public void onBindResult(boolean isBindSuccess, String msg) {
        llResult.setVisibility(View.VISIBLE);
        this.isBindSuccess = isBindSuccess;
        if (isBindSuccess) {
            ivResult.setImageResource(R.drawable.check);
            tvResult.setText(getString(R.string.bind_equipment_success));
            tvContent.setVisibility(View.INVISIBLE);
            tvSubmit.setText(getString(R.string.bind_success_button));
            llBack.setVisibility(View.GONE);
            tvRetry.setVisibility(View.GONE);
        } else {
            ivResult.setImageResource(R.drawable.error);
            tvResult.setText(getString(R.string.bind_equipment_error));
            tvSubmit.setText(getString(R.string.bind_error_button));
            tvContent.setText(CommonUtils.isEmpty(msg) ? getString(R.string.bind_equipment_by_other_error) : msg);
        }
        ivResult.setVisibility(View.VISIBLE);
        tvResult.setVisibility(View.VISIBLE);
        llSubmit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCommit(String mobilePhone) {
        if (CommonUtils.isNonNull(mobilePhone)) {
            if (CommonUtils.isEmpty(mobilePhone))
                onCheckFailure(getString(R.string.bind_equipment_by_mobile_phone_empty));
            else if (!MobileUtils.isMobileNO(mobilePhone))
                onCheckFailure(getString(R.string.bind_equipment_by_mobile_phone_error));
            else if (this.mobilePhone.equals(mobilePhone)) {
                Intent intent = new Intent(this, ValidateActivity.class);
                intent.putExtra("type", BuildConf.AuthCodeType.BIND_EQUIPMENT);
                intent.putExtra("mobilePhone", mobilePhone);
                intent.putExtra("encryptSerialNumber", encryptSerialNumber);
                startActivityForResult(intent, BuildConf.RequestResultCode.BIND_EQUIPMENT_REQUEST);
                return;
            } else {
                onCheckFailure(getString(R.string.bind_equipment_by_mobile_phone_differ));
            }
        } else {
            onCheckFailure(getString(R.string.bind_equipment_by_other_error));
        }
    }

    /**
     * 验证失败的处理
     *
     * @param errorReason
     */
    private void onCheckFailure(String errorReason) {
        tvRetry.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvRetry.getPaint().setAntiAlias(true);//抗锯齿
        tvRetry.setVisibility(View.VISIBLE);
        onBindResult(false, errorReason);
    }

    @Override
    public void onCancel() {
        onCheckFailure(getString(R.string.bind_equipment_by_user_cancel));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BuildConf.RequestResultCode.BIND_EQUIPMENT_REQUEST == requestCode) {
            if (RESULT_CANCELED == resultCode) {
                onCancel();
            } else {
                boolean bindResult = data.getBooleanExtra("bindResult", false);
                String bindMsg = data.getStringExtra("bindMsg");
                onBindResult(bindResult, bindMsg);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
