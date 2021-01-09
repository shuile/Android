package com.cyt.testbinder.testProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cyt.sdk_base.utils.LogUtil;
import com.cyt.sdk_base.utils.MyConstant;

/**
 * @author 122668
 */
public class BookProvider extends ContentProvider {

    private static final String TAG = "BookProvider";

    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + MyConstant.PROVIDER_AUTHORITY_BOOK + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + MyConstant.PROVIDER_AUTHORITY_BOOK + "/user");

    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MyConstant.PROVIDER_AUTHORITY_BOOK, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(MyConstant.PROVIDER_AUTHORITY_BOOK, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        LogUtil.d(TAG, "onCreate, current thread: " + Thread.currentThread().getName());
        mContext = getContext();
        // ContentProvider创建时，初始化数据库。注意：这里仅仅是为了演示，实际使用中不推荐在主线程中进行耗时的数据库操作
        initProviderData();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        LogUtil.d(TAG, "query, current thread: " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
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
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(table, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        LogUtil.d(TAG, "delete");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int count = mDb.delete(table, selection, selectionArgs);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        LogUtil.d(TAG, "update");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int row = mDb.update(table, values, selection, selectionArgs);
        if (row > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;
    }

    private void initProviderData() {
        mDb = new DbOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3, 'Android');");
        mDb.execSQL("insert into book values(4, 'Html5');");
        mDb.execSQL("insert into book values(5, 'iOS');");
        mDb.execSQL("insert into user values(1, 'jake', 1);");
        mDb.execSQL("insert into user values(2, 'jasmine', 0);");
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri))  {
            case BOOK_URI_CODE:
                tableName = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:

                break;
        }
        return tableName;
    }
}
