package com.xiezhenqi.base.mvp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * AbstractModel
 * Created by Wesley on 2018/6/4.
 */
public abstract class AbstractModel<Presenter extends LoadingView> implements BaseModel {

    protected final Presenter presenter;
    private List<Call> callList = new ArrayList<>();

    public AbstractModel(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void cancelRequest() {
        for (int i = 0; i < callList.size(); i++) {
            callList.get(i).cancel();
        }
    }

    protected void addRequest(Call call) {
        callList.add(call);
    }

}
