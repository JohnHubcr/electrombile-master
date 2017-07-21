package com.zenchn.electrombile.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.app.Constants;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.utils.imageUtils.ImageLoaderHelper;
import com.zenchn.electrombile.utils.imageUtils.ImageUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 15:49
 * 描    述：查看大图的页面
 * 修订记录：
 */

public class ImageShowerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_bitmap)
    ImageView ivBitmap;

    private Bitmap bitmap;
    private int type;
    private String imageUrl;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void initData() {
        tvTitle.setText(getString(R.string.title_image_shower));
        type = getIntent().getIntExtra("type", BuildConf.ImageType.IMG_LOCAL);
        imageUrl = getIntent().getStringExtra("imageUrl");
        if (type == BuildConf.ImageType.IMG_LOCAL) {
            bitmap = ImageUtil.revitionImageSize(imageUrl);
            ivBitmap.setImageBitmap(bitmap);
        } else if (type == BuildConf.ImageType.IMG_NETWORK) {
            ImageLoaderHelper.displayImage(imageUrl, ivBitmap);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_shower;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle(); // 回收图片所占的内存
        }
        System.gc(); // 提醒系统及时回收
        super.onDestroy();
    }

    @OnClick(R.id.ll_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
        }
    }
}
