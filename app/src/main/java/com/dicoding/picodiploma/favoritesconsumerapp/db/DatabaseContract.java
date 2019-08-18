package com.dicoding.picodiploma.favoritesconsumerapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    // menentukan Authority dan scheme yang akan digunakan
    // fungsinya untuk alamat dari table yang akan digunakan
    private static final String AUTHORITY = "com.dicoding.picodiploma.finalsubmission";
    private static final String SCHEME = "content";

    // inisialisasi nama dan row table movie
    public static final class MovieColumns implements BaseColumns {
        static final String MOVIE_TABLE_NAME = "tableMovie";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String LANGUAGE = "language";
        public static final String POSTER = "poster";
        public static final String DATE = "date";
        public static final String POPULAR = "popular";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";
        public static final String GENRE = "genre";

        // menentukan uri untuk table movie
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(MOVIE_TABLE_NAME)
                .build();
     }

    // inisialisasi nama dan row table tv show
    public static final class TvShowColumns implements BaseColumns {
        static final String TV_TABLE_NAME = "tableTv";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String OVERVIEW = "overview";
        public static final String LANGUAGE = "language";
        public static final String GENRE = "genre";
        public static final String POSTER = "poster";
        public static final String DATE = "date";
        public static final String POPULAR = "popular";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT = "vote_count";

        // menentukan uri untuk table tv show
        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TV_TABLE_NAME)
                .build();
    }

    // method dibawah ini berguna untuk mengkonversi data cursor ke data primitive
    // dimana data primitive ini nantinya yang akan di manipulasi dan ditampilkan

    // konversi data cursor ke string
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    // konversi data cursor ke string
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    // konversi data cursor ke string
    public static double getCoulumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}
