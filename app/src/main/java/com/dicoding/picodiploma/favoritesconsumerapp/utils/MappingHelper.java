package com.dicoding.picodiploma.favoritesconsumerapp.utils;

import android.database.Cursor;

import com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract;
import com.dicoding.picodiploma.favoritesconsumerapp.models.MovieModel;
import com.dicoding.picodiploma.favoritesconsumerapp.models.TvShowModel;

import java.util.ArrayList;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.DATE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.GENRE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.ID;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.LANGUAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.POPULAR;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.POSTER;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.TITLE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.VOTE_COUNT;

public class MappingHelper {
    public static ArrayList<MovieModel> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<MovieModel> movieList = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(OVERVIEW));
            String originalLanguage = movieCursor.getString(movieCursor.getColumnIndexOrThrow(LANGUAGE));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE));
            String posterPath = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTER));
            String releaseDate = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DATE));
            double voteAverage = movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            double popularity = movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(POPULAR));
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(ID));
            int voteCount = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(VOTE_COUNT));
            String genre = movieCursor.getString(movieCursor.getColumnIndexOrThrow(GENRE));
            movieList.add(new MovieModel(overview, originalLanguage, title, posterPath,
                    releaseDate, voteAverage, popularity, id, voteCount, genre));
        }
        return movieList;
    }

    public static ArrayList<TvShowModel> mapCursorToArrayListTv(Cursor tvCursor) {
        ArrayList<TvShowModel> tvList = new ArrayList<>();

        while (tvCursor.moveToNext()) {
            String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.OVERVIEW));
            String originalLanguage = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.LANGUAGE));
            String title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.TITLE));
            String posterPath = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.POSTER));
            String releaseDate = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.DATE));
            double voteAverage = tvCursor.getDouble(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.VOTE_AVERAGE));
            double popularity = tvCursor.getDouble(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.POPULAR));
            int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.ID));
            int voteCount = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.VOTE_COUNT));
            String genre = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.GENRE));
            tvList.add(new TvShowModel(overview, originalLanguage, title, posterPath, releaseDate,
                    voteAverage, popularity, id, voteCount, genre));
        }

        return tvList;
    }
}
