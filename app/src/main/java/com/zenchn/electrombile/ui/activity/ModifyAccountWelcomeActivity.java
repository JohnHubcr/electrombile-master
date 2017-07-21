package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.mlibrary.utils.MobileUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 16:23
 * 描    述：修改账号用户确认的界面
 * 修订记录：
 */
public class ModifyAccountWelcomeActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    private String encryptPassword;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_account_welcome;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_modify_account_with_ask_query));
        tvAccount.setText(MobileUtils.getEncryptMobileNO(loginInfo.getLoginName()));
        encryptPassword = getIntent().getStringExtra("encryptPassword");
    }

    @OnClick({R.id.ll_back, R.id.ll_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_commit:
                Intent intent = new Intent(this, ModifyAccountQueryActivity.class);
                intent.putExtra("encryptPassword", encryptPassword);
                startActivityForResult(intent, BuildConf.RequestResultCode.MODIFY_ACCOUNT_REQUEST);
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
