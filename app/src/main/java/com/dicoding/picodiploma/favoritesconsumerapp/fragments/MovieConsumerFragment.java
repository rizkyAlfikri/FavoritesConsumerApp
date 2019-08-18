package com.dicoding.picodiploma.favoritesconsumerapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dicoding.picodiploma.favoritesconsumerapp.utils.LoadCallback;
import com.dicoding.picodiploma.favoritesconsumerapp.activity.MainActivity;
import com.dicoding.picodiploma.favoritesconsumerapp.R;
import com.dicoding.picodiploma.favoritesconsumerapp.activity.DetailMovieConsumerActivity;
import com.dicoding.picodiploma.favoritesconsumerapp.adapters.MovieConsumerAdapter;
import com.dicoding.picodiploma.favoritesconsumerapp.models.MovieModel;
import com.dicoding.picodiploma.favoritesconsumerapp.utils.ItemClickSupport;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.dicoding.picodiploma.favoritesconsumerapp.utils.MappingHelper.mapCursorToArrayList;


public class MovieConsumerFragment extends Fragment implements LoadCallback {
    private MovieConsumerAdapter movieConsumerAdapter;
    private ArrayList<MovieModel> listMovie;
    @BindView(R.id.rv_movie)
    RecyclerView rvMovie;
    @BindString(R.string.no_data)
    String noData;

    public MovieConsumerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_consumer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // inisialisasi RecyclerView, Adapter, HandlerThead dan Data Observer
        rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovie.setHasFixedSize(true);
        movieConsumerAdapter = new MovieConsumerAdapter(view.getContext());
        rvMovie.setAdapter(movieConsumerAdapter);

        // statement ini berfungsi supaya observasi data di lakukan di worker thread
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        // data observer berfungsi untuk notify/ mengawasi ketika terjadi perubahan data
        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getContext() != null) {
            getContext().getContentResolver().registerContentObserver(CONTENT_URI,
                    true, myObserver);
        }

        // jalankan kelas getData untuk mengakses query ke database di worker thread
        new getData(getContext(), this).execute();

        // statement ini berfungsi supaya user dapat mengakses setiap data
        // ketika data telah dipilih maka detail activity dari data yang dipilih akan ditampilkan
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
            Uri uri = Uri.parse(CONTENT_URI + "/" + listMovie.get(position).getId());
            Intent intent = new Intent(recyclerView.getContext(), DetailMovieConsumerActivity.class);
            intent.setData(uri);
            intent.putExtra(DetailMovieConsumerActivity.EXTRA_POSITION, position);
            startActivityForResult(intent, DetailMovieConsumerActivity.REQUEST_MOVIE_UPDATE);
        });
    }

    @Override
    public void onResume() {
        // method ini akan dijalankan ketika fragment berada di state onResume
        // statement ini akan menjalankan query ke database
        super.onResume();
        new getData(getContext(), this).execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // method ini digunakan untuk notifikasi ke adapter setiap kali ada penambahan atau pengurangan data
        if (requestCode == DetailMovieConsumerActivity.REQUEST_MOVIE_UPDATE) {
            if (resultCode == DetailMovieConsumerActivity.RESULT_MOVIE_DELETE) {
                int position = data.getIntExtra(DetailMovieConsumerActivity.EXTRA_POSITION, 0);
                movieConsumerAdapter.removeItem(position);
                Toast.makeText(getContext(), getString(R.string.delete_item), Toast.LENGTH_SHORT).show();
            } else if (resultCode == DetailMovieConsumerActivity.RESULT_MOVIE_ADD) {
                MovieModel movieModel = data.getParcelableExtra(DetailMovieConsumerActivity.EXTRA_MOVIE);
                movieConsumerAdapter.addItem(movieModel);
            }
        }
    }


    @Override
    public void postExecute(Cursor cursor) {
        // method ini merupakan hasil output dari kelas getData
        // statement dibawah ini berfungsi untuk memproses data yang telah di query dari database
        // lalu datanya akan ditampilkan melalui adapter
        listMovie = mapCursorToArrayList(cursor);
        if (listMovie.size() > 0) {
            movieConsumerAdapter.setListMovie(listMovie);
        } else {
            Toast.makeText(getContext(), noData, Toast.LENGTH_LONG).show();
        }
    }

    // class ini bertugas untuk melakukan proses query data ke database dengan menggunakan worker thread
    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        private getData(Context context, LoadCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    // kelas ini unuk mengobservasi perubahan data dari database yang sedang ditampilkan
    static class DataObserver extends ContentObserver {

        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getData(context, (MainActivity) context).execute();
        }
    }
}
