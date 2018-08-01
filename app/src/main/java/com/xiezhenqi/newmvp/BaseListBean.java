package com.xiezhenqi.newmvp;

import com.google.gson.annotations.SerializedName;

/**
 * 列表基类
 *
 * @author xzq
 */

public abstract class BaseListBean {

    @SerializedName(value = "snPageCount", alternate = {"count", "listCount", "pageCount"})
    private int snPageCount;

    /**
     * 是否还有下一页
     *
     * @param page 当前页码
     * @return 是否还有下一页
     */
    public boolean hasNextPage(final int page) {
        return page < snPageCount;
    }
}
