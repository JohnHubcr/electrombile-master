package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.widget.Dialog.CommonDialogFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 9:28
 * 描    述：App描述类
 * 修订记录：
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.tv_hot_line)
    TextView tvHotLine;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        String appName = getString(R.string.app_name);
        tvTitle.setText(String.format(getString(R.string.title_about), appName));
        tvFollow.setText(String.format(getString(R.string.follow_app_name), appName));
        tvVersion.setText(getResources().getString(R.string.app_version));
        tvHotLine.setText(getResources().getString(R.string.app_hot_line));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @OnClick({R.id.ll_back, R.id.ll_guide, R.id.ll_agreements, R.id.ll_follow, R.id.ll_hot_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:// 返回
                onBackPressed();
                break;
            case R.id.ll_agreements:// 软件许可及服务协议
                Intent intent = new Intent(this, WebViewCommonActivity.class);
                intent.putExtra("webType", BuildConf.WebViewType.AGREEMENT);
                startActivity(intent);
                break;
            case R.id.ll_guide:// 新功能介绍
                CommonDialogFactory.createSimpleDialog(this, getString(R.string.coming_soon), Constants.SHOW_DIALOG_SHORT_TIME);
                break;
            case R.id.ll_hot_line:// 拨打客服电话
                CommonDialogFactory.createCallDialog(this, getString(R.string.ask_call_service), tvHotLine.getText().toString()).show();
                break;
            case R.id.ll_follow:// 关注微信号
                CommonDialogFactory.createSimpleDialog(this, getString(R.string.coming_soon), Constants.SHOW_DIALOG_SHORT_TIME);
                break;
        }
    }
}
