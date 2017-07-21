package com.zenchn.electrombile.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.enrique.stackblur.StackBlurManager;
import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.LoginInfo;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.presenter.MainPresenter;
import com.zenchn.electrombile.presenter.impl.MainPresenterImpl;
import com.zenchn.electrombile.ui.view.MainView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.AnimationFactory;
import com.zenchn.electrombile.wrapper.ACacheWrapper;

import butterknife.BindView;

/**
 * 作    者：wangr on 2017/2/21 13:38
 * 描    述：启动界面
 * 修订记录：
 */

public class MainActivity extends BaseActivity implements MainView, Animation.AnimationListener {

    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.iv_blur)
    ImageView ivBlur;

    private MainPresenter presenter;
    private StackBlurManager stackBlurManager;
    private AnimationSet welcomeScaleAnimation;
    private Bitmap rawBitmap;
    private Bitmap blurBitmap;

    public MainActivity() {
        this.presenter = new MainPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        if (!this.isTaskRoot()) {
            finish();
            return;
        }
        setMainBackground();
        initData();
    }

    @Override
    protected void initData() {
        startAnimation();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseLogin();
            }
        }, BuildConf.ACTIVITY.MAIN_BLUR_ANIMATION_DURATION);
    }

    private void setMainBackground() {
        rawBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_start);
        ivMain.setImageBitmap(rawBitmap);
        stackBlurManager = new StackBlurManager(rawBitmap);
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

//    public void getBlurCache() {
//        ivMain
//                .getViewTreeObserver()
//                .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//
//                    @Override
//                    public boolean onPreDraw() {
//                        // 一般来说，对ViewTreeObserver注册的监听上的方法会多次调用，一般进来一次就够了，所以一进来就取消此监听
//                        ivMain.getViewTreeObserver().removeOnPreDrawListener(this);
//                        ivMain.buildDrawingCache();// 在截图之前先要创建一把截图
//                        Bitmap bitmap = ivMain.getDrawingCache();// 对View进行截图
//                        stackBlurManager = new StackBlurManager(bitmap);
//                        return true;
//                    }
//                });
//    }

    @Override
    public void startAnimation() {
        welcomeScaleAnimation = AnimationFactory.createWelcomeScaleAnimation(false, BuildConf.ACTIVITY.MAIN_BLUR_ANIMATION_DURATION);
        ivMain.startAnimation(welcomeScaleAnimation);
        welcomeScaleAnimation.setAnimationListener(this);
    }

    @Override
    public void stopAnimation() {
        if (welcomeScaleAnimation != null)
            welcomeScaleAnimation.cancel();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        ivBlur.setVisibility(View.VISIBLE);
        blurBitmap = stackBlurManager.process(BuildConf.ACTIVITY.MAIN_BLUR_PROCESS_RADIUS);
        ivBlur.setImageBitmap(blurBitmap);
        ivBlur.setScaleX(BuildConf.ACTIVITY.MAIN_BLUR_SCALE_VALUE);
        ivBlur.setScaleY(BuildConf.ACTIVITY.MAIN_BLUR_SCALE_VALUE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private void chooseLogin() {
        UserInfo localUserInfo = ACacheWrapper.getUserInfo();
        if (localUserInfo == null || CommonUtils.isEmpty(localUserInfo.getUsername())) {
            onAutoLoginFail();
        } else {
            onAutoLogin(localUserInfo);
        }
    }

    /**
     * 登录
     *
     * @param localUserInfo
     */
    private void onAutoLogin(UserInfo localUserInfo) {
        UserInfo userInfo = CommonUtils.getBaseUserInfo(this);
        userInfo.setUsername(localUserInfo.getUsername());
        userInfo.setEncryptPassword(localUserInfo.getEncryptPassword());
        presenter.login(userInfo);
    }

    @Override
    public void onAutoLoginSuccess(UserInfo userInfo, LoginInfo loginInfo) {
        ApplicationKit.getInstance().updateLoginInfo(loginInfo);
        ACacheWrapper.tempLoginInfo(loginInfo);
        ACacheWrapper.saveUserInfo(userInfo);
        ACacheWrapper.saveLoginNameHistory(loginInfo.getLoginName());
        SwitchActivity.launch(this);
        finish();
    }

    @Override
    public void onAutoLoginFail() {
        navigateToLogin();
    }

    public void navigateToLogin() {
        LoginActivity.launch(this);
        finish();
    }

    public void navigateToGuide() {
        GuideActivity.launch(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        CommonUtils.recycleBitmap(rawBitmap, blurBitmap);
    }
}
