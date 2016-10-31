package com.xiezhenqi.request;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.xiezhenqi.entity.ResponseDto;
import com.xiezhenqi.utils.EntitySerializer;
import com.xiezhenqi.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 请求
 *
 * @author Alex
 */
public class RequestHelper {

    private static final String TAG = "RequestHelper";
    private RequestUrlBuilder urlMaker;
    private TypeToken typeToken;
    private Object tag;
    private boolean isPost = false;
    private final Map<String, String> postData = new HashMap<>();
    private RequestReady requestReady;

    RequestHelper(@NonNull RequestUrlBuilder urlMaker, @NonNull TypeToken typeToken) {
        this.urlMaker = urlMaker;
        this.typeToken = typeToken;
    }

    URL getUrl() throws RequestException {
        return urlMaker.makeUrl();
    }

    void onReady() {
        if (requestReady != null) {
            requestReady.onReady(this);
        }
    }

    RequestBody getRequestBody() throws UnsupportedEncodingException {
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
                getOutputBuffer());
    }

    void onPostRequestBuild(Request.Builder builder) {
        if (requestReady != null)
            requestReady.onPostRequestBuild(builder);
    }

    /**
     * 获取需上传的交互数据
     * <p/>
     * 包内可见
     *
     * @return 交互数据
     * @throws UnsupportedEncodingException
     */
    byte[] getOutputBuffer() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (String key : postData.keySet()) {
            sb.append(key);
            sb.append("=");
            if (postData.get(key) != null)
                sb.append(URLEncoder.encode(postData.get(key), "utf-8"));
            sb.append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString().getBytes("utf-8");
    }

    /**
     * 数据解析
     *
     * @param inputStream 输入流
     * @return 解析结果
     * @throws RequestException
     */
    Object doParse(InputStream inputStream)
            throws RequestException {
        String json = "";
        InputStreamReader isReader = null;
        BufferedReader reader = null;
        try {
            isReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(isReader);
            String inputLine;
            while (((inputLine = reader.readLine()) != null)) {
                json += inputLine;
            }
        } catch (IOException e) {
            throw new RequestException(RequestError.Error.CODE_0103);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (isReader != null)
                    isReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LogUtils.debug(TAG, json);

        ResponseDto result;
        try {
            result = EntitySerializer.deserializerType(json, typeToken.getType());
        } catch (Exception e) {
            result = null;
        }
        if (result == null) {
            LogUtils.e(TAG, json);
            throw new RequestException(RequestError.Error.CODE_0104);
        }
        if (result.isSuccessful()) {
            return result.getObject();
        } else {
            throw new RequestException(result.showapi_res_code, result.showapi_res_error);
        }
    }

    public Object getTag() {
        return tag;
    }

    public RequestHelper setTag(Object tag) {
        this.tag = tag;
        return this;
    }

    public boolean isPost() {
        return isPost;
    }

    public void setToPost(boolean isPost) {
        this.isPost = isPost;
    }


    /**
     * 设置Post参数
     *
     * @param key   关键字
     * @param value 值
     */
    void addPostData(String key, long value) {
        addPostData(key, Long.toString(value));
    }

    /**
     * 设置Post参数
     *
     * @param key   关键字
     * @param value 值
     */
    void addPostData(String key, String value) {
        postData.put(key, value);
    }

    /**
     * 设置请求准备
     *
     * @param requestReady 请求准备
     */
    void setRequestReady(RequestReady requestReady) {
        this.requestReady = requestReady;
    }

    RequestUrlBuilder getUrlMaker() {
        return urlMaker;
    }

    /**
     * 请求准备
     */
    interface RequestReady {
        void onReady(RequestHelper requestHelper);

        void onPostRequestBuild(Request.Builder builder);
    }
}
