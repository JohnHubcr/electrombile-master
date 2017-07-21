package com.zenchn.electrombile.utils.imageUtils;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zenchn.electrombile.R;

/**
 * 图片显示类
 *
 * @author wangr
 */
public class ImageLoaderHelper {

    private static final ImageLoader imageLoader = ImageLoader.getInstance();

    private static final DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.logo_about) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.logo_about) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.logo_about) // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
            .build(); // 创建配置过得DisplayImageOption对象

    public static void displayImage(String uri, ImageView imageView) {
        imageLoader.displayImage(uri, imageView, options);
    }

}
