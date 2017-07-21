package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.wrapper.ACacheWrapper;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 16:26
 * 描    述：修改账号密码验证的界面
 * 修订记录：
 */
public class ModifyAccountValidateStatusActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_result)
    ImageView ivResult;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_button)
    TextView tvButton;

    private String encryptPassword;
    private boolean result;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void handler(Message msg) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_safety_verification_status;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_modify_account_with_safety_verification));
        encryptPassword = getIntent().getStringExtra("encryptPassword");
        UserInfo userInfo = ACacheWrapper.getUserInfo();
        if (userInfo.getEncryptPassword() != null) {
            result = userInfo.getEncryptPassword().equals(encryptPassword);
            if (result) {
                ivResult.setImageResource(R.drawable.check);
                tvResult.setText(R.string.modify_account_check_ok);
                tvButton.setText(getString(R.string.modify_account_button_next));
            } else {
                ivResult.setImageResource(R.drawable.error);
                tvResult.setText(R.string.modify_account_check_error);
                tvButton.setText(getString(R.string.modify_account_button_resume_input));
            }
        } else {
            showResMessage(R.string.modify_account_check_error);
            onBackPressed();
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_button:
                if (result) {
                    Intent intent = new Intent(this, ModifyAccountWelcomeActivity.class);
                    intent.putExtra("encryptPassword", encryptPassword);
                    startActivityForResult(intent, BuildConf.RequestResultCode.MODIFY_ACCOUNT_REQUEST);
                } else {
                    onBackPressed();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BuildConf.RequestResultCode.MODIFY_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_FIRST_USER) {
                setResult(resultCode);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
