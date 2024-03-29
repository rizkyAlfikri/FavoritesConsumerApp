package com.dicoding.picodiploma.favoritesconsumerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.dicoding.picodiploma.favoritesconsumerapp.utils.ContentValueHelper.getContentValue;

public class DetailMovieConsumerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_MOVIE_UPDATE = 101;
    public static final int RESULT_MOVIE_ADD = 301;
    public static final int RESULT_MOVIE_DELETE = 201;
    private Uri uri;
    private MovieModel movieModel;
    private boolean isFavorite = false;
    @BindView(R.id.txt_overview)
    TextView txtOverview;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_popular)
    TextView txtPopular;
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

        // cek apakah data sudah ada di database atau belum
        // jika ada, ambil data yang diminta dari database
        // lalu masukan ke objek MovieModel, dan set isFavorite menjadi true
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

        // load data dari movieModel ke View
        txtOverview.setText(movieModel.getOverview());
        txtDate.setText(movieModel.getReleaseDate());
        txtLanguage.setText(movieModel.getOriginalLanguage());
        txtVoteAverage.setText(String.valueOf(movieModel.getVoteAverage()));
        txtVoteCount.setText(String.valueOf(movieModel.getVoteCount()));
        txtPopular.setText(String.valueOf(movieModel.getPopularity()));
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + movieModel.getPosterPath();
        collapsingToolbarLayout.setTitle(movieModel.getTitle());
        Glide.with(this)
                .load(urlPhoto)
                .apply(new RequestOptions())
                .into(imgPhoto);
        imgFavorite.setOnClickListener(this);

        //  statement dibawah ini untuk menginfokan apakah data sudah ada di database atau belum
        if (isFavorite) {
            // jika data sudah ada di datbase maka jalanakan perintah dibawah ini
            Glide.with(this)
                    .load(R.drawable.ic_favorite_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            txtFavorite.setText(unfavorite);
        } else {
            // jika data belum ada di database maka jalankan perintah ini
            Glide.with(this)
                    .load(R.drawable.ic_favorite_border_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            txtFavorite.setText(favorite);
        }
    }

    @Override
    public void onClick(View v) {
        // jika icon favorite di tekan
        if (v.getId() == R.id.img_favorite) {
            // ambil data dari intent dan masukan ke variabel position
            // lalu masukan variabel tersebut ke intent yang baru bersamaan data MovieModel
            int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOVIE, movieModel);
            intent.putExtra(EXTRA_POSITION, position);

            if (!isFavorite) {
                // jika  data belum ada di database, maka akan menjalankan program dibawah ini
                Glide.with(this)
                        .load(R.drawable.ic_favorite_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                isFavorite = true;
                txtFavorite.setText(unfavorite);
                Toast.makeText(this, movieModel.getTitle() + " " + favorite, Toast.LENGTH_SHORT).show();
                isFavorite = true;

                // object movieResult akan di konversi menjadi objet Content Value,
                // object content value inilah yang nantinya akan dimasukan ke database
                ContentValues values = getContentValue(movieModel);
                getContentResolver().insert(CONTENT_URI, values);

                // Intent yang telah dibuat tadi dikirim ke Favorite Movie dengan Flag Movie RESULT_MOVIE_ADD
                // statement ini berfungsi untuk animasi penambahan data di recyclerview
                setResult(RESULT_MOVIE_ADD, intent);
            } else {
                // jika  data belum ada di database, maka akan menjalankan program dibawah ini
                Glide.with(this)
                        .load(R.drawable.ic_favorite_border_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                txtFavorite.setText(favorite);
                Toast.makeText(this, movieModel.getTitle() + " " + getString(R.string.delete_favorite), Toast.LENGTH_SHORT).show();
                isFavorite = false;
                // mendelete data movie yang telah tersimpan di database
                getContentResolver().delete(uri, null, null);

                // Intent yang telah dibuat tadi dikirim ke Favorite Movie dengan Flag Movie RESULT_MOVIE_DELETE
                // statement ini berfungsi untuk animasi penghapusan data di recyclerview
                setResult(RESULT_MOVIE_DELETE, intent);
            }
        }
    }
}
