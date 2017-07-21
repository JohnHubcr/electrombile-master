package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;

/**
 * 作    者：wangr on 2017/2/22 15:01
 * 描    述：
 * 修订记录：
 */

public interface FeedbackView extends BaseView {

    /**
     * 保存内容
     */
    void saveFeedbackInfo();

    /**
     * 上传结果
     *
     * @param data
     */
    void onUploadResult(Boolean data);
}
