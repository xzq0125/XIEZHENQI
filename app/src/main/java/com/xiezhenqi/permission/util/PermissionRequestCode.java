package com.xiezhenqi.permission.util;

import java.util.ArrayList;
import java.util.List;

/**
 * PermissionRequestCode
 * Created by Wesley on 2018/6/2.
 */
public class PermissionRequestCode {

    //危险权限请求码
    public static final int CALENDAR = 6660;
    public static final int CAMERA = 6661;
    public static final int CONTACTS = 6662;
    public static final int LOCATION = 6663;
    public static final int MICRO = 6664;
    public static final int PHONE = 6665;
    public static final int SENSORS = 6666;
    public static final int SMS = 6667;
    public static final int STORAGE = 6668;

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
