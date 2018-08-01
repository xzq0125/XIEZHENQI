package com.xiezhenqi.newmvp;

import java.util.List;

/**
 * IAdapter
 * Created by xzq on 2018/7/13.
 */

public interface IAdapter<T> {

    void setData(List<T> data);

    void setData(List<T> data, boolean hasNext);

    void addData(List<T> data, boolean hasNext);
}
