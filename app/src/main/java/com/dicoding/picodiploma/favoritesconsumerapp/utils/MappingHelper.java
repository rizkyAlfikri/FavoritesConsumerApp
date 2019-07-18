package com.dicoding.picodiploma.favoritesconsumerapp.utils;

import android.database.Cursor;

import com.dicoding.picodiploma.favoritesconsumerapp.models.MovieModel;

import java.util.ArrayList;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.DATE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.ID;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.LANGUAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.OVERVIEW;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.POPULAR;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.POSTER;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.TITLE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.VOTE_COUNT;

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
            movieList.add(new MovieModel(overview, originalLanguage, title, posterPath,
                    releaseDate, voteAverage, popularity, id, voteCount));
        }
        return movieList;
    }
}
