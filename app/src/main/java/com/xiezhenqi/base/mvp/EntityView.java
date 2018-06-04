package com.xiezhenqi.base.mvp;

/**
 * 实体View
 * Created by Wesley on 2018/6/4.
 */
public interface EntityView<Entity> {

    /**
     * 设置数据
     *
     * @param entity 实体
     */
    void setData(Entity entity);
}
