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
import com.dicoding.picodiploma.favoritesconsumerapp.activity.DetailTvShowConsumerActivity;
import com.dicoding.picodiploma.favoritesconsumerapp.adapters.TvShowConsumerAdapter;
import com.dicoding.picodiploma.favoritesconsumerapp.models.TvShowModel;
import com.dicoding.picodiploma.favoritesconsumerapp.utils.ItemClickSupport;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dicoding.picodiploma.favoritesconsumerapp.db.DatabaseContract.TvShowColumns.CONTENT_URI_TV;
import static com.dicoding.picodiploma.favoritesconsumerapp.utils.MappingHelper.mapCursorToArrayListTv;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowConsumerFragment extends Fragment implements LoadCallback {
    private TvShowConsumerAdapter tvShowConsumerAdapter;
    private ArrayList<TvShowModel> listTv;
    @BindView(R.id.rv_tv_show)
    RecyclerView rvTvShow;

    public TvShowConsumerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show_consumer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // inisialisasi RecyclerView, Adapter, HandlerThead dan Data Observer
        rvTvShow.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvTvShow.setHasFixedSize(true);
        tvShowConsumerAdapter = new TvShowConsumerAdapter(view.getContext());
        rvTvShow.setAdapter(tvShowConsumerAdapter);

        // statement ini berfungsi supaya observasi data di lakukan di worker thread
        HandlerThread handlerThread = new HandlerThread("DataObserverTv");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        // data observer berfungsi untuk notify/ mengawasi ketika terjadi perubahan data
        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getContext() != null) {
            getContext().getContentResolver().registerContentObserver(
                    CONTENT_URI_TV, true, myObserver);
        }

        // jalankan kelas getDataTv untuk mengakses query ke database di worker thread
        new getDataTv(getContext(), this).execute();

        // statement ini berfungsi supaya user dapat mengakses setiap data
        // ketika data telah dipilih maka detail activity dari data yang dipilih akan ditampilkan
        ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) -> {
            Uri uri = Uri.parse(CONTENT_URI_TV + "/" + listTv.get(position).getId());
            Intent intent = new Intent(recyclerView.getContext(), DetailTvShowConsumerActivity.class);
            intent.setData(uri);
            intent.putExtra(DetailTvShowConsumerActivity.EXTRA_TV, listTv.get(position));
            intent.putExtra(DetailTvShowConsumerActivity.EXTRA_POSITION, position);
            startActivityForResult(intent, DetailTvShowConsumerActivity.REQUEST_TV_UPDATE);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // method ini akan dijalankan ketika fragment berada di state onResume
        // statement ini akan menjalankan query ke database
        new getDataTv(getContext(), this).execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // method ini digunakan untuk notifikasi ke adapter setiap kali ada penambahan atau pengurangan data
        if (requestCode == DetailTvShowConsumerActivity.REQUEST_TV_UPDATE) {
            if (resultCode == DetailTvShowConsumerActivity.RESULT_TV_DELETE) {
                int position = data.getIntExtra(DetailTvShowConsumerActivity.EXTRA_POSITION, 0);
                tvShowConsumerAdapter.removeItem(position);
            } else if (resultCode == DetailTvShowConsumerActivity.RESULT_TV_ADD) {
                TvShowModel tvShowModel = data.getParcelableExtra(DetailTvShowConsumerActivity.EXTRA_TV);
                tvShowConsumerAdapter.addItem(tvShowModel);
            }
        }
    }

    @Override
    public void postExecute(Cursor cursor) {
        // method ini merupakan hasil output dari kelas getData
        // statement dibawah ini berfungsi untuk memproses data yang telah di query dari database
        // lalu datanya akan ditampilkan melalui adapter
        listTv = mapCursorToArrayListTv(cursor);
        if (listTv != null) {
            tvShowConsumerAdapter.setListTv(listTv);
        } else {
            Toast.makeText(getContext(), getString(R.string.no_data), Toast.LENGTH_SHORT).show();
        }
    }

    // class ini bertugas untuk melakukan proses query data ke database dengan menggunakan worker thread
    private static class getDataTv extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        getDataTv(Context context, LoadCallback callback) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI_TV,
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
            new getDataTv(context, (MainActivity) context).execute();
        }
    }
}

