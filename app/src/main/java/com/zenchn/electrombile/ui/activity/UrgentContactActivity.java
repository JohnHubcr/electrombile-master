package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.presenter.UrgentContactPresenter;
import com.zenchn.electrombile.presenter.impl.UrgentContactPresenterImpl;
import com.zenchn.electrombile.ui.view.UrgentContactView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.mlibrary.utils.MobileUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：wangr on 2016/12/5 16:34
 * 描述：紧急联系人添加（修改）的页面
 */
public class UrgentContactActivity extends BaseActivity implements UrgentContactView, TextWatcher, CommonDialogFactory.OnConfirmListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_urgent_contact)
    EditText etUrgentContact;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.iv_choose_contact)
    ImageView ivChooseContact;

    private UrgentContactPresenter presenter;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        removeListener();
        super.onDestroy();
    }

    public UrgentContactActivity() {
        presenter = new UrgentContactPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_urgent_contact;
    }

    protected void addListener() {
        etUrgentContact.addTextChangedListener(this);
    }

    protected void removeListener() {
        etUrgentContact.removeTextChangedListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String urgentContact = intent.getStringExtra("urgentContact");
        if (CommonUtils.isEmpty(urgentContact)) {
            tvTitle.setText(getString(R.string.urgent_contact));
        } else {
            etUrgentContact.setText(urgentContact);
            etUrgentContact.setSelection(urgentContact.length());
            tvTitle.setText(getString(R.string.urgent_contact_modify));
        }
    }

    @OnClick({R.id.ll_back, R.id.tv_commit, R.id.iv_clear, R.id.iv_choose_contact})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_contact:
                startActivityForResult(new Intent(this, ChooseContactsActivity.class), BuildConf.RequestResultCode.CHOOSE_CONTACTS_REQUEST);
                break;

            case R.id.tv_commit:
                askUserConfirm(etUrgentContact.getText().toString());
                break;

            case R.id.iv_clear:
                etUrgentContact.setText("");
                break;

            case R.id.ll_back:
                onBackPressed();
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
    public void afterTextChanged(Editable edt) {
        String number = edt.toString();
        if (CommonUtils.isNonNull(number)) {
            ivChooseContact.setVisibility(View.GONE);
            ivClear.setVisibility(View.VISIBLE);
        } else {
            ivClear.setVisibility(View.GONE);
            ivChooseContact.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BuildConf.RequestResultCode.CHOOSE_CONTACTS_REQUEST) {

            if (RESULT_OK == resultCode) {
                String mobilePhone = data.getStringExtra("mobilePhone");
                if (CommonUtils.isNonNull(mobilePhone)) {
                    etUrgentContact.setText(mobilePhone);
                    etUrgentContact.setSelection(mobilePhone.length());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void updateUrgentContact(String urgentContact) {
        ApplicationKit.getInstance().getLoginInfo().setOtherPhone(urgentContact);
        showResMessage(R.string.urgent_contact_commit_success);
        navigateToSetting(urgentContact);
    }

    private void navigateToSetting(String urgentContact) {
        Intent data = new Intent();
        data.putExtra("urgentContact", urgentContact);
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * 提示框
     *
     * @param emergentContact
     */
    private void askUserConfirm(String emergentContact) {

        if (CommonUtils.isEmpty(loginInfo.getOtherPhone())) {
            if (CommonUtils.isEmpty(emergentContact))
                showResMessage(R.string.urgent_contact_empty);
            else if (MobileUtils.isMobileNO(emergentContact))
                CommonDialogFactory.createStandardDialog(this, getString(R.string.confirm_add_emergent_contact), this).show();
            else
                showResMessage(R.string.urgent_contact_error);
        } else {
            if (CommonUtils.isEmpty(emergentContact))
                CommonDialogFactory.createStandardDialog(this, getString(R.string.confirm_remove_emergent_contact), this).show();
            else if (MobileUtils.isMobileNO(emergentContact))
                CommonDialogFactory.createStandardDialog(this, getString(R.string.confirm_add_emergent_contact), this).show();
            else
                showResMessage(R.string.urgent_contact_error);
        }
    }

    @Override
    public void onConfirm() {
        presenter.updateUrgentContact(loginInfo.getAccessToken(), etUrgentContact.getText().toString());
    }

}
