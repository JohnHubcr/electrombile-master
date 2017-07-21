package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.InsurancePolicyInfo;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.presenter.InsuranceProtectPresenter;
import com.zenchn.electrombile.presenter.impl.InsuranceProtectPresenterImpl;
import com.zenchn.electrombile.ui.view.InsuranceProtectView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/3/9 10:24
 * 描    述：
 * 修订记录：
 */
public class InsuranceProtectActivity extends BaseActivity implements InsuranceProtectView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_insurance_remark)
    TextView tvInsuranceRemark;
    @BindView(R.id.tv_insurance_time_limit)
    TextView tvInsuranceTimeLimit;
    @BindView(R.id.tv_insurance_content)
    TextView tvInsuranceContent;
    @BindView(R.id.tv_insurance_provider)
    TextView tvInsuranceProvider;
    @BindView(R.id.tv_improve_progress)
    TextView tvImproveProgress;
    @BindView(R.id.iv_improve_point)
    ImageView ivImprovePoint;
    @BindView(R.id.tv_service_call)
    TextView tvServiceCall;

    private InsuranceProtectPresenter presenter;
    private Intent intent;

    public InsuranceProtectActivity() {
        presenter = new InsuranceProtectPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_insurance_protect));
        presenter.getInsurancePolicyInfo(loginInfo.getAccessToken(), loginInfo.getSerialNumber());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_insurance_protect;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePerfectStatus(loginInfo.getPerfectStatus());
    }

    private void updatePerfectStatus(int perfectStatus) {
        if (Constants.INSURANCE.INSURANCE_CLAIM_COMPLETED == perfectStatus) {
            tvImproveProgress.setText(getString(R.string.insurance_improve));
            ivImprovePoint.setImageResource(R.drawable.small_green_dot);
        } else {
            tvImproveProgress.setText(getString(R.string.insurance_not_improve));
            ivImprovePoint.setImageResource(R.drawable.small_red_dot);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_insurance_cover, R.id.ll_insurance_improve, R.id.ll_claim_flowsheet, R.id.tv_service_call, R.id.ll_claim})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_insurance_cover:
                intent = new Intent();
                intent.putExtra("type", BuildConf.WebViewType.INSURANCE_CONTENT);
                intent.setClass(this, WebViewCommonActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_insurance_improve:
                intent = new Intent(this, InsuranceClaimActivity.class);
                intent.putExtra("operateType", Constants.INSURANCE.INSURANCE_CLAIM_SUPERADDITION);
                startActivityForResult(intent, BuildConf.RequestResultCode.ADD_INSURANCE_CLAIM_REQUEST);
                break;
            case R.id.ll_claim_flowsheet:
                intent = new Intent();
                intent.putExtra("type", BuildConf.WebViewType.INSURANCE_CLAIM_PROCESS);
                intent.setClass(this, WebViewCommonActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_service_call:
                CommonDialogFactory.createCallDialog(this, getString(R.string.ask_call_service), tvServiceCall.getText().toString()).show();
                break;
            case R.id.ll_claim:
                CommonDialogFactory.createCallDialog(this, getString(R.string.ask_call_service), tvServiceCall.getText().toString()).show();
                break;
        }
    }

    @Override
    public void showInsurancePolicyInfo(InsurancePolicyInfo insurancePolicyInfo) {
        if (insurancePolicyInfo != null) {

            String content = insurancePolicyInfo.getContent();
            if (CommonUtils.isNonNull(content))
                tvInsuranceContent.setText(content);

            String category = insurancePolicyInfo.getCategory();
            if (CommonUtils.isNonNull(category))
                tvInsuranceProvider.setText(String.format(getString(R.string.protect_insurance_provider_format), category));

            String phone = insurancePolicyInfo.getPhone();
            if (!CommonUtils.isEmpty(phone))
                tvServiceCall.setText(phone);

            String remark = insurancePolicyInfo.getRemark();
            if (!CommonUtils.isEmpty(remark)) {
                tvInsuranceRemark.setText(remark);
            }

            String protectionStartTime = insurancePolicyInfo.getProtectionStartTime();
            String protectionEndTime = insurancePolicyInfo.getProtectionEndTime();
            if (CommonUtils.isNonNull(protectionStartTime) && CommonUtils.isNonNull(protectionEndTime))
                tvInsuranceTimeLimit.setText(String.format(getString(R.string.protect_insurance_time_limit_format), protectionStartTime, protectionEndTime));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BuildConf.RequestResultCode.ADD_INSURANCE_CLAIM_REQUEST == requestCode && RESULT_OK == resultCode) {
            loginInfo = ApplicationKit.getInstance().getLoginInfo();
            updatePerfectStatus(loginInfo.getPerfectStatus());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
