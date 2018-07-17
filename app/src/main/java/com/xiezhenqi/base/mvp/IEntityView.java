package com.xiezhenqi.base.mvp;

/**
 * 网络响应以实体形式返回
 * 实体接口
 * Created by Wesley on 2018/6/4.
 */
public interface IEntityView<Entity> extends ILoadingEntityView {

    /**
     * 设置数据
     *
     * @param entity 实体
     */
    void setData(Entity entity);
}
