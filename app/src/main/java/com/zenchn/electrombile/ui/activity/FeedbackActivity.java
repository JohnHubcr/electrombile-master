package com.zenchn.electrombile.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.Constants;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.base.BaseActivity;
import com.zenchn.electrombile.entity.FeedbackInfo;
import com.zenchn.electrombile.presenter.FeedbackPresenter;
import com.zenchn.electrombile.presenter.impl.FeedbackPresenterImpl;
import com.zenchn.electrombile.ui.view.FeedbackView;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.utils.imageUtils.ImageFileSelector;
import com.zenchn.electrombile.utils.imageUtils.ImageUtil;
import com.zenchn.electrombile.widget.ActionSheet;
import com.zenchn.electrombile.wrapper.ACacheWrapper;
import com.zenchn.mlibrary.utils.DateUtils;
import com.zenchn.mlibrary.utils.FileUtils;
import com.zenchn.mlibrary.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作    者：wangr on 2017/2/22 14:59
 * 描    述：用户反馈的页面
 * 修订记录：
 */
public class FeedbackActivity extends BaseActivity implements FeedbackView, OnClickListener, TextWatcher, ImageFileSelector.ImageFilesCallback, ActionSheet.MenuItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_feedback)
    EditText etFeedback;
    @BindView(R.id.tv_residual)
    TextView tvResidual;
    @BindView(R.id.tv_auto_save)
    TextView tvAutoSave;
    @BindView(R.id.iv_upload_pic)
    ImageView ivUploadPic;
    @BindView(R.id.et_number)
    EditText etNumber;

    private FeedbackPresenter presenter;
    private ImageFileSelector imageFileSelector;
    private boolean isCommit = false;
    private ActionSheet menuView;

    private String oldContent;
    private Bitmap thumbnail;
    private File feedbackImageFile;


    @Override
    protected void onDestroy() {
        CommonUtils.recycleBitmap(thumbnail);
        super.onDestroy();
        presenter.onDestroy();
    }

    public FeedbackActivity() {
        this.presenter = new FeedbackPresenterImpl(this);
        this.imageFileSelector = new ImageFileSelector(this);
    }

    @Override
    public boolean useUiHandler() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_feedback;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        addListener();
        initData();
        initTempFolder();
        initImageConfig();
    }

    protected void addListener() {
        etFeedback.addTextChangedListener(this);
    }

    protected void initData() {
        tvTitle.setText(getString(R.string.title_feed_back));
        renewFeedbackInfo();//恢复已经填写的内容
    }

    private void renewFeedbackInfo() {
        FeedbackInfo feedbackInfo = ACacheWrapper.getFeedbackInfo();

        if (feedbackInfo != null) {

            if (CommonUtils.isNonNull(feedbackInfo.getFeedbackContent())) {
                String feedbackContent = feedbackInfo.getFeedbackContent();
                etFeedback.setText(feedbackContent);
                etFeedback.setSelection(feedbackContent.length());
                tvAutoSave.setText(getString(R.string.feedback_recover_save));
            }

            if (CommonUtils.isNonNull(feedbackInfo.getContactNumber())) {
                String contactNumber = feedbackInfo.getContactNumber();
                etNumber.setText(contactNumber);
                etNumber.setSelection(contactNumber.length());
            }

            File feedbackImageFile = feedbackInfo.getFeedbackImageFile();
            if (FileUtils.isFileExist(feedbackImageFile)) {
                this.feedbackImageFile = feedbackImageFile;
                thumbnail = ImageUtil.revitionImageSize(feedbackImageFile.getAbsolutePath());
                setThumbnail(thumbnail);
            }
        }
    }

    /**
     * 检查缓存图片的文件夹
     */
    private void initTempFolder() {
        if (!FileUtils.isFolderExist(BuildConf.appFolder)) {// 判断文件目录是否存在
            FileUtils.createFolder(BuildConf.appFolder);
        }
    }

    /**
     * 设置图片工具类的配置
     */
    private void initImageConfig() {
        imageFileSelector.setQuality(80);
        imageFileSelector.setCallback(this);
        imageFileSelector.setDelete(false);
    }

    @OnClick({R.id.ll_back, R.id.tv_save, R.id.ll_upload_pic, R.id.ll_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;

            case R.id.tv_save:
                saveFeedbackInfo();
                break;

            case R.id.ll_upload_pic:
                showActionSheet();
                break;

            case R.id.ll_commit:
                uploadFeedback();
                break;
        }
    }

    /**
     * 显示菜单
     */
    public void showActionSheet() {
        setTheme(R.style.ActionSheetStyle);
        menuView = new ActionSheet(this);
        menuView.setCancelButtonTitle("取消");// before add items
        menuView.addItems("查看大图", "我的相册", "拍照", "删除");
        menuView.setItemClickListener(this);
        menuView.setCancelableOnTouchMenuOutside(true);
        menuView.showMenu();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = etFeedback.getText().toString();
        int i = BuildConf.ACTIVITY.FEEDBACK_CONTENT_MAX_LENGTH - content.length();
        i = i < 0 ? 0 : i;
        tvResidual.setText(String.format(getString(R.string.feedback_length_limit_format), String.valueOf(i)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageFileSelector.onActivityResult(requestCode, resultCode, BuildConf.ACTIVITY.FEEDBACK_KEY_FEEDBACK_PIC_FILE, data);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onBackPressed() {
        if (isCommit) {
            super.onBackPressed();
        } else {
            showProgress();
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    saveFeedbackInfo();
                    hideProgress();
                    FeedbackActivity.super.onBackPressed();
                }
            }, 200);
        }
    }

    public void uploadFeedback() {
        FeedbackInfo feedbackInfo = getFeedbackInfo();
        if (checkFeedbackInfo(feedbackInfo)) {
            presenter.uploadFeedback(loginInfo.getAccessToken(), feedbackInfo);
        }
    }

    private boolean checkFeedbackInfo(FeedbackInfo feedbackInfo) {
        String feedbackContent = feedbackInfo.getFeedbackContent();
        if (CommonUtils.isEmpty(feedbackContent)) {
            showResMessage(R.string.feedback_no_content);
            return false;
        }
//        else if (!FileUtils.isFileExist(feedbackImageFile)) {
//            showResMessage(R.string.no_feedback_pic);
//            return false;
//        }
        return true;
    }

    @Override
    public void saveFeedbackInfo() {
        ACacheWrapper.saveFeedbackInfo(getFeedbackInfo());
        tvAutoSave.setText(String.format(getString(R.string.feedback_save_time_format), DateUtils.getCurrentTime(Constants.YEAR_MONTH_DAY_HOUR_MIN)));
    }

    @Override
    public void onUploadResult(Boolean result) {
        if (CommonUtils.isTrue(result)) {
            ToastUtils.showDefaultMessage(getApplicationContext(), getString(R.string.feedback_success));
            ACacheWrapper.removeFeedbackInfo();
            finish();
        } else {
            ToastUtils.showDefaultMessage(getApplicationContext(), getString(R.string.feedback_failure));
        }
    }

    @NonNull
    private FeedbackInfo getFeedbackInfo() {
        FeedbackInfo feedbackInfo = new FeedbackInfo(etFeedback.getText().toString());
        feedbackInfo.setFeedbackImageFile(feedbackImageFile);
        feedbackInfo.setContactNumber(etNumber.getText().toString());
        return feedbackInfo;
    }

    @Override
    public void onSelectSuccess(String file, String tag) {
    }

    @Override
    public void onCompressSuccess(String filePath, String tag) {
        this.feedbackImageFile = new File(filePath);
        thumbnail = ImageUtil.revitionImageSize(filePath);
        setThumbnail(thumbnail);
    }

    /**
     * 设置缩略图
     *
     * @param photo
     */
    private void setThumbnail(Bitmap photo) {
        ivUploadPic.setImageBitmap(photo);
    }

    @Override
    public void onImageError() {
        showResMessage(R.string.load_picture_error);
    }

    @Override
    public void onImageError(String tag) {
        showResMessage(R.string.load_picture_error);
    }

    /**
     * 查看大图
     */
    private void viewImg() {
        if (FileUtils.isFileExist(feedbackImageFile)) {
            Intent intent = new Intent(this, ImageShowerActivity.class);
            intent.putExtra("type", BuildConf.ImageType.IMG_LOCAL);
            intent.putExtra("imageUrl", feedbackImageFile.getAbsolutePath());
            startActivity(intent);
        }
    }

    /**
     * 判断目录状态
     *
     * @return
     */
    private boolean checkSDCard() {
        Boolean checkDir = imageFileSelector.checkDir();
        if (checkDir == null) {
            showResMessage(R.string.sd_card_error);
            checkDir = false;
        } else if (!checkDir) {
            showResMessage(R.string.read_data_error);
        }
        return checkDir;
    }

    @Override
    public void onItemClick(int itemPosition) {
        switch (itemPosition) {
            case 0:// 查看大图
                viewImg();
                break;
            case 1:// 我的相册
                if (checkSDCard()) {
                    imageFileSelector.selectImage(this);
                }
                break;
            case 2:// 拍照
                if (checkSDCard()) {
                    imageFileSelector.takePhoto(this);
                }
                break;
            case 3:// 删除
                feedbackImageFile = null;
                ivUploadPic.setImageResource(R.drawable.add_photo);
                break;
        }
        menuView.dismissMenu();
    }

}
