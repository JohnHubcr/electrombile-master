package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.presenter.SettingPresenter;
import com.zenchn.electrombile.presenter.impl.SettingPresenterImpl;
import com.zenchn.electrombile.ui.view.SettingView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.utils.EquModelUtils;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.mlibrary.utils.FileUtils;
import com.zenchn.mlibrary.utils.MobileUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 14:56
 * 描    述：用户设置中心
 * 修订记录：
 */
public class SettingActivity extends BaseActivity implements SettingView, CommonDialogFactory.OnConfirmListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_urgent_contact)
    TextView tvUrgentContact;
    @BindView(R.id.ra_setting_point)
    RelativeLayout raSettingPoint;
    @BindView(R.id.tv_notification_isOpen)
    TextView tvNotificationIsOpen;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.tv_about)
    TextView tvAbout;

    private SettingPresenter presenter;
    private Boolean isHideFunction;

    public SettingActivity() {
        presenter = new SettingPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_setting));
        tvAbout.setText(String.format(getString(R.string.about_app), getString(R.string.app_name)));
        tvAccount.setText(MobileUtils.getEncryptMobileNO(loginInfo.getLoginName()));
        isHideFunction = EquModelUtils.isHideFunction(loginInfo.getEquModel());
        showUrgentContact(loginInfo.getOtherPhone());
        showTempInfo();
    }

    /**
     * 显示紧急联系人信息
     *
     * @param urgentContact
     */
    private void showUrgentContact(String urgentContact) {
        if (CommonUtils.isEmpty(urgentContact)) {
            tvUrgentContact.setVisibility(View.GONE);
            raSettingPoint.setVisibility(View.VISIBLE);
        } else {
            tvUrgentContact.setText(urgentContact);
            tvUrgentContact.setVisibility(View.VISIBLE);
            raSettingPoint.setVisibility(View.GONE);
        }
    }

    private void showTempInfo() {
        File logs = new File(Constants.appFolder);
        File files = new File(Constants.appFolder + File.separator + Constants.FILE_DIR_FILES);
        long dirSize = FileUtils.getFileSize(logs) + FileUtils.getFileSize(files);
        tvCacheSize.setText(FileUtils.formatFileSize(dirSize));
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean alarmNotificationStatus = ACacheWrapper.getAlarmNotificationStatus();
        if (alarmNotificationStatus) {
            tvNotificationIsOpen.setText(getString(R.string.loudspeaker_mute_mode));
            tvNotificationIsOpen.setVisibility(View.VISIBLE);
        } else {
            tvNotificationIsOpen.setVisibility(View.GONE);
        }
    }

    @Override
    public void navigateToLogin() {
        ApplicationKit applicationKit = ApplicationKit.getInstance();
        applicationKit.restartApp();
    }

    @Override
    public void clearLoginCache() {
        ACacheWrapper.removeUserInfo();//移除登录信息
    }

    @OnClick({R.id.ll_back, R.id.ll_modify_account, R.id.ll_modify_password, R.id.ll_urgent_contacts, R.id.ll_notification, R.id.ll_clear_cache, R.id.ll_feedback, R.id.ll_about, R.id.ll_quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.ll_modify_account:
                startActivity(new Intent(this, ModifyAccountValidateActivity.class));
                break;
            case R.id.ll_modify_password:
                startActivity(new Intent(this, RevPwdActivity.class));
                break;
            case R.id.ll_urgent_contacts:
                if (isHideFunction == null) {
                    CommonDialogFactory.createBindDialog(this).show();
                } else {
                    Intent intent = new Intent(this, UrgentContactActivity.class);
                    intent.putExtra("urgentContact", loginInfo.getOtherPhone());
                    startActivityForResult(intent, BuildConf.RequestResultCode.UPDATE_CONTACTS_REQUEST);
                }
                break;
            case R.id.ll_notification:
                if (isHideFunction == null) {
                    CommonDialogFactory.createBindDialog(this).show();
                } else {
                    startActivity(new Intent(this, NotificationModeActivity.class));
                }
                break;
            case R.id.ll_clear_cache:
                if (CommonUtils.isNonNull(tvCacheSize.getText().toString())) {
                    CommonDialogFactory.createClearCacheDialog(this, tvCacheSize.getText().toString(), this).show();
                } else {
                    showResMessage(R.string.clear_success);
                }
                break;
            case R.id.ll_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.ll_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.ll_quit:
                if (isHideFunction == null) {
                    clearLoginCache();//清理登录缓存
                    navigateToLogin();//未绑定设备直接跳到登录界面
                } else {
                    presenter.logout(loginInfo.getLoginName(), loginInfo.getAccessToken());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BuildConf.RequestResultCode.UPDATE_CONTACTS_REQUEST && RESULT_OK == resultCode) {
            String urgentContact = data.getStringExtra("urgentContact");
            loginInfo.setOtherPhone(urgentContact);
            showUrgentContact(urgentContact);
        }
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("urgentContact", loginInfo.getOtherPhone());
        setResult(RESULT_CANCELED, data);
        finish();
    }

    @Override
    public void onConfirm() {
        File logs = new File(Constants.appFolder + File.separator + Constants.FILE_DIR_CACHE);
        File files = new File(Constants.appFolder + File.separator + Constants.FILE_DIR_FILES);
        File downloads = new File(Constants.appFolder + File.separator + Constants.FILE_DIR_DOWNLOAD);
        FileUtils.deleteDirectory(logs.getAbsolutePath());
        FileUtils.deleteDirectory(files.getAbsolutePath());
        FileUtils.deleteDirectory(downloads.getAbsolutePath());
        tvCacheSize.setText("");
    }

}
