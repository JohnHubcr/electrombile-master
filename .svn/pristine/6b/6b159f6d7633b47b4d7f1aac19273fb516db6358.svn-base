package com.zenchn.electrombile.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.presenter.ResetPwdPresenter;
import com.zenchn.electrombile.presenter.impl.ResetPwdPresenterImpl;
import com.zenchn.electrombile.ui.view.ResetPwdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 17:16
 * 描    述：
 * 修订记录：
 */
public class ResetPwdActivity extends BaseActivity implements ResetPwdView, View.OnClickListener, TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.iv_new_close)
    ImageView ivNewClose;
    @BindView(R.id.et_again)
    EditText etAgain;
    @BindView(R.id.iv_again_close)
    ImageView ivAgainClose;

    private ResetPwdPresenter presenter;

    private String mobilePhone;
    private String authCode;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public ResetPwdActivity() {
        presenter = new ResetPwdPresenterImpl(this);
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 添加监听
     */
    protected void addListener() {
        etNew.addTextChangedListener(this);
        etAgain.addTextChangedListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mobilePhone = getIntent().getStringExtra("mobilePhone");
        authCode = getIntent().getStringExtra("authCode");
        tvTitle.setText(getString(R.string.title_reset_password));
    }

    @Override
    protected void handler(Message msg) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick({R.id.ll_back, R.id.ll_submit, R.id.iv_new_close, R.id.iv_again_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:// 关闭
                onBackPressed();
                break;
            case R.id.ll_submit:// 重设密码
                String newPassword = etNew.getText().toString();
                if (TextUtils.isEmpty(newPassword)) {
                    showResMessage(R.string.reset_new_password_empty);
                    return;
                }
                if (newPassword.length() < 6) {
                    showResMessage(R.string.reset_new_password_length_not_enough);
                    return;
                }
                String againPassword = etAgain.getText().toString();
                if (TextUtils.isEmpty(againPassword)) {
                    showResMessage(R.string.reset_again_password_empty);
                    return;
                }
                if (againPassword.length() < 6) {
                    showResMessage(R.string.reset_again_password_length_not_enough);
                    return;
                }
                if (!newPassword.equals(againPassword)) {
                    showResMessage(R.string.reset_password_differ);
                    return;
                }
                presenter.resetPwd(mobilePhone, newPassword, authCode);
                break;
            case R.id.iv_new_close:
                etNew.setText("");
                break;
            case R.id.iv_again_close:
                etAgain.setText("");
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
        ivNewClose.setVisibility(TextUtils.isEmpty(etNew.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
        ivAgainClose.setVisibility(TextUtils.isEmpty(etAgain.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void onRestPwdSuccess() {
        showResMessage(R.string.reset_password_success);
        setResult(RESULT_OK);
        finish();
    }

}
