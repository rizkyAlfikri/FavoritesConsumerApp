package com.dicoding.picodiploma.favoritesconsumerapp.utils;

import android.content.ContentValues;

import com.dicoding.picodiploma.favoritesconsumerapp.models.MovieModel;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.DATE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.ID;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.LANGUAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.OVERVIEW;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.POPULAR;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.POSTER;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.TITLE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.VOTE_COUNT;

public class ContentValueHelper {
    public static ContentValues getContentValue(MovieModel movieModel) {
        ContentValues values = new ContentValues();
        values.put(ID, movieModel.getId());
        values.put(TITLE, movieModel.getTitle());
        values.put(OVERVIEW, movieModel.getOverview());
        values.put(LANGUAGE, movieModel.getOriginalLanguage());
        values.put(POSTER, movieModel.getPosterPath());
        values.put(DATE, movieModel.getReleaseDate());
        values.put(POPULAR, movieModel.getPopularity());
        values.put(VOTE_AVERAGE, movieModel.getVoteAverage());
        values.put(VOTE_COUNT, movieModel.getVoteCount());
        return values;
    }
}
