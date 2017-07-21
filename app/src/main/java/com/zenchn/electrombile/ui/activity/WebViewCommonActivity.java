package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/21 19:03
 * 描    述：
 * 修订记录：
 */
public class WebViewCommonActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.in_head)
    View inHead;

    private int type;
    private String url;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
        initWebView();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        switch (type) {
            case BuildConf.WebViewType.AGREEMENT:// 软件许可及服务协议
                tvTitle.setText("软件许可及服务协议");
                url = "file:///android_asset/web/agreement.html";
                break;

            case BuildConf.WebViewType.STEAL_INSURANCE:// 盗窃险
                tvTitle.setText("盗窃险");
                url = "file:///android_asset/web/steal_insurance.html";
                break;

            case BuildConf.WebViewType.RESPONSIBILITY_INSURANCE:// 第三方责任险
                tvTitle.setText("第三方责任险");
                url = "file:///android_asset/web/responsibility_insurance.html";
                webView.loadUrl("file:///android_asset/web/responsibility_insurance.html");
                break;

            case BuildConf.WebViewType.INSURANCE_CLAIM_PROCESS:// 理赔流程
                tvTitle.setText("理赔流程");
                url = "file:///android_asset/web/claim_process.html";
                break;

            case BuildConf.WebViewType.INSURANCE_CONTENT:// 保险内容
                tvTitle.setText("保险内容");
                url = "file:///android_asset/web/insurance_content.html";
                break;

            case BuildConf.WebViewType.INSURANCE_DECLARE:// 免责声明
                tvTitle.setText("重要通知");
                url = "file:///android_asset/web/insurance_declaratio.html";
                webView.loadUrl("file:///android_asset/web/insurance_declaratio.html");
                break;

            case BuildConf.WebViewType.USER_DEFINED_WEB:// 用户自定义
                String title = intent.getStringExtra("title");
                url = intent.getStringExtra("url");
                tvTitle.setText(TextUtils.isEmpty(title) ? "欢迎访问" : title);
                break;

            case BuildConf.WebViewType.USER_DEFINED_WEB_NO_TITLE:// 用户自定义（无标题）
                inHead.setVisibility(View.GONE);
                url = intent.getStringExtra("url");
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }


    /**
     * 配置webView属性
     */
    private void initWebView() {
        webView.clearCache(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        // 设置编码格式
        webSettings.setDefaultTextEncodingName(BuildConf.DEFAULT_ENCODING_CHARSET);

        if (BuildConf.WebViewType.USER_DEFINED_WEB == type) {
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);// 设置js可以直接打开窗口，如window.open()，默认为false
            webSettings.setJavaScriptEnabled(true);// 是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
            webSettings.setSupportZoom(true);// 是否可以缩放，默认true
            webSettings.setBuiltInZoomControls(true);// 是否显示缩放按钮，默认false
            webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放。大视图模式
            webSettings.setLoadWithOverviewMode(true);// 和setUseWideViewPort(true)一起解决网页自适应问题
            webSettings.setAppCacheEnabled(true);// 是否使用缓存
            webSettings.setDomStorageEnabled(true);// DOM Storage
            // webSettings.setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.post(new Runnable() {
            public void run() {
                webView.loadUrl(url);
            }
        });
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        onBackPressed();
    }
}
