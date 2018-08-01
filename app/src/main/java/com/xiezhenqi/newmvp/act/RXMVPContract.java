package com.xiezhenqi.newmvp.act;

import com.xiezhenqi.newmvp.HomePageBean;
import com.xiezhenqi.newmvp.mvp.IListView;

/**
 * RXMVPContract
 * Created by Wesley on 2018/7/10.
 */
public interface RXMVPContract {

    interface View extends IListView<HomePageBean.Datas> {
    }

    interface Presenter {
        void getData(int page);
    }

}
