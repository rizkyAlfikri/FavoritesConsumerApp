package com.dicoding.picodiploma.favoritesconsumerapp;

import android.database.Cursor;

public interface LoadCallback {
    void postExecute(Cursor cursor);
}
