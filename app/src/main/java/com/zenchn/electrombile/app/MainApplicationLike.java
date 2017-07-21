package com.zenchn.electrombile.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.mlibrary.utils.FileUtils;

/**
 * 作者：wangr on 2017/1/9 9:34
 * 描述：这个类是Application的代理类，以前所有在Application的实现必须要全部拷贝到这里
 */

public class MainApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "MyTinker";

    public MainApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent, Resources[] resources, ClassLoader[] classLoader, AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 这里进行Bugly初始化
//        // 设置开发设备
//        Bugly.setIsDevelopmentDevice(getApplication(), true);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId

        Log.d(TAG, "MainApplicationLike onCreate start");
        Bugly.init(getApplication(), "61c0726228", false);

        initFolder();//检测缓存文件夹
        initBaiduMap();//初始化百度地图
        initApplicationKit();//初始化application管理类
        initImageLoader();//初始化ImageLoader

        Log.d(TAG, "MainApplicationLike onCreate end");
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // TODO: 安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    /**
     * 创建文件夹
     */
    private void initFolder() {
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            if (!FileUtils.isFolderExist(Constants.appFolder))
                FileUtils.createFolder(Constants.appFolder);
        }
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
        SDKInitializer.initialize(getApplication());
    }

    /**
     * 设置默认APP管理类
     */
    private void initApplicationKit() {
        ApplicationKit.getInstance().initKit(getApplication());
    }

    /**
     * 初始化Imageloader
     */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplication())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
