package com.dicoding.picodiploma.favoritesconsumerapp.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.DATE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.ID;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.LANGUAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.OVERVIEW;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.POPULAR;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.POSTER;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.TITLE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.VOTE_COUNT;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.getColumnInt;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.getColumnString;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.getCoulumnDouble;

public class MovieModel implements Parcelable {
    private String overview;
    private String originalLanguage;
    private String title;
//    private List<Integer> genreIds;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;
    private double popularity;
    private int id;
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.voteAverage);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.id);
        dest.writeInt(this.voteCount);
    }

    public MovieModel() {
    }

    public MovieModel(String overview, String originalLanguage, String title, String posterPath,
                      String releaseDate, double voteAverage, double popularity, int id,
                      int voteCount) {

        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.id = id;
        this.voteCount = voteCount;
    }

    public MovieModel(Cursor cursor) {
        this.overview = getColumnString(cursor, OVERVIEW);
        this.originalLanguage = getColumnString(cursor, LANGUAGE);
        this.title = getColumnString(cursor, TITLE);
        this.posterPath = getColumnString(cursor, POSTER);
        this.releaseDate = getColumnString(cursor, DATE);
        this.voteAverage = getCoulumnDouble(cursor, VOTE_AVERAGE);
        this.popularity = getCoulumnDouble(cursor, POPULAR);
        this.id = getColumnInt(cursor, ID);
        this.voteCount = getColumnInt(cursor, VOTE_COUNT);
    }

    protected MovieModel(Parcel in) {
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.popularity = in.readDouble();
        this.id = in.readInt();
        this.voteCount = in.readInt();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
