// IBookManager.aidl
package com.cyt.testbinder.aidl;

// Declare any non-default types here with import statements

import com.cyt.testbinder.aidl.Book;
import com.cyt.testbinder.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
