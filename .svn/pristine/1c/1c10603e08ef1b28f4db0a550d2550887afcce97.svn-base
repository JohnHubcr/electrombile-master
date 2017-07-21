package com.zenchn.electrombile.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.widget.AnimationFactory;
import com.zenchn.mlibrary.utils.ApkUtils;
import com.zenchn.mlibrary.utils.EncryptUtils;
import com.zenchn.mlibrary.utils.MobileUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 17:06
 * 描    述：注册的界面
 * 修订记录：
 */
public class RegisterActivity extends BaseActivity implements OnClickListener, OnFocusChangeListener, TextWatcher {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.iv_mobile_close)
    ImageView ivMobileClose;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_psw_show)
    ImageView ivPswShow;
    @BindView(R.id.tv_has_account)
    TextView tvHasAccount;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.ll_agreement)
    LinearLayout llAgreement;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private Intent intent;
    private boolean isShowPsw;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    /**
     * 添加监听
     */
    protected void addListener() {
        etMobile.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
        etMobile.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        String appName = getString(R.string.app_name);
        tvVersion.setText(String.format(getString(R.string.app_version_show), ApkUtils.getVersionName(this)));
        tvAgreement.setText(String.format(getString(R.string.agreement_app), appName));
        tvHasAccount.setText(String.format(getString(R.string.has_account), appName));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    /**
     * 显示或隐藏密码
     */
    private void changeShowOrHidePsw() {
        isShowPsw = !isShowPsw;
        ivPswShow.setImageResource(isShowPsw ? R.drawable.eye_open : R.drawable.eye_close);
        etPassword.setTransformationMethod(isShowPsw ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etPassword.setSelection(etPassword.getText().length());
    }

    @OnClick({R.id.ll_submit, R.id.tv_login, R.id.tv_agreement, R.id.iv_mobile_close, R.id.iv_psw_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_submit:// 注册
                String mobilePhone = etMobile.getText().toString();
                if (TextUtils.isEmpty(mobilePhone)) {
                    showResMessage(R.string.register_number_is_empty);
                    return;
                }
                if (!MobileUtils.isMobileNO(mobilePhone)) {
                    showResMessage(R.string.mobile_phone_error);
                    return;
                }

                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    showResMessage(R.string.register_psw_is_empty);
                    return;
                }
                if (password.length() < 6) {
                    showResMessage(R.string.register_psw_length_not_enough);
                    return;
                }
                navigateToValidate(mobilePhone, password);
                break;
            case R.id.tv_login:// 登录
                onBackPressed();
                break;
            case R.id.tv_agreement:// 协议
                navigateToAgreement();
                break;
            case R.id.iv_mobile_close:
                etMobile.setText("");
                break;

            case R.id.iv_psw_show://密码显示与隐藏
                changeShowOrHidePsw();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == etPassword) {
            ivPswShow.setVisibility(!TextUtils.isEmpty(etPassword.getText().toString()) ? View.VISIBLE : View.INVISIBLE);
        } else if (v == etMobile) {
            ivMobileClose.setVisibility(hasFocus && !TextUtils.isEmpty(etMobile.getText().toString()) ? View.VISIBLE : View.INVISIBLE);
        }
        playHasFocusAnimation(hasFocus);
    }

    // 获得或者失去焦点的动画
    private void playHasFocusAnimation(final boolean hasFocus) {
        AnimationSet animationSet = AnimationFactory.createInputFocusAnimation(false, 100, hasFocus);

        if (hasFocus) {
            llLogin.setAnimation(animationSet);
        } else {
            llAgreement.setAnimation(animationSet);
        }
        animationSet.start();
        animationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if (hasFocus) {
                    llMain.scrollTo(0, ivLogo.getHeight());// 此处为得到焦点时的处理内容
                    tvVersion.setVisibility(View.GONE);
                } else {
                    tvVersion.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llLogin.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
                llAgreement.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * 跳转验证码的界面
     *
     * @param mobilePhone
     * @param password
     */
    private void navigateToValidate(String mobilePhone, String password) {
        intent = new Intent(this, ValidateActivity.class);
        intent.putExtra("type", BuildConf.AuthCodeType.REGISTER);
        intent.putExtra("mobilePhone", mobilePhone);
        intent.putExtra("encryptPassword", EncryptUtils.MD5Encrypt(password));
        startActivityForResult(intent, BuildConf.RequestResultCode.REGISTER_REQUEST);
    }

    /**
     * 跳转到软件协议的界面
     */
    private void navigateToAgreement() {
        intent = new Intent(this, WebViewCommonActivity.class);
        intent.putExtra("type", BuildConf.WebViewType.AGREEMENT);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        llMain.setFocusable(true);
        llMain.setFocusableInTouchMode(true);
        llMain.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
        llMain.scrollTo(0, 0);
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BuildConf.RequestResultCode.REGISTER_REQUEST && resultCode == RESULT_OK) {
            setResult(resultCode, data);
            finish();
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
    public void afterTextChanged(Editable s) {
        if (etMobile.hasFocus())
            ivMobileClose.setVisibility(TextUtils.isEmpty(etMobile.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
        if (etPassword.hasFocus())
            ivPswShow.setVisibility(TextUtils.isEmpty(etPassword.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }
}
