package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.adapter.InsuranceProductAdapter;
import com.zenchn.electrombile.alipay.AliPayActivity;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.InsuranceProductInfo;
import com.zenchn.electrombile.entity.model.InsuranceStatusEnum;
import com.zenchn.electrombile.presenter.InsurancePayPresenter;
import com.zenchn.electrombile.presenter.impl.InsurancePayPresenterImpl;
import com.zenchn.electrombile.ui.view.InsurancePayView;
import com.zenchn.electrombile.widget.CircleTextImageView;
import com.zenchn.electrombile.widget.RecyclerView.decoration.SimpleDecoration;
import com.zenchn.electrombile.widget.RecyclerView.listener.OnItemSegmentClickListener;
import com.zenchn.mlibrary.utils.MobileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/3/7 13:43
 * 描    述：开通保险服务
 * 修订记录：
 */
public class InsurancePayActivity extends BaseActivity implements InsurancePayView, OnItemSegmentClickListener<InsuranceProductInfo> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    CircleTextImageView ivHead;
    @BindView(R.id.tv_mobile_phone)
    TextView tvMobilePhone;
    @BindView(R.id.rv)
    RecyclerView rv;

    private InsuranceProductAdapter adapter;
    private ArrayList<InsuranceProductInfo> mData;
    private InsurancePayPresenter presenter;

    private String insuranceStatus;

    public InsurancePayActivity() {
        presenter = new InsurancePayPresenterImpl(this);
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initRecycleView();
        initData();
    }

    private void initRecycleView() {

        //设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(this));

        //设置adapter
        adapter = new InsuranceProductAdapter(this, mData = new ArrayList<>());

        adapter.setListener(this);

        rv.setAdapter(adapter);

        //设置Item增加、移除动画
        rv.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
        rv.addItemDecoration(new SimpleDecoration(this, SimpleDecoration.VERTICAL_LIST));

    }

    @Override
    protected void initData() {
        tvTitle.setText(R.string.title_insurance_pay);
        insuranceStatus = getIntent().getStringExtra("insuranceStatus");
        tvMobilePhone.setText(MobileUtils.getEncryptMobileNO(loginInfo.getLoginName()));
        presenter.getProductInfo(loginInfo.getAccessToken());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_insurance_pay;
    }

    @OnClick(R.id.ll_back)
    public void onClick() {
        onBackPressed();
    }

    @Override
    public void showInsuranceProductList(List<InsuranceProductInfo> data) {
        if (data != null) {
            mData.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateIndentSuccess(String sign, String outTradeNo) {
        Intent intent = new Intent(this, AliPayActivity.class);
        intent.putExtra("sign", sign);
        intent.putExtra("outTradeNo", outTradeNo);
        intent.putExtra("insuranceStatus", insuranceStatus);
        startActivityForResult(intent, BuildConf.RequestResultCode.PAY_REQUEST);
    }

    @Override
    public void onCreateIndentFailure() {

    }

    @Override
    public void onLeftClick(int position, InsuranceProductInfo data) {

    }

    @Override
    public void onRightClick(int position, InsuranceProductInfo data) {
        presenter.createIndent(loginInfo.getAccessToken(), data.getId(), loginInfo.getSerialNumber());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BuildConf.RequestResultCode.PAY_REQUEST == requestCode) {
            if (InsuranceStatusEnum.未购买.ordinal() != resultCode) {
                setResult(resultCode);
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
