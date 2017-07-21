package com.zenchn.electrombile.ui.activity;


import android.content.Intent;
import android.os.Bundle;
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
 * 作者：wangr on 2016/12/5 16:48
 * 描述： 发送验证码的界面
 */
public class SendValidateActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_mobile_phone)
    EditText etMobilePhone;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    private String type;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    private void addListener() {
        etMobilePhone.addTextChangedListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        if (type.equals(BuildConf.AuthCodeType.MODIFY_PWD))
            tvTitle.setText(getString(R.string.title_reset_password));
        else if (type.equals(BuildConf.AuthCodeType.BIND_EQUIPMENT))
            tvTitle.setText(getString(R.string.title_bind_equipment));
        else
            tvTitle.setText(getString(R.string.title_input_mobilePhone));
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_send_validate;
    }

    public void navigateToSendValidate(String type, String mobilePhone) {
        Intent intent = new Intent(this, ValidateActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("mobilePhone", mobilePhone);
        startActivityForResult(intent, BuildConf.RequestResultCode.RESET_PASSWORD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BuildConf.RequestResultCode.RESET_PASSWORD_REQUEST) {
            setResult(resultCode);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.ll_back, R.id.iv_close, R.id.ll_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:// 关闭
                onBackPressed();
                break;
            case R.id.ll_submit:// 发送验证码
                String mobilePhone = etMobilePhone.getText().toString();
                if (TextUtils.isEmpty(mobilePhone)) {
                    showResMessage(R.string.mobile_phone_empty);
                    return;
                }
                //判断是否是一个手机
                if (MobileUtils.isMobileNO(mobilePhone)) {
                    navigateToSendValidate(type, mobilePhone);
                } else {
                    showResMessage(R.string.mobile_phone_error);
                }
                break;
            case R.id.iv_close:
                etMobilePhone.setText("");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
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
        ivClose.setVisibility(TextUtils.isEmpty(etMobilePhone.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

}
