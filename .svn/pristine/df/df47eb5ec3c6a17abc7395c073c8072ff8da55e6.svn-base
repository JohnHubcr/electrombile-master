package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.eventBus.IdentifyingCodeEvent;
import com.zenchn.electrombile.presenter.ValidatePresenter;
import com.zenchn.electrombile.presenter.impl.ValidatePresenterImpl;
import com.zenchn.electrombile.receiver.SmsReceiver;
import com.zenchn.electrombile.ui.view.ValidateView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.GridpasswordView.GridPasswordView;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 16:31
 * 描    述：
 * 修订记录：
 */
public class ValidateActivity extends BaseActivity implements ValidateView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.gpv_textVisiblePassword)
    GridPasswordView gpvTextVisiblePassword;
    @BindView(R.id.ll_rest_send)
    LinearLayout llRestSend;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_count_down)
    LinearLayout llCountDown;

    private ValidatePresenter presenter;

    private String type;
    private int count;

    private String mobilePhone;
    private String authCode;

    public ValidateActivity() {
        presenter = new ValidatePresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initData();
        initReceiver();
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_validate_input));
        gpvTextVisiblePassword.setPasswordVisibility(true);// 设置显示输入
        type = getIntent().getStringExtra("type");
        mobilePhone = getIntent().getStringExtra("mobilePhone");

        tvMobile.setText(mobilePhone);

        count = ACacheWrapper.getAuthCodeTime(mobilePhone);
        if (count != 60) {
            startTimer();
        } else {
            presenter.getAuthCode(mobilePhone, type);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_validate;
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_SMS_RECEIVED);
        registerReceiver(new SmsReceiver(), filter);// 注册广播
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEventMainThread(IdentifyingCodeEvent event) {
        String identifyingCode = event.getIdentifyingCode();
        if (CommonUtils.isNonNull(identifyingCode))
            gpvTextVisiblePassword.setPassword(identifyingCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onGetAuthCodeError() {
        resetTimer();//重置定时器
    }

    @Override
    public void onShowGetAuthCodeError() {
        showResMessage(R.string.identifying_send_error);
    }

    @Override
    public void onGetAuthCodeSuccess() {
        startTimer();//开始定时器
    }

    /**
     * 开始定时器
     */
    private void startTimer() {
        llCountDown.setVisibility(View.VISIBLE);
        llRestSend.setVisibility(View.GONE);
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (count > 0) {
                    tvTime.setText(String.valueOf(count));
                    count--;
                    mHandler.postDelayed(this, 1000);
                } else {
                    mHandler.removeCallbacks(this);
                    resetTimer();
                }
            }
        });
    }

    /**
     * 重置定时器
     */
    private void resetTimer() {
        llCountDown.setVisibility(View.GONE);
        llRestSend.setVisibility(View.VISIBLE);
        count = 60;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BuildConf.RequestResultCode.RESET_PASSWORD_REQUEST) {
            setResult(resultCode);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (count > 0)
            ACacheWrapper.tempAuthCodeTime(mobilePhone, count);
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.ll_rest_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:// 返回
                onBackPressed();
                break;

            case R.id.ll_rest_send:// 重新发送验证码
                presenter.getAuthCode(mobilePhone, type);
                break;

            case R.id.ll_submit:// 完成
                authCode = gpvTextVisiblePassword.getPassWord();
                if (TextUtils.isEmpty(authCode) || authCode.length() != 4) {
                    showResMessage(R.string.identifying_code_error);
                    return;
                }
                switchValidateMode(type);
                break;
        }
    }

    private void switchValidateMode(String type) {

        switch (type) {

            case BuildConf.AuthCodeType.REGISTER:
                navigateToRegister(mobilePhone, authCode);
                break;

            case BuildConf.AuthCodeType.ACCOUNT_REPLACE:
                navigateToReplaceAccount(mobilePhone, authCode);
                break;

            case BuildConf.AuthCodeType.MODIFY_PWD:
                navigateToResetPassword(mobilePhone, authCode);
                break;

            case BuildConf.AuthCodeType.BIND_EQUIPMENT:
                navigateToBindEquipment(mobilePhone, authCode);
                break;

        }

    }

    /**
     * 重置密码的业务
     *
     * @param mobilePhone
     * @param authCode
     */
    private void navigateToResetPassword(String mobilePhone, String authCode) {
        Intent intent = new Intent(this, ResetPwdActivity.class);
        intent.putExtra("authCode", authCode);
        intent.putExtra("mobilePhone", mobilePhone);
        startActivityForResult(intent, BuildConf.RequestResultCode.RESET_PASSWORD_REQUEST);
    }

    /**
     * 注册的业务
     *
     * @param mobilePhone
     * @param authCode
     */
    private void navigateToRegister(String mobilePhone, String authCode) {
        String encryptPassword = getIntent().getStringExtra("encryptPassword");
        presenter.register(mobilePhone, encryptPassword, authCode);
    }

    @Override
    public void navigateToHome(UserInfo userInfo) {
        Intent data = new Intent();
        data.putExtra("data", userInfo);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void navigateToRegister() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * 账号变更的业务
     *
     * @param mobilePhone
     * @param authCode
     */
    private void navigateToReplaceAccount(String mobilePhone, String authCode) {
        String encryptPassword = getIntent().getStringExtra("encryptPassword");
        presenter.modifyAccount(loginInfo.getAccessToken(), mobilePhone, encryptPassword, authCode);
    }

    @Override
    public void navigateToLogin(String mobilePhone) {
        Intent data = new Intent();
        data.putExtra("data", mobilePhone);
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * 绑定设备的验证
     *
     * @param mobilePhone
     * @param authCode
     */
    private void navigateToBindEquipment(String mobilePhone, String authCode) {
        String encryptSerialNumber = getIntent().getStringExtra("encryptSerialNumber");
        presenter.bindEquipment(loginInfo.getAccessToken(), encryptSerialNumber, mobilePhone, authCode);
    }

    @Override
    public void backToBind(boolean bindResult, String bindMsg) {
        Intent data = new Intent();
        data.putExtra("bindResult", bindResult);
        data.putExtra("bindMsg", bindMsg);
        setResult(RESULT_OK, data);
        finish();
    }
}
