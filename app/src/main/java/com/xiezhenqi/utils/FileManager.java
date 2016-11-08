package com.xiezhenqi.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.xiezhenqi.config.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * 文件管理器 管理日志、缓存、图片、更新包
 * <p/>
 * 使用getExternalFilesDir()得到目录，保证应用卸载后不残留文件
 *
 * @author Alex
 */
public class FileManager {

    public static final int KB = 1024;
    public static final int MB = 1048576;

    /**
     * 写入日志文件
     * <p/>
     * 日志小于1M则追加，大于1M则清除重新写入
     *
     * @param context Context
     * @param message 消息内容
     */
    public static void writeLogFile(Context context, String message) {
        File logDir = context.getExternalFilesDir(Config.DIRECTORY_LOG);
        if (logDir != null && logDir.exists()) {
            String logFilePath = logDir.getPath() + File.separator
                    + Config.LOG_NAME;
            File logFile = new File(logFilePath);
            writeString(logFile, logFile.length() < MB, message);
        }
    }

    /**
     * 从文件读取String
     *
     * @param file 需要读取的文件
     * @return 读取的字符串
     */
    public static String readString(File file) {
        FileReader cacheReader = null;
        BufferedReader bufReader = null;
        try {
            cacheReader = new FileReader(file);
            bufReader = new BufferedReader(cacheReader);
            char[] buffer = new char[1024];
            StringBuilder stringBuffer = new StringBuilder();
            while (bufReader.read(buffer) != -1) {
                stringBuffer.append(buffer);
            }
            return stringBuffer.toString().toUpperCase(Locale.getDefault());
        } catch (IOException e) {
            return null;
        } finally {
            try {
                if (bufReader != null)
                    bufReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (cacheReader != null)
                    cacheReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * String 写入文件
     *
     * @param file   待写入文件
     * @param append 是否追加
     * @param data   待写入内容
     * @return 是否写入成功
     */
    public static boolean writeString(File file, boolean append, String data) {
        FileWriter cacheWriter = null;
        BufferedWriter bufWriter = null;
        try {
            cacheWriter = new FileWriter(file, append);
            bufWriter = new BufferedWriter(cacheWriter);
            bufWriter.write(data);
            bufWriter.newLine();
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (bufWriter != null)
                    bufWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (cacheWriter != null)
                    cacheWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建照片
     *
     * @param context Context
     * @return 照片文件
     */
    public static File createPhoto(Context context) {
        File photo;
        File DCIMDric = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DCIMDric == null || !DCIMDric.exists()) {
            photo = new File(context.getFilesDir(), Config.PHOTO_PREFIX
                    + DateUtils.getCalendarStr(DateUtils.PATTERN_YYYY_MM_DD_HHMMSS_0) + Config.PHOTO_EXTENSION);
        } else {
            File photoPath = new File(DCIMDric, Config.DIRECTORY_PHOTOS);
            if (!photoPath.exists() && !photoPath.mkdirs()) {
                // 目录不存在且创建失败
                return null;
            }
            photo = new File(photoPath, Config.PHOTO_PREFIX
                    + DateUtils.getCalendarStr(DateUtils.PATTERN_YYYY_MM_DD_HHMMSS_1) + Config.PHOTO_EXTENSION);
        }
        if (photo.exists() && !photo.delete()) {
            // 图片已存在且无法删除
            return null;
        }
        return photo;
    }

    /**
     * 图片缓存目录
     *
     * @param context Context
     * @return 更新文件
     */
    public static File getImageCache(Context context) {
        return context.getExternalFilesDir(Config.DIRECTORY_IMAGE);
    }

    /**
     * 照片裁剪目录
     *
     * @param context Context
     * @return 照片裁剪目录
     */
    public static File getCropCache(Context context) {
        return context.getExternalFilesDir(Config.DIRECTORY_CROP);
    }

    /**
     * 获取头像裁剪文件
     *
     * @param context Context
     * @return 头像裁剪文件
     */
    public static File getCropHeadFile(Context context) {
        return new File(getCropCache(context), Config.FILENAME_CROP_PIC);
    }

    /**
     * 判断是否为图片
     *
     * @param filePath 文件路径
     * @return 是否为图片
     */
    public static boolean isBitmapFile(String filePath) {
        return isBitmapFile(filePath, null);
    }

    /**
     * 判断是否为图片
     *
     * @param filePath 文件路径
     * @return 是否为图片
     */
    public static boolean isBitmapFile(String filePath, int[] size) {
        int width;
        int height;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            width = options.outWidth;
            height = options.outHeight;
        } catch (OutOfMemoryError e) {
            return false;
        }
        if (size != null && size.length >= 2) {
            size[0] = width;
            size[1] = height;
        }
        return width > 0 && height > 0;
    }
}
