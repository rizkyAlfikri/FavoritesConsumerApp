package com.dicoding.picodiploma.favoritesconsumerapp.utils;

import android.database.Cursor;

// interface ini digunakan untuk helper ketika melakukan proses asynchronous query data ke database
public interface LoadCallback {
    void postExecute(Cursor cursor);
}
