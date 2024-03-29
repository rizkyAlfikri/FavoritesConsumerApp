package com.dicoding.picodiploma.favoritesconsumerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.picodiploma.favoritesconsumerapp.R;
import com.dicoding.picodiploma.favoritesconsumerapp.models.MovieModel;
import com.dicoding.picodiploma.favoritesconsumerapp.utils.Config;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieConsumerAdapter extends RecyclerView.Adapter<MovieConsumerAdapter.MovieViewHolder> {
    private Context context;
    private ArrayList<MovieModel> listMovie = new ArrayList<>();

    // method construct
    public MovieConsumerAdapter(Context context) {
        this.context = context;
    }

    // set data array list movieModel ke adapter untuk di proses
    public void setListMovie(ArrayList<MovieModel> listMovie) {
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    // adapter melakukan notifikasi ketika ada penambahan data
    public void addItem(MovieModel movieResults) {
        this.listMovie.add(movieResults);
        notifyItemInserted(listMovie.size() - 1);
    }

    // adapter melakukan notifikasi ketika ada pengurangan data
    public void removeItem(int position) {
        this.listMovie.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.listMovie.size());
    }

    @NonNull
    @Override
    public MovieConsumerAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // inflate file layout yang akan digunakan
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieConsumerAdapter.MovieViewHolder holder, int position) {
        // load data movieModel ke view adapter
        holder.txtTitle.setText(listMovie.get(position).getTitle());
        holder.txtDate.setText(listMovie.get(position).getReleaseDate());
        holder.txtRate.setText(String.valueOf(listMovie.get(position).getVoteAverage()));
        holder.txtGenre.setText(listMovie.get(position).getGenre());

        // load image data
        String urlPhoto = Config.IMAGE_URL_BASE_PATH + listMovie.get(position).getPosterPath();
        Glide.with(context)
                .load(urlPhoto)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        // jika listMovie tidak null, maka adapter akan menampilkan semua data yang ada
        // jika listMovie null, maka adapter tidak akan menampilkan data
        if (listMovie != null) {
            return listMovie.size();
        } else {
            return 0;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_rate)
        TextView txtRate;
        @BindView(R.id.txt_genre)
        TextView txtGenre;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
