package com.zenchn.electrombile.ui.view;

import com.zenchn.electrombile.base.BaseView;
import com.zenchn.electrombile.adapter.bean.ContactInfo;

/**
 * 作    者：wangr on 2017/2/27 16:16
 * 描    述：
 * 修订记录：
 */

public interface ChooseContactView extends BaseView {

    /**
     * 记录查询到的联系人
     *
     * @param contactInfo
     */
    void addContacts(ContactInfo contactInfo);

    /**
     * 查询完毕
     */
    void onQueryCompleted();
}
