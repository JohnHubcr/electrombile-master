package com.zenchn.electrombile.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.presenter.LoginPresenter;
import com.zenchn.electrombile.presenter.impl.LoginPresenterImpl;
import com.zenchn.electrombile.router.Router;
import com.zenchn.electrombile.ui.view.LoginView;
import com.zenchn.electrombile.widget.AnimationFactory;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.ActionSheet;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.mlibrary.utils.ApkUtils;
import com.zenchn.mlibrary.utils.EncryptUtils;
import com.zenchn.mlibrary.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/21 14:31
 * 描    述：
 * 修订记录：
 */
public class LoginActivity extends BaseActivity implements LoginView, View.OnFocusChangeListener, TextWatcher, ActionSheet.MenuItemClickListener {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.iv_login_name_close)
    ImageView ivLoginNameClose;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_psw_show)
    ImageView ivPswShow;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.ll_input_box)
    LinearLayout llInputBox;

    private LoginPresenter presenter;
    private Intent intent;
    private boolean isShowPsw;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public LoginActivity() {
        presenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    @Override
    protected void initData() {
        tvVersion.setText(String.format(getString(R.string.app_version_show), ApkUtils.getVersionName(this)));
        String loginNameHistory = ACacheWrapper.getLoginNameHistory();
        if (!TextUtils.isEmpty(loginNameHistory))
            etLoginName.setText(loginNameHistory);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        llMain.setFocusable(true);
        llMain.setFocusableInTouchMode(true);
        llMain.requestFocus();
        tvRegister.setVisibility(View.VISIBLE);
        tvMenu.setVisibility(View.GONE);
        llMain.scrollTo(0, 0);
    }

    /**
     * 增加监听
     */
    protected void addListener() {
        etLoginName.setOnFocusChangeListener(this);
        etPassword.setOnFocusChangeListener(this);
        etLoginName.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            presenter.exit(mHandler);
            return false;
        }
        return super.onKeyDown(keyCode, event);
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

    @OnClick({R.id.ll_submit, R.id.tv_register, R.id.tv_menu, R.id.iv_login_name_close, R.id.iv_psw_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_name_close://清除用户名
                etLoginName.setText("");
                break;

            case R.id.iv_psw_show://密码显示与隐藏
                changeShowOrHidePsw();
                break;

            case R.id.ll_submit:// 登录

                String loginName = etLoginName.getText().toString();
                if (TextUtils.isEmpty(loginName)) {
                    showResMessage(R.string.login_name_empty);
                    return;
                }

                String loginPassword = etPassword.getText().toString();
                if (TextUtils.isEmpty(loginPassword)) {
                    showResMessage(R.string.login_psw_empty);
                    return;
                }
                login(loginName, EncryptUtils.MD5Encrypt(loginPassword));

                break;
            case R.id.tv_register:// 注册
                intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, BuildConf.RequestResultCode.REGISTER_REQUEST);
                break;
            case R.id.tv_menu:// 菜单
                showActionSheet();
                break;
        }

    }

    /**
     * 登录操作
     *
     * @param loginName
     * @param encryptPassword
     */
    private void login(String loginName, String encryptPassword) {
        UserInfo userInfo = CommonUtils.getBaseUserInfo(this);
        userInfo.setUsername(loginName);
        userInfo.setEncryptPassword(encryptPassword);
        presenter.login(userInfo);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == etPassword) {
            ivPswShow.setVisibility(!TextUtils.isEmpty(etPassword.getText().toString()) ? View.VISIBLE : View.INVISIBLE);
        } else if (v == etLoginName) {
            ivLoginNameClose.setVisibility(hasFocus && !TextUtils.isEmpty(etLoginName.getText().toString()) ? View.VISIBLE : View.INVISIBLE);
        }
        playHasFocusAnimation(hasFocus);
    }


    /**
     * 焦点动画
     *
     * @param hasFocus
     */
    private void playHasFocusAnimation(final boolean hasFocus) {
        AnimationSet animationSet = AnimationFactory.createInputFocusAnimation(false, 100, hasFocus);
        tvRegister.setAnimation(animationSet);
        animationSet.start();
        animationSet.setAnimationListener(new Animation.AnimationListener() {

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
                tvRegister.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
                tvMenu.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            }
        });
    }


    @Override
    public void onGetTokenSuccess(UserInfo userInfo, LoginInfo loginInfo) {
        if (loginInfo != null) {
            ApplicationKit.getInstance().updateLoginInfo(loginInfo);
            ACacheWrapper.tempLoginInfo(loginInfo);
            ACacheWrapper.saveUserInfo(userInfo);
            ACacheWrapper.saveLoginNameHistory(loginInfo.getLoginName());
            etPassword.setText("");
            SwitchActivity.launch(this);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        llMain.setFocusable(true);
        llMain.setFocusableInTouchMode(true);
        llMain.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(llMain.getWindowToken(), 0);
        tvRegister.setVisibility(View.VISIBLE);
        tvMenu.setVisibility(View.GONE);
        llMain.scrollTo(0, 0);
        return super.onTouchEvent(event);
    }

    public void showActionSheet() {
        setTheme(R.style.ActionSheetStyle);
        ActionSheet menuView = new ActionSheet(this);
        menuView.setCancelButtonTitle("取消");// before add items
//        menuView.addItems("重设密码", "手机验证码登录");
        menuView.addItems("重设密码");
        menuView.setItemClickListener(this);
        menuView.setCancelableOnTouchMenuOutside(true);
        menuView.showMenu();
    }

    @Override
    public void onItemClick(int itemPosition) {
        switch (itemPosition) {

            case 0:// 重设密码
                intent = new Intent(this, SendValidateActivity.class);
                intent.putExtra("type", BuildConf.AuthCodeType.MODIFY_PWD);
                startActivityForResult(intent, BuildConf.RequestResultCode.RESET_PASSWORD_REQUEST);
                break;
//            case 1:// 手机验证码登录
//                intent = new Intent(this, SendValidateActivity.class);
//                break;
        }
    }

    @Override
    public void showExitMessage() {
        showResMessage(R.string.simple_again_exit);
    }

    @Override
    public void exit() {
        ToastUtils.cancelCurrentToast();
        ApplicationKit.getInstance().exitApp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == BuildConf.RequestResultCode.REGISTER_REQUEST) {
            UserInfo userInfo = (UserInfo) data.getSerializableExtra("data");
            login(userInfo.getUsername(), userInfo.getEncryptPassword());
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
    public void afterTextChanged(Editable edit) {
        if (etLoginName.hasFocus())
            ivLoginNameClose.setVisibility(TextUtils.isEmpty(etLoginName.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
        if (etPassword.hasFocus())
            ivPswShow.setVisibility(TextUtils.isEmpty(etPassword.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

    public static void launch(Activity activity) {
        Router
                .newIntent()
                .from(activity)
                .to(LoginActivity.class)
                .data(new Bundle())
                .launch();
    }
}
