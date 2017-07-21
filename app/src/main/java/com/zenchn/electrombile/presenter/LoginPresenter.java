package com.zenchn.electrombile.presenter;

import android.support.annotation.NonNull;

import com.zenchn.electrombile.base.BasePresenter;
import com.zenchn.electrombile.entity.UserInfo;
import com.zenchn.mlibrary.base.UiHandler;

/**
 * 作    者：wangr on 2017/2/22 14:14
 * 描    述：
 * 修订记录：
 */
public interface LoginPresenter extends BasePresenter {

    /**
     * 登录
     *
     * @param userInfo
     */
    void login(@NonNull UserInfo userInfo);

    /**
     * 退出程序
     */
    void exit(UiHandler handler);

}
