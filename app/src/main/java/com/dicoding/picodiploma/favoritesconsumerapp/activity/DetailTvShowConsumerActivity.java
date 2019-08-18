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
import com.dicoding.picodiploma.favoritesconsumerapp.models.TvShowModel;
import com.dicoding.picodiploma.favoritesconsumerapp.utils.Config;
import com.google.android.material.appbar.CollapsingToolbarLayout;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.CONTENT_URI_TV;
import static com.dicoding.picodiploma.favoritesconsumerapp.utils.ContentValueHelper.getContentValueTv;

public class DetailTvShowConsumerActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_TV = "extra_tv";
    public static String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_TV_UPDATE = 102;
    public static final int RESULT_TV_ADD = 302;
    public static final int RESULT_TV_DELETE = 202;
    private Uri uri;
    private TvShowModel tvShowModel;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show_consumer);
        ButterKnife.bind(this);

        // cek apakah data sudah ada di database atau belum
        // jika ada, ambil data yang diminta dari database
        // lalu masukan ke objek TvShowModel, dan set isFavorite menjadi true
        uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri,
                    null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) tvShowModel = new TvShowModel(cursor);
                cursor.close();
                isFavorite = true;
            }
        }

        // load data dari tvShowModel ke View
        txtOverview.setText(tvShowModel.getOverview());
        txtDate.setText(tvShowModel.getReleaseDate());
        txtLanguage.setText(tvShowModel.getOriginalLanguage());
        txtVoteAverage.setText(String.valueOf(tvShowModel.getVoteAverage()));
        txtVoteCount.setText(String.valueOf(tvShowModel.getVoteCount()));
        txtPopular.setText(String.valueOf(tvShowModel.getPopularity()));
        collapsingToolbarLayout.setTitle(tvShowModel.getTitle());
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + tvShowModel.getPosterPath();
        Glide.with(this).load(urlPhoto).apply(new RequestOptions()).into(imgPhoto);

        imgFavorite.setOnClickListener(this);

        //  statement dibawah ini untuk menginfokan apakah data sudah ada di database atau belum
        if (isFavorite) {
            // jika data sudah ada di datbase maka jalanakan perintah dibawah ini
            Glide.with(this)
                    .load(R.drawable.ic_favorite_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            txtFavorite.setText(getString(R.string.unfavorite));
        } else {
            // jika data belum ada di database maka jalankan perintah ini
            Glide.with(this)
                    .load(R.drawable.ic_favorite_border_black_24dp)
                    .apply(new RequestOptions().override(36, 36))
                    .into(imgFavorite);
            txtFavorite.setText(getString(R.string.favorite));
        }
    }

    @Override
    public void onClick(View v) {
        // jika icon favorite di tekan
        if (v.getId() == R.id.img_favorite) {
            // ambil data dari intent dan masukan ke variabel position
            // lalu masukan variabel tersebut ke intent yang baru bersamaan data TvShowModel
            int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_POSITION, position);
            intent.putExtra(EXTRA_TV, tvShowModel);

            if (!isFavorite) {
                // jika  data belum ada di database, maka akan menjalankan program dibawah ini
                Glide.with(this)
                        .load(R.drawable.ic_favorite_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                isFavorite = true;
                txtFavorite.setText(getString(R.string.unfavorite));
                Toast.makeText(this, tvShowModel.getTitle() + " " +
                        getString(R.string.add_favorite), Toast.LENGTH_SHORT).show();
                isFavorite = true;

                // object movieResult akan di konversi menjadi objet Content Value,
                // object content value inilah yang nantinya akan dimasukan ke database
                ContentValues values = getContentValueTv(tvShowModel);
                getContentResolver().insert(CONTENT_URI_TV, values);

                // Intent yang telah dibuat tadi dikirim ke Favorite Movie dengan Flag Movie RESULT_MOVIE_ADD
                // statement ini berfungsi untuk animasi penambahan data di recyclerview
                setResult(RESULT_TV_ADD, intent);
            } else {
                // jika  data belum ada di database, maka akan menjalankan program dibawah in
                Glide.with(this)
                        .load(R.drawable.ic_favorite_border_black_24dp)
                        .apply(new RequestOptions().override(36, 36))
                        .into(imgFavorite);
                txtFavorite.setText(getString(R.string.favorite));
                Toast.makeText(this, tvShowModel.getTitle() + " " +
                        getString(R.string.delete_favorite), Toast.LENGTH_SHORT).show();
                isFavorite = false;

                // mendelete data movie yang telah tersimpan di database
                getContentResolver().delete(uri, null, null);

                // Intent yang telah dibuat tadi dikirim ke Favorite Movie dengan Flag Movie RESULT_MOVIE_DELETE
                // statement ini berfungsi untuk animasi penghapusan data di recyclerview
                setResult(RESULT_TV_DELETE, intent);
            }
        }
    }
}
