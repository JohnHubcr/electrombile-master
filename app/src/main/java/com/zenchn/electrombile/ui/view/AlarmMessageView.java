package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.entity.AlarmMessageInfo;

import java.util.List;

/**
 * 作    者：wangr on 2017/3/2 17:37
 * 描    述：
 * 修订记录：
 */

public interface AlarmMessageView extends BaseView {

    /**
     * 显示消息
     *
     * @param messageList
     * @param pageNumber
     * @param totalPages
     */
    void showMessageList(List<AlarmMessageInfo> messageList, int pageNumber, int totalPages);


    /**
     * 加载数据出错
     */
    void onLoadError();

    /**
     * 刷新完成
     */
    void onRefreshCompleted();

}
