package com.xiezhenqi.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * GlideUtils
 * Created by sean on 2016/11/9.
 */

public class GlideUtils {

    /**
     * 从网络中异步加载本地图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromWeb(Activity activity, String uri, ImageView imageView) {
        // String imageUri = "http://site.com/image.png"; // from Web
        Glide.with(activity).load(uri).into(imageView);
    }

    /**
     * 从网络中异步加载本地图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromWeb(Fragment fragment, String uri, ImageView imageView) {
        // String imageUri = "http://site.com/image.png"; // from Web
        Glide.with(fragment).load(uri).into(imageView);
    }

    /**
     * 从网络中异步加载本地图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromWeb(Context context, String uri, ImageView imageView) {
        // String imageUri = "http://site.com/image.png"; // from Web
        Glide.with(context).load(uri).into(imageView);
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromSDCard(Activity activity, String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        Glide.with(activity).load("file://" + uri).into(imageView);
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromSDCard(Fragment fragment, String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        Glide.with(fragment).load("file://" + uri).into(imageView);
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromSDCard(Context context, String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        Glide.with(context).load("file://" + uri).into(imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称
     * @param imageView ImageView
     */
    public static void displayFromAssets(Activity activity, String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        Glide.with(activity).load("assets://" + imageName).into(imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称
     * @param imageView ImageView
     */
    public static void displayFromAssets(Fragment fragment, String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        Glide.with(fragment).load("assets://" + imageName).into(imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称
     * @param imageView ImageView
     */
    public static void displayFromAssets(Context context, String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        Glide.with(context).load("assets://" + imageName).into(imageView);
    }

    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId   图片ID
     * @param imageView ImageView
     */
    public static void displayFromDrawable(Activity activity, @DrawableRes int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        Glide.with(activity).load("drawable://" + imageId).into(imageView);
    }

    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId   图片ID
     * @param imageView ImageView
     */
    public static void displayFromDrawable(Fragment fragment, @DrawableRes int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        Glide.with(fragment).load("drawable://" + imageId).into(imageView);
    }

    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId   图片ID
     * @param imageView ImageView
     */
    public static void displayFromDrawable(Context context, @DrawableRes int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        Glide.with(context).load("drawable://" + imageId).into(imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromContentProvider(Activity activity, String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        Glide.with(activity).load("content://" + uri).into(imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromContentProvider(Fragment fragment, String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        Glide.with(fragment).load("content://" + uri).into(imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     *
     * @param uri       路径
     * @param imageView ImageView
     */
    public static void displayFromContentProvider(Context context, String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        Glide.with(context).load("content://" + uri).into(imageView);
    }

}
