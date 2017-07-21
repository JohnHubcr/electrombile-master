package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.mlibrary.utils.EncryptUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * 作    者：wangr on 2017/2/22 16:26
 * 描    述：修改账号安全验证的界面
 * 修订记录：
 */
public class ModifyAccountValidateActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.iv_psw_show)
    ImageView ivPswShow;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    private void addListener() {
        etPsw.addTextChangedListener(this);
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_modify_account_with_safety_verification));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_safety_verification;
    }

    @OnTouch({R.id.iv_psw_show})
    public boolean onTouchEvent(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            showPsw();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            hidePsw();
        }
        return true;
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }

    /**
     * 隐藏密码（密文）
     */
    private void hidePsw() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivPswShow.setImageResource(R.drawable.eye_close);
                etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etPsw.setSelection(etPsw.getText().length());
            }
        }, 1000);
    }

    /**
     * 显示密码（明文）
     */
    private void showPsw() {
        ivPswShow.setImageResource(R.drawable.eye_open);
        etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        etPsw.setSelection(etPsw.getText().length());
    }

    @OnClick({R.id.ll_next, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_next:
                String psw = etPsw.getText().toString();
                if (TextUtils.isEmpty(psw)) {
                    showResMessage(R.string.modify_account_by_password_empty);
                    return;
                }

                Intent intent = new Intent(this, ModifyAccountValidateStatusActivity.class);
                intent.putExtra("encryptPassword", EncryptUtils.MD5Encrypt(psw));
                startActivityForResult(intent, BuildConf.RequestResultCode.MODIFY_ACCOUNT_REQUEST);
                break;

            case R.id.ll_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BuildConf.RequestResultCode.MODIFY_ACCOUNT_REQUEST) {
            if (resultCode == RESULT_FIRST_USER) {
                onBackPressed();
            }
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
        ivPswShow.setVisibility(TextUtils.isEmpty(etPsw.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

}
