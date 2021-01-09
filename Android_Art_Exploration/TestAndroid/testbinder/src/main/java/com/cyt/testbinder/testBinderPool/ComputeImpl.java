package com.cyt.testbinder.testBinderPool;

import android.os.RemoteException;

import com.cyt.testbinder.aidl.ICompute;

/**
 * Created by shui on 2020/10/5
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
