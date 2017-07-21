package com.zenchn.electrombile.router;

import android.app.Activity;

/**
 * 作    者：wangr on 2017/2/13 15:17
 * 描    述：
 * 修订记录：
 */
public interface RouterCallback {

    void onBefore(Activity from, Class<?> to);

    void OnNext(Activity from, Class<?> to);

    void onError(Activity from, Class<?> to, Throwable throwable);

}
