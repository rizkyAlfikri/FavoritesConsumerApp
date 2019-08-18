package com.dicoding.picodiploma.favoritesconsumerapp.utils;

import android.content.ContentValues;

import com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract;
import com.dicoding.picodiploma.favoritesconsumerapp.models.MovieModel;
import com.dicoding.picodiploma.favoritesconsumerapp.models.TvShowModel;

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
        values.put(GENRE, movieModel.getGenre());
        return values;
    }

    public static ContentValues getContentValueTv(TvShowModel tvShowModel) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TvShowColumns.ID, tvShowModel.getId());
        values.put(DatabaseContract.TvShowColumns.TITLE, tvShowModel.getTitle());
        values.put(DatabaseContract.TvShowColumns.OVERVIEW, tvShowModel.getOverview());
        values.put(DatabaseContract.TvShowColumns.LANGUAGE, tvShowModel.getOriginalLanguage());
        values.put(DatabaseContract.TvShowColumns.POSTER, tvShowModel.getPosterPath());
        values.put(DatabaseContract.TvShowColumns.DATE, tvShowModel.getReleaseDate());
        values.put(DatabaseContract.TvShowColumns.POPULAR, tvShowModel.getPopularity());
        values.put(DatabaseContract.TvShowColumns.VOTE_AVERAGE, tvShowModel.getVoteAverage());
        values.put(DatabaseContract.TvShowColumns.VOTE_COUNT, tvShowModel.getVoteCount());
        values.put(DatabaseContract.TvShowColumns.GENRE, tvShowModel.getGenre());
        return values;
    }
}
