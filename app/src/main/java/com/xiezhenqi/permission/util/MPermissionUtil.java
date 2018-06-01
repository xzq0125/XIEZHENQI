package com.xiezhenqi.permission.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import com.xiezhenqi.permission.annotation.OnMPermissionDenied;
import com.xiezhenqi.permission.annotation.OnMPermissionGranted;
import com.xiezhenqi.permission.annotation.OnMPermissionNeverAskAgain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


final public class MPermissionUtil {

    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findNeverAskAgainPermissions(Activity activity, String... permission) {
        List<String> neverAskAgainPermission = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED &&
                    !activity.shouldShowRequestPermissionRationale(value)) {
                neverAskAgainPermission.add(value);
            }
        }
        return neverAskAgainPermission;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissionWithoutNeverAskAgain(Activity activity, String... permission) {
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED &&
                    activity.shouldShowRequestPermissionRationale(value)) {
                denyPermissions.add(value);
            }
        }

        return denyPermissions;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public static boolean hasNeverAskAgainPermission(Activity activity, List<String> permission) {
        //TODO:统一调用授权失败方法，采用对话框形式提示用户授权
//        for (String value : permission) {
//            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED &&
//                    !activity.shouldShowRequestPermissionRationale(value)) {
//                return true;
//            }
//        }

        return false;
    }

    public static <A extends Annotation> Method findMethodWithRequestCode(Class clazz, Class<A> annotation, int requestCode) {
        String clsName = clazz.getName();
        if (clsName.startsWith("android.") || clsName.startsWith("java.")) {
            return null;
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(annotation) != null &&
                    isEqualRequestCodeFromAnnotation(method, annotation, requestCode)) {
                return method;
            }
        }
        return findMethodWithRequestCode(clazz.getSuperclass(), annotation, requestCode);
    }

    private static boolean isEqualRequestCodeFromAnnotation(Method m, Class clazz, int requestCode) {
        if (clazz.equals(OnMPermissionDenied.class)) {
            return requestCode == m.getAnnotation(OnMPermissionDenied.class).requestCode();
        } else if (clazz.equals(OnMPermissionGranted.class)) {
            return requestCode == m.getAnnotation(OnMPermissionGranted.class).requestCode();
        } else {
            return clazz.equals(OnMPermissionNeverAskAgain.class)
                    && requestCode == m.getAnnotation(OnMPermissionNeverAskAgain.class).requestCode();
        }
    }

    public static String toString(List<String> permission) {
        if (permission == null || permission.isEmpty()) {
            return "";
        }
        return toString(permission.toArray(new String[permission.size()]));
    }

    public static String toString(String[] permission) {
        if (permission == null || permission.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String p : permission) {
            sb.append(p.replaceFirst("android.permission.", ""));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
