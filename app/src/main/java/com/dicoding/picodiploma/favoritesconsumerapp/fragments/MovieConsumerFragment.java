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

import com.dicoding.picodiploma.favoritesconsumerapp.LoadCallback;
import com.dicoding.picodiploma.favoritesconsumerapp.MainActivity;
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

import static com.dicoding.picodiploma.favoritesconsumerapp.db.moviedb.MovieDatabaseContract.MovieColumns.CONTENT_URI;
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

        rvMovie.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvMovie.setHasFixedSize(true);
        movieConsumerAdapter = new MovieConsumerAdapter(view.getContext());
        rvMovie.setAdapter(movieConsumerAdapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getContext() != null) {
            getContext().getContentResolver().registerContentObserver(CONTENT_URI,
                    true, myObserver);
        }

        new getData(getContext(), this).execute();

        ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
            Uri uri = Uri.parse(CONTENT_URI + "/" + listMovie.get(position).getId());
            Intent intent = new Intent(recyclerView.getContext(), DetailMovieConsumerActivity.class);
            intent.setData(uri);
            startActivity(intent);
        });
    }

    @Override
    public void postExecute(Cursor cursor) {
        listMovie = mapCursorToArrayList(cursor);
        if (listMovie.size() > 0) {
            movieConsumerAdapter.setListMovie(listMovie);
        } else {
            Toast.makeText(getContext(), noData, Toast.LENGTH_LONG).show();
        }
    }

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
