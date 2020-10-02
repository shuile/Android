package com.cyt.testbinder.testAIDL.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyt.sdk_base.utils.LogUtil;
import com.cyt.sdk_base.utils.MyConstant;
import com.cyt.testbinder.R;
import com.cyt.testbinder.aidl.Book;
import com.cyt.testbinder.aidl.IBookManager;
import com.cyt.testbinder.aidl.IOnNewBookArrivedListener;
import com.cyt.testbinder.testAIDL.BookManagerService;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";

    private IBookManager mRemoteBookManager;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstant.MSG_NEW_BOOK_ARRIVED:

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager = bookManager;
                List<Book> list = bookManager.getBookList();
                LogUtil.i(TAG, "query book list, list type: " + list.getClass().getCanonicalName());
                LogUtil.i(TAG, "query book list: " + list.toString());
                Book newBook = new Book(3, "Android进阶");
                bookManager.addBook(newBook);
                LogUtil.i(TAG, "add book: " + newBook);
                List<Book> newList = bookManager.getBookList();
                LogUtil.i(TAG, "query book list: " + newList.toString());
                bookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteBookManager = null;
            LogUtil.e(TAG, "binder died.");
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(MyConstant.MSG_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                LogUtil.i(TAG, "unregister listener: " + mOnNewBookArrivedListener);
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        super.onDestroy();
    }
}