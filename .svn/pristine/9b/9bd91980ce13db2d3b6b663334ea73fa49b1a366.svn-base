package com.zenchn.electrombile.presenter.impl;

import com.zenchn.electrombile.api.FeedbackApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.entity.FeedbackInfo;
import com.zenchn.electrombile.presenter.FeedbackPresenter;
import com.zenchn.electrombile.ui.view.FeedbackView;

/**
 * 作    者：wangr on 2017/2/22 15:06
 * 描    述：
 * 修订记录：
 */
public class FeedbackPresenterImpl implements FeedbackPresenter, ApiCallback<Boolean> {

    private FeedbackView feedbackView;

    public FeedbackPresenterImpl(FeedbackView feedbackView) {
        this.feedbackView = feedbackView;
    }

    @Override
    public void uploadFeedback(String accessToken, FeedbackInfo feedbackInfo) {
        if (feedbackView != null) {
            feedbackView.showProgress();
            FeedbackApi
                    .getInstance()
                    .accountFeedBack(accessToken, feedbackInfo, this);
        }
    }

    @Override
    public void onDestroy() {
        feedbackView = null;
    }

    @Override
    public void onSuccess(Boolean data) {
        if (feedbackView != null) {
            feedbackView.hideProgress();
            feedbackView.onUploadResult(data);
        }
    }

    @Override
    public void onResponseError(String err_msg) {
        if (feedbackView != null) {
            feedbackView.hideProgress();
            feedbackView.onResponseError(err_msg);
        }
    }

    @Override
    public void onFailure() {
        if (feedbackView != null) {
            feedbackView.hideProgress();
            feedbackView.onFailure();
        }
    }

    @Override
    public void onGrantRefuse() {
        if (feedbackView != null) {
            feedbackView.hideProgress();
            feedbackView.onGrantRefuse();
        }
    }
}
