package com.xiezhenqi.newmvp.mvp;

/**
 * 实体接口
 * 网络响应以实体形式返回
 *
 * @author xzq
 */
public interface IEntityView<Entity> extends ILoadingEntityView {

    /**
     * 设置数据
     *
     * @param entity 实体
     */
    void setData(Entity entity);
}
