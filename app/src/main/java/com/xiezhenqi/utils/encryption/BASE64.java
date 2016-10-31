package com.xiezhenqi.utils.encryption;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * BASE64
 * Created by Tse on 2016/8/6.
 */
public class BASE64 {

    /**
     * BASE64 加密
     *
     * @param str         待加密字符串
     * @param encodingIn  解码编码格式
     * @param encodingOut 输出编码格式
     * @return 加密字符串
     */
    public static String encodeBASE64(String str, String encodingIn,
                                      String encodingOut) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String strBase64 = null;
        try {
            byte[] encode = str.getBytes(encodingIn);
            strBase64 = new String(Base64.encode(encode, Base64.DEFAULT),
                    encodingOut);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strBase64;
    }

    /**
     * BASE64 解码
     *
     * @param str         待编码字符串
     * @param encodingIn  解码编码格式
     * @param encodingOut 输出编码格式
     * @return 编码字符串
     */
    public static String decodeBASE64(String str, String encodingIn,
                                      String encodingOut) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String strBase64 = null;
        try {
            byte[] encode = str.getBytes(encodingIn);
            strBase64 = new String(Base64.decode(encode, Base64.DEFAULT),
                    encodingOut);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return strBase64;
    }
}
