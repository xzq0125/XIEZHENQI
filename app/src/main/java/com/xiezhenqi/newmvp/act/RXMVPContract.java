package com.xiezhenqi.newmvp.act;

import com.xiezhenqi.base.mvp.IListView;
import com.xiezhenqi.newmvp.ShopBean;

/**
 * RXMVPContract
 * Created by Wesley on 2018/7/10.
 */
public interface RXMVPContract {

    interface View extends IListView<ShopBean.ListBean> {
    }

    interface Presenter {
        void getData(int page);
    }

}
