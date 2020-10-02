package com.cyt.sdk_base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * @author 122668
 */
public class AppInfo {

    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
