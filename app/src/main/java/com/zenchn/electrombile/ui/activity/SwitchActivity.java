package com.zenchn.electrombile.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.ClientInfo;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.presenter.SwitchPresenter;
import com.zenchn.electrombile.presenter.impl.SwitchPresenterImpl;
import com.zenchn.electrombile.router.Router;
import com.zenchn.electrombile.ui.view.SwitchView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.mlibrary.utils.DateUtils;

/**
 * 作    者：wangr on 2017/2/21 17:31
 * 描    述：
 * 修订记录：
 */
public class SwitchActivity extends BaseActivity implements SwitchView {

    private SwitchPresenter presenter;

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public SwitchActivity() {
        presenter = new SwitchPresenterImpl(this);
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        if (loginInfo != null) {
            ClientInfo baseClientInfo = CommonUtils.getBaseClientInfo(this);
            String serialNumber = getIntent().getStringExtra("serialNumber");
            if (CommonUtils.isNonNull(serialNumber))
                baseClientInfo.setSerialNumber(serialNumber);
            long logoutTime = ACacheWrapper.getLogoutTime(loginInfo.getLoginName());
            long currentTime = DateUtils.getCurrentTime();
            baseClientInfo.setQuitTime(Math.min(logoutTime, currentTime));
            presenter.getUserInfo(baseClientInfo, loginInfo);
        } else {
            navigateToLogin();
        }
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void onGetUserSuccess(LoginInfo loginInfo) {
        ApplicationKit.getInstance().updateLoginInfo(loginInfo);
        ACacheWrapper.tempLoginInfo(loginInfo);
        navigateToHome();
    }

    @Override
    public void onGetUserFailure(String err_message) {
        if (CommonUtils.isEmpty(err_message)) {
            showResMessage(R.string.login_error);
        } else {
            showMessage(err_message);
        }
        navigateToLogin();
    }

    @Override
    public void showAnimation() {

    }

    @Override
    public void stopAnimation() {

    }

    private void navigateToLogin() {
        LoginActivity.launch(this);
        finish();
    }

    private void navigateToHome() {
        HomePageActivity.launch(this);
        finish();
    }

    public static void launch(Activity activity) {
        launch(activity, new Bundle());
    }

    public static void launch(Activity activity, Bundle bundle) {
        Router
                .newIntent()
                .from(activity)
                .to(SwitchActivity.class)
                .data(bundle)
                .launch();
    }

}
