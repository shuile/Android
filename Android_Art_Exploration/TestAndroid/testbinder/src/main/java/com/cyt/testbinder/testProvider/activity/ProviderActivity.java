package com.cyt.testbinder.testProvider.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyt.sdk_base.utils.LogUtil;
import com.cyt.sdk_base.utils.MyConstant;
import com.cyt.testbinder.R;
import com.cyt.testbinder.aidl.Book;
import com.cyt.testbinder.testBundle.UserS;

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri uri = Uri.parse("content://" + MyConstant.PROVIDER_AUTHORITY_BOOK);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);
        getContentResolver().query(uri, null, null, null, null);

        Uri bookUri = Uri.parse("content://" + MyConstant.PROVIDER_AUTHORITY_BOOK + "/book");
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            LogUtil.d(TAG, "query book: " + book.toString());
        }

        bookCursor.close();

        Uri userUri = Uri.parse("content://" + MyConstant.PROVIDER_AUTHORITY_BOOK + "/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            UserS userS = new UserS();
            userS.userId = userCursor.getInt(0);
            userS.userName = userCursor.getString(1);
            userS.isMale = userCursor.getInt(2) == 1;
            LogUtil.d(TAG, "query user: " + userS);
        }
        userCursor.close();
    }
}