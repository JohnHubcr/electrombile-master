package com.zenchn.electrombile.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.router.Router;

/**
 * 作    者：wangr on 2017/2/21 17:33
 * 描    述：
 * 修订记录：
 */

public class GuideActivity extends BaseActivity {

    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    public static void launch(Activity activity) {
        Router
                .newIntent()
                .from(activity)
                .to(GuideActivity.class)
                .data(new Bundle())
                .launch();
    }
}
