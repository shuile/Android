package com.cyt.testbinder.testAIDL;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.cyt.sdk_base.utils.LogUtil;
import com.cyt.sdk_base.utils.MyConstant;
import com.cyt.testbinder.aidl.Book;
import com.cyt.testbinder.aidl.IBookManager;
import com.cyt.testbinder.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 122668
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);

    // CopyOnWriteArrayList支持并发读写，线程同步
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<IOnNewBookArrivedListener>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//            } else {
//                LogUtil.d(TAG, "already exists.");
//            }
//            LogUtil.d(TAG, "registerListener, size: " + mListenerList.size());
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//                LogUtil.d(TAG, "unregister listener succeed.");
//            } else {
//                LogUtil.d(TAG, "not found, can not unregister");
//            }
//            LogUtil.d(TAG, "unregisterListener, current size: " + mListenerList.size());
            mListenerList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "iOS"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission(MyConstant.PERMISSION_ACCESS_BOOK_SERVICE);
        if (check == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
//        LogUtil.d(TAG, "onNewBookArrived, notify listeners: " + mListenerList.size());
//        for (int i = 0; i < mListenerList.size(); i++) {
//            IOnNewBookArrivedListener listener = mListenerList.get(i);
//            LogUtil.d(TAG, "onNewBookArrived, notify listener: " + listener);
//            listener.onNewBookArrived(book);
//        }
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    listener.onNewBookArrived(book);
                }catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mListenerList.finishBroadcast();
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            // do background processing here......
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
