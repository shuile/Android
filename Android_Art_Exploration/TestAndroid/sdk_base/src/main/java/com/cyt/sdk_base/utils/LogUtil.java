package com.cyt.sdk_base.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author chenyiting
 */
public class LogUtil {

    private static final String TAG = "LogUtil";

    private static boolean isReslease = false;

    /**
     * set apk  in debug mode
     * @param release
     */
    public static void setDebug(boolean release) {
        isReslease = release;
    }

    public static void v(String content) {
        v(TAG, content);
    }

    public static void v(String tag, String content) {
        if (isReslease) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            v(TAG, content);
            return;
        }
        Log.v(tag, content);
    }

    public static void d(String content) {
        d(TAG, content);
    }

    public static void d(String tag, String content) {
        if (isReslease) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            d(TAG, content);
            return;
        }
        Log.d(tag, content);
    }

    public static void i(String content) {
        i(TAG, content);
    }

    public static void i(String tag, String content) {
        if (isReslease) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            i(TAG, content);
            return;
        }
        Log.i(tag, content);
    }

    public static void w(String content) {
        w(TAG, content);
    }

    public static void w(String tag, String content) {
        if (isReslease) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            w(TAG, content);
            return;
        }
        Log.w(tag, content);
    }

    public static void e(String content) {
        e(TAG, content);
    }

    public static void e(String tag, String content) {
        if (isReslease) {
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            e(TAG, content);
            return;
        }
        Log.e(tag, content);
    }
}
