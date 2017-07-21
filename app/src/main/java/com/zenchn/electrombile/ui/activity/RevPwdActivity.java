package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.presenter.RevPwdPresenter;
import com.zenchn.electrombile.presenter.impl.RevPwdPresenterImpl;
import com.zenchn.electrombile.ui.view.RevPwdView;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 16:00
 * 描    述：修改密码的界面
 * 修订记录：
 */
public class RevPwdActivity extends BaseActivity implements RevPwdView, View.OnClickListener, TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.iv_old_show)
    ImageView ivOldShow;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.iv_new_show)
    ImageView ivNewShow;
    @BindView(R.id.et_new_password_again)
    EditText etNewPasswordAgain;
    @BindView(R.id.iv_again_show)
    ImageView ivAgainShow;

    private RevPwdPresenter presenter;
    private boolean isOldShow;
    private boolean isNewShow;
    private boolean isAgainShow;

    public RevPwdActivity() {
        presenter = new RevPwdPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    protected void addListener() {
        etOldPassword.addTextChangedListener(this);
        etNewPassword.addTextChangedListener(this);
        etNewPasswordAgain.addTextChangedListener(this);
    }

    protected void initData() {
        tvTitle.setText(getString(R.string.title_modify_psw));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    /**
     * 显示或隐藏密码
     */
    private void changeShowOrHidePsw(int id) {
        switch (id) {
            case R.id.iv_old_show:
                isOldShow = !isOldShow;
                ivOldShow.setImageResource(isOldShow ? R.drawable.eye_open : R.drawable.eye_close);
                etOldPassword.setTransformationMethod(isOldShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                etOldPassword.setSelection(etOldPassword.getText().length());
                break;
            case R.id.iv_new_show:
                isNewShow = !isNewShow;
                ivNewShow.setImageResource(isNewShow ? R.drawable.eye_open : R.drawable.eye_close);
                etNewPassword.setTransformationMethod(isNewShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                etNewPassword.setSelection(etNewPassword.getText().length());
                break;
            case R.id.iv_again_show:
                isAgainShow = !isAgainShow;
                ivAgainShow.setImageResource(isAgainShow ? R.drawable.eye_open : R.drawable.eye_close);
                etNewPasswordAgain.setTransformationMethod(isAgainShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                etNewPasswordAgain.setSelection(etNewPasswordAgain.getText().length());
                break;
        }
    }

    @OnClick({R.id.ll_back, R.id.tv_commit, R.id.iv_old_show, R.id.iv_new_show, R.id.iv_again_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;

            case R.id.iv_old_show:
                changeShowOrHidePsw(R.id.iv_old_show);
                break;

            case R.id.iv_again_show:
                changeShowOrHidePsw(R.id.iv_again_show);
                break;

            case R.id.iv_new_show:
                changeShowOrHidePsw(R.id.iv_new_show);
                break;

            case R.id.tv_commit:
                String oldPassword = etOldPassword.getText().toString();
                if (TextUtils.isEmpty(oldPassword)) {
                    showResMessage(R.string.modify_password_by_old_password_empty);
                    return;
                }
                if (oldPassword.length() < 6) {
                    showResMessage(R.string.modify_password_by_new_password_length_not_enough);
                    return;
                }

                String newPassword = etNewPassword.getText().toString();
                if (TextUtils.isEmpty(newPassword)) {
                    showResMessage(R.string.modify_password_by_new_password_empty);
                    return;
                }
                if (newPassword.length() < 6) {
                    showResMessage(R.string.modify_password_by_new_password_length_not_enough);
                    return;
                }

                String newAgainPassword = etNewPasswordAgain.getText().toString();
                if (!newAgainPassword.equals(newPassword)) {
                    showResMessage(R.string.modify_password_by_twice_password_differ);
                    return;
                }
                presenter.revPwd(loginInfo.getAccessToken(), newPassword, oldPassword);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        boolean oldEmpty = TextUtils.isEmpty(etOldPassword.getText().toString());
        ivOldShow.setVisibility(oldEmpty ? View.INVISIBLE : View.VISIBLE);
        boolean newEmpty = TextUtils.isEmpty(etNewPassword.getText().toString());
        ivNewShow.setVisibility(newEmpty ? View.INVISIBLE : View.VISIBLE);
        boolean newAgainEmpty = TextUtils.isEmpty(etNewPasswordAgain.getText().toString());
        ivAgainShow.setVisibility(newAgainEmpty ? View.INVISIBLE : View.VISIBLE);

        if (oldEmpty || newEmpty || newAgainEmpty) {
            tvCommit.setTextColor(getResources().getColor(R.color.base_text_color_dark));
        } else {
            tvCommit.setTextColor(getResources().getColor(R.color.base_text_color_light));
        }
    }

    @Override
    public void modifySuccess(String encryptNewPassword) {
        UserInfo userInfo = ACacheWrapper.getUserInfo();
        userInfo.setEncryptPassword(encryptNewPassword);
        ACacheWrapper.saveUserInfo(userInfo);
        showResMessage(R.string.modify_password_success);
        finish();
    }


}
