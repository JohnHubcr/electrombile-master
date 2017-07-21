package com.zenchn.electrombile.entity;

import java.io.File;
import java.io.Serializable;

/**
 * 作    者：wangr on 2017/3/13 14:09
 * 描    述：反馈信息描述类
 * 修订记录：
 */
public class FeedbackInfo implements Serializable {

    private String feedbackContent;// 反馈内容
    private String contactNumber;// 联系电话
    private File feedbackImageFile;// 用户图片（图片文件）

    public FeedbackInfo(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public FeedbackInfo(String feedbackContent, String contactNumber, File feedbackImageFile) {
        this.feedbackContent = feedbackContent;
        this.contactNumber = contactNumber;
        this.feedbackImageFile = feedbackImageFile;
    }

    public FeedbackInfo(String feedbackContent, String contactNumber, String feedbackImagePath) {
        this.feedbackContent = feedbackContent;
        this.contactNumber = contactNumber;
        this.feedbackImageFile = new File(feedbackImagePath);
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public File getFeedbackImageFile() {
        return feedbackImageFile;
    }

    public void setFeedbackImageFile(File feedbackImageFile) {
        this.feedbackImageFile = feedbackImageFile;
    }

    @Override
    public String toString() {
        return "FeedbackInfo{" +
                "feedbackContent='" + feedbackContent + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", feedbackImagePath=" + (feedbackImageFile != null ? feedbackImageFile.getAbsolutePath() : "null") +
                '}';
    }
}
