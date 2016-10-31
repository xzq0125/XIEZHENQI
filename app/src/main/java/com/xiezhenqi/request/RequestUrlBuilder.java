package com.xiezhenqi.request;

import android.support.annotation.NonNull;

import com.xiezhenqi.config.Config;
import com.xiezhenqi.utils.LogUtils;
import com.xiezhenqi.utils.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Url 拼接器
 * Created by Tse on 2016/10/20.
 */
public class RequestUrlBuilder {

    private final String TAG = getClass().getSimpleName();
    private static final String UC_AKEY = "showapi_appid";// AppKey
    private static final String UC_SIGN = "showapi_sign";// 请求签名

    private String firstHalf;

    /**
     * URL参数
     */
    private final Map<String, String> urlData = new HashMap<>();

    RequestUrlBuilder(@NonNull String address) {
        firstHalf = "http://route.showapi.com" + address;
    }

    URL makeUrl() throws RequestException {
        if (StringUtils.isNullOrEmpty(firstHalf))
            throw new RequestException(RequestError.Error.CODE_0101);
        try {
            return new URL(getUrlString());
        } catch (MalformedURLException e) {
            throw new RequestException(RequestError.Error.CODE_0101);
        }
    }

    /**
     * 获取连接地址
     *
     * @return 地址字符串
     */
    private String getUrlString() {

        StringBuilder url = new StringBuilder();
        url.append(firstHalf);
        url.append("?");

        url.append("&");
        url.append(UC_AKEY);
        url.append("=");
        url.append(Config.APP_KEY);

        url.append("&");
        url.append(UC_SIGN);
        url.append("=");
        url.append(Config.UC_SIGN);

        for (String key : urlData.keySet()) {
            url.append("&");
            url.append(key);
            url.append("=");
            try {
                url.append(URLEncoder.encode(urlData.get(key), Config.ENCODING_UTF));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LogUtils.debug(TAG, url.toString());

        return url.toString();
    }

    /**
     * 设置URL参数
     *
     * @param key   关键字
     * @param value 值
     */
    void addUrlData(String key, String value) {
        urlData.put(key, value);
    }

    /**
     * 设置URL参数
     *
     * @param key   关键字
     * @param value 值
     */
    void addUrlData(String key, long value) {
        addUrlData(key, Long.toString(value));
    }

}
