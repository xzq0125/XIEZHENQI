package com.xiezhenqi.permission.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 危险权限请求码
 * Created by Wesley on 2018/6/2.
 */
public class PermissionRequestCode {

    //危险权限请求码
    public static final int CALENDAR = 16660;
    public static final int CAMERA = 16661;
    public static final int CONTACTS = 16662;
    public static final int LOCATION = 16663;
    public static final int MICRO = 16664;
    public static final int PHONE = 16665;
    public static final int SENSORS = 16666;
    public static final int SMS = 16667;
    public static final int STORAGE = 16668;

    private static List<Integer> sCodeList = new ArrayList<>();

    static {
        sCodeList.add(CALENDAR);
        sCodeList.add(CAMERA);
        sCodeList.add(CONTACTS);
        sCodeList.add(LOCATION);
        sCodeList.add(MICRO);
        sCodeList.add(PHONE);
        sCodeList.add(SENSORS);
        sCodeList.add(SMS);
        sCodeList.add(STORAGE);
    }

    public static boolean isCustomCode(final int requestCode) {
        return !sCodeList.contains(requestCode);
    }

}
