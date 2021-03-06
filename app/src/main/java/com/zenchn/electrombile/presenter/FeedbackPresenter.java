package com.zenchn.electrombile.presenter;

import com.zenchn.electrombile.base.BasePresenter;
import com.zenchn.electrombile.entity.FeedbackInfo;

/**
 * 作    者：wangr on 2017/2/22 15:04
 * 描    述：
 * 修订记录：
 */
public interface FeedbackPresenter extends BasePresenter{

    /**
     * 上传反馈信息
     *
     * @param accessToken
     * @param feedbackInfo
     */
    void uploadFeedback(String accessToken, FeedbackInfo feedbackInfo);
}
