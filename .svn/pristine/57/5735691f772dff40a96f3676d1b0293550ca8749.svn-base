package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.mlibrary.utils.MobileUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 16:27
 * 描    述：账号变更确认的界面
 * 修订记录：
 */
public class ModifyAccountQueryActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_modify_desc)
    TextView tvModifyDesc;
    @BindView(R.id.et_mobile_phone)
    EditText etMobilePhone;
    @BindView(R.id.iv_mobile_phone_clear)
    ImageView ivMobilePhoneClear;
    private String encryptPassword;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    private void addListener() {
        etMobilePhone.addTextChangedListener(this);
    }

    @Override
    protected void handler(Message msg) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_query_account;
    }


    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_modify_account_with_ask_query));
        encryptPassword = getIntent().getStringExtra("encryptPassword");
        tvModifyDesc.setText(String.format(getString(R.string.modify_account_with_safety_query), MobileUtils.getEncryptMobileNO(loginInfo.getLoginName())));
    }

    @OnClick({R.id.ll_back, R.id.iv_mobile_phone_clear, R.id.ll_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.iv_mobile_phone_clear:
                etMobilePhone.setText("");
                break;
            case R.id.ll_next:
                String mobilePhone = etMobilePhone.getText().toString();

                if (TextUtils.isEmpty(mobilePhone)) {
                    showResMessage(R.string.modify_account_by_phone_empty);
                    return;
                }

                if (!MobileUtils.isMobileNO(mobilePhone)) {
                    showResMessage(R.string.modify_account_by_phone_error);
                    return;
                }
                Intent intent = new Intent(this, ValidateActivity.class);
                intent.putExtra("type", BuildConf.AuthCodeType.ACCOUNT_REPLACE);
                intent.putExtra("mobilePhone", mobilePhone);
                intent.putExtra("encryptPassword", encryptPassword);
                startActivityForResult(intent, BuildConf.RequestResultCode.MODIFY_ACCOUNT_REQUEST);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_FIRST_USER);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        ivMobilePhoneClear.setVisibility(TextUtils.isEmpty(etMobilePhone.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

}

