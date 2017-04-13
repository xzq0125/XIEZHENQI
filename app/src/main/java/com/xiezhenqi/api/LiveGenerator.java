package com.xiezhenqi.api;

/**
 * LiveGenerator
 * Created by sean on 2017/4/13.
 */

public class LiveGenerator extends BaseGenerator<LiveService> {

    public LiveGenerator() {
        this(false);
    }

    public LiveGenerator(boolean secure) {
        super("coapi.douyucdn.cn", secure);
    }

    @Override
    protected Class<LiveService> getServiceType() {
        return LiveService.class;
    }
}
