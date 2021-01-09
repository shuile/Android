package com.cyt.testbinder.testBinderPool.activity;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cyt.sdk_base.utils.LogUtil;
import com.cyt.testbinder.R;
import com.cyt.testbinder.aidl.ICompute;
import com.cyt.testbinder.aidl.ISecurityCenter;
import com.cyt.testbinder.testBinderPool.BinderPool;
import com.cyt.testbinder.testBinderPool.ComputeImpl;
import com.cyt.testbinder.testBinderPool.SecurityCenterImpl;

public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";

    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        LogUtil.d(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        LogUtil.d(TAG, "content:" + msg);
        try {
            String password = mSecurityCenter.encrypt(msg);
            LogUtil.d(TAG, "encrypt: " + password);
            LogUtil.d(TAG, "decrypt: " + mSecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LogUtil.d(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        try {
            LogUtil.d(TAG, "3+5=" + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}