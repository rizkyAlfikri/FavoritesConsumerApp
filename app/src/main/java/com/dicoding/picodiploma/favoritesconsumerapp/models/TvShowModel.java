package com.dicoding.picodiploma.favoritesconsumerapp.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.DATE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.GENRE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.ID;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.LANGUAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.OVERVIEW;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.POPULAR;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.POSTER;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.TITLE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.VOTE_AVERAGE;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.VOTE_COUNT;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.getColumnInt;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.getColumnString;
import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.getCoulumnDouble;

public class TvShowModel implements Parcelable {
    private String overview;
    private String originalLanguage;
    private String title;
    private String posterPath;
    private String releaseDate;
    private double voteAverage;
    private double popularity;
    private int id;
    private int voteCount;
    private String genre;

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public TvShowModel(String overview, String originalLanguage, String title, String posterPath,
                       String releaseDate, double voteAverage, double popularity, int id,
                       int voteCount, String genre) {

        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.id = id;
        this.voteCount = voteCount;
        this.genre = genre;
    }

    public TvShowModel(Cursor cursor) {
        this.overview = getColumnString(cursor, OVERVIEW);
        this.originalLanguage = getColumnString(cursor, LANGUAGE);
        this.title = getColumnString(cursor, TITLE);
        this.posterPath = getColumnString(cursor, POSTER);
        this.releaseDate = getColumnString(cursor, DATE);
        this.voteAverage = getCoulumnDouble(cursor, VOTE_AVERAGE);
        this.popularity = getCoulumnDouble(cursor, POPULAR);
        this.id = getColumnInt(cursor, ID);
        this.voteCount = getColumnInt(cursor, VOTE_COUNT);
        this.genre = getColumnString(cursor, GENRE);
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
        dest.writeString(this.genre);
    }

    private TvShowModel(Parcel in) {
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.popularity = in.readDouble();
        this.id = in.readInt();
        this.voteCount = in.readInt();
        this.genre = in.readString();
    }

    public static final Parcelable.Creator<TvShowModel> CREATOR = new Parcelable.Creator<TvShowModel>() {
        @Override
        public TvShowModel createFromParcel(Parcel source) {
            return new TvShowModel(source);
        }

        @Override
        public TvShowModel[] newArray(int size) {
            return new TvShowModel[size];
        }
    };
}
