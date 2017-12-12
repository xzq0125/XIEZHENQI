package com.xiezhenqi.utils.encryption;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * MD5
 * Created by Tse on 2016/8/6.
 */
public class MD5 {

    /**
     * MD5加码
     *
     * @param str       待加码数据
     * @param lowerCase 是否小写
     * @return MD5值
     */
    public static String encode(String str, boolean lowerCase) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("utf-8"));
            StringBuilder builder = new StringBuilder();
            for (byte b : md5.digest()) {
                builder.append(Integer.toHexString((b >> 4) & 0xf));
                builder.append(Integer.toHexString(b & 0xf));
            }
            if (lowerCase)
                return builder.toString();
            else
                return builder.toString().toUpperCase(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
