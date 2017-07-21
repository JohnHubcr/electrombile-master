package com.zenchn.electrombile.alipay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.model.InsuranceStatusEnum;
import com.zenchn.electrombile.ui.activity.InsuranceActivateActivity;
import com.zenchn.electrombile.utils.CommonUtils;

public class AliPayActivity extends BaseActivity implements AliPayView {

    private AliPayPresenter presenter;

    private int protectionStatus;
    private String outTradeNo;

    @Override
    public boolean useUiHandler() {
        return true;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public AliPayActivity() {
        this.presenter = new AliPayPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        Intent data = getIntent();
        protectionStatus = data.getIntExtra("protectionStatus", InsuranceStatusEnum.未购买.ordinal());
        String sign = data.getStringExtra("sign");

        String outTradeNo = data.getStringExtra("outTradeNo");
        if (CommonUtils.isNonNull(sign) && CommonUtils.isNonNull(outTradeNo)) {
            this.outTradeNo = outTradeNo;
            payV2(sign);
        }
    }

    /**
     * 支付宝支付业务
     *
     * @param orderInfo
     */
    private void payV2(String orderInfo) {
        if (TextUtils.isEmpty(BuildConf.AliPay.PARTNER) || TextUtils.isEmpty(orderInfo)) {
            showAlertDialog();
            return;
        }
        presenter.payV2(new PayTask(this), orderInfo, mHandler);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | 订单信息").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialoginterface, int i) {
                finish();
            }
        }).show();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BuildConf.RequestResultCode.ADD_INSURANCE_REQUEST == requestCode) {
            setResult(resultCode);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        setResult(protectionStatus);
        finish();
    }

    @Override
    public void getPayResult() {
        presenter.getPayResult(loginInfo.getAccessToken(), outTradeNo);
    }

    @Override
    public void onPaySuccess() {
        showResMessage(R.string.pay_success);
        Intent intent = new Intent(AliPayActivity.this, InsuranceActivateActivity.class);
        intent.putExtra("protectionStatus", InsuranceStatusEnum.未启用.ordinal());
        intent.putExtra("operateType", Constants.INSURANCE_ADD);
        startActivityForResult(intent, BuildConf.RequestResultCode.ADD_INSURANCE_REQUEST);
    }

    @Override
    public void onPayFailure() {
        showResMessage(R.string.pay_failure);
        setResult(protectionStatus);
        finish();
    }
}
