package com.dicoding.picodiploma.favoritesconsumerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.favoritesconsumerapp.R;
import com.dicoding.picodiploma.favoritesconsumerapp.models.MovieModel;
import com.dicoding.picodiploma.favoritesconsumerapp.utils.Config;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.CONTENT_URI;
import static com.dicoding.picodiploma.favoritesconsumerapp.utils.ContentValueHelper.getContentValue;

public class DetailMovieConsumerActivity extends AppCompatActivity implements View.OnClickListener {
    private Uri uri;
    private MovieModel movieModel;
    private boolean isFavorite = false;
    @BindView(R.id.txt_overview)
    TextView txtOverview;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_language)
    TextView txtLanguage;
    @BindView(R.id.txt_vote_average)
    TextView txtVoteAverage;
    @BindView(R.id.txt_vote_count)
    TextView txtVoteCount;
    @BindView(R.id.txt_favorite)
    TextView txtFavorite;
    @BindView(R.id.img_favorite)
    ImageView imgFavorite;
    @BindView(R.id.img_photo)
    ImageView imgPhoto;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindString(R.string.favorite)
    String favorite;
    @BindString(R.string.unfavorite)
    String unfavorite;
    @BindString(R.string.add_favorite)
    String addFavorite;
    @BindString(R.string.delete_favorite)
    String deleteFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_consumer);
        ButterKnife.bind(this);

        uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri,
                    null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) movieModel = new MovieModel(cursor);
                cursor.close();
                isFavorite = true;
            }
        }

        txtOverview.setText(movieModel.getTitle());
        txtDate.setText(movieModel.getReleaseDate());
        txtLanguage.setText(movieModel.getOriginalLanguage());
        txtVoteAverage.setText(String.valueOf(movieModel.getVoteAverage()));
        txtVoteCount.setText(String.valueOf(movieModel.getVoteCount()));
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + movieModel.getPosterPath();
        collapsingToolbarLayout.setTitle(movieModel.getTitle());
        Glide.with(this)
                .load(urlPhoto)
                .apply(new RequestOptions())
                .into(imgPhoto);
        imgFavorite.setOnClickListener(this);

        if (isFavorite) {
            Glide.with(this)
                    .load(R.drawable.ic_favorite_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            txtFavorite.setText(unfavorite);
        } else {
            Glide.with(this)
                    .load(R.drawable.ic_favorite_border_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            txtFavorite.setText(favorite);
        }
    }

    @Override
    public void onClick(View v) {
        if (!isFavorite) {
            Glide.with(this)
                    .load(R.drawable.ic_favorite_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            isFavorite = true;
            txtFavorite.setText(unfavorite);
            Toast.makeText(this, movieModel.getTitle() + " " + favorite, Toast.LENGTH_SHORT).show();
            isFavorite = true;
            ContentValues values = getContentValue(movieModel);
            getContentResolver().insert(CONTENT_URI, values);
        } else {
            Glide.with(this)
                    .load(R.drawable.ic_favorite_border_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            txtFavorite.setText(favorite);
            Toast.makeText(this, movieModel.getTitle() + " " + movieModel.getTitle(), Toast.LENGTH_SHORT).show();
            isFavorite = false;
            getContentResolver().delete(uri, null, null);
        }
    }
}
