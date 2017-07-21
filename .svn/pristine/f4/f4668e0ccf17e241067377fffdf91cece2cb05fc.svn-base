package com.zenchn.electrombile.base;

import com.zenchn.mlibrary.base.DefaultService;
import com.zenchn.mlibrary.cache.ACache;
import com.zenchn.mlibrary.event.BusFactory;

/**
 * 作    者：wangr on 2017/2/20 23:15
 * 描    述：
 * 修订记录：
 */
public abstract class BaseService extends DefaultService {

    protected ACache aCache;

    @Override
    public void onCreate() {
        super.onCreate();
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
        if (useACache()) {
            aCache = ACache.get(this);
        }
    }

    protected abstract boolean useEventBus();

    protected boolean useACache() {
        return true;
    }

}
