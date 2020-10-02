// IOnNewBookArrivedListener.aidl
package com.cyt.testbinder.aidl;

// Declare any non-default types here with import statements
import com.cyt.testbinder.aidl.Book;

interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book newBook);
}
