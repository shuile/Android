package com.cyt.sdk_base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * @author 122668
 */
public class ActivityUtils {

    /**
     * start Activity
     * @param context current context of Activity
     * @param cls target Activity
     */
    public static void startActivtiy(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }

    /**
     * startActivityForResult
     * @param activity current Activity
     * @param cls target Activity
     * @param requestCode request Code
     */
    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode) {
        activity.startActivityForResult(new Intent(activity, cls), requestCode);
    }
}
