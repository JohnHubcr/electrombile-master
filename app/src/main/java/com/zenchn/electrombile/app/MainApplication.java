package com.zenchn.electrombile.app;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 作者：wangr on 2017/1/9 9:35
 * 描述：隔离Application，所有操作转移至代理类MainApplicationLike进行
 */
public class MainApplication extends TinkerApplication {

    public MainApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.zenchn.electrombile.app.MainApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

}
