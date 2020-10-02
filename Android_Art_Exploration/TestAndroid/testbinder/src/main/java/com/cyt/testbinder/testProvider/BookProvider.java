package com.cyt.testbinder.testProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cyt.sdk_base.utils.LogUtil;

/**
 * @author 122668
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";

    @Override
    public boolean onCreate() {
        LogUtil.d(TAG, "onCreate, current thread: " + Thread.currentThread().getName());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        LogUtil.d(TAG, "query, current thread: " + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        LogUtil.d(TAG, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        LogUtil.d(TAG, "insert");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        LogUtil.d(TAG, "delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        LogUtil.d(TAG, "update");
        return 0;
    }
}
