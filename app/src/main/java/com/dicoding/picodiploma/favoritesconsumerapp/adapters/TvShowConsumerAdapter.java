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
import com.dicoding.picodiploma.favoritesconsumerapp.models.TvShowModel;
import com.dicoding.picodiploma.favoritesconsumerapp.utils.Config;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TvShowConsumerAdapter extends RecyclerView.Adapter<TvShowConsumerAdapter.TvViewHolder> {
    private Context context;
    private ArrayList<TvShowModel> listTv = new ArrayList<>();

    // method construct
    public TvShowConsumerAdapter(Context context) {
        this.context = context;
    }

    // set data array list movieModel ke adapter untuk di proses
    public void setListTv(ArrayList<TvShowModel> listTv) {
        this.listTv.clear();
        this.listTv.addAll(listTv);
        notifyDataSetChanged();
    }

    // adapter melakukan notifikasi ketika ada penambahan data
    public void addItem(TvShowModel tvShowModel) {
        this.listTv.add(tvShowModel);
        notifyItemInserted(listTv.size() - 1);
    }

    // adapter melakukan notifikasi ketika ada pengurangan data
    public void removeItem(int position) {
        this.listTv.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.listTv.size());
    }

    @NonNull
    @Override
    public TvShowConsumerAdapter.TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate file layout yang akan digunakan
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite_movie, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowConsumerAdapter.TvViewHolder holder, int position) {
        // load data movieModel ke view adapter
        holder.txtTitle.setText(listTv.get(position).getTitle());
        holder.txtDate.setText(listTv.get(position).getReleaseDate());
        holder.txtRate.setText(String.valueOf(listTv.get(position).getVoteAverage()));
        holder.txtGenre.setText(listTv.get(position).getGenre());
        String urlImage = Config.IMAGE_URL_BASE_PATH + listTv.get(position).getPosterPath();
        Glide.with(context)
                .load(urlImage)
                .apply(new RequestOptions().override(100, 140))
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        // jika listMovie tidak null, maka adapter akan menampilkan semua data yang ada
        // jika listMovie null, maka adapter tidak akan menampilkan data
        if (listTv != null) {
            return listTv.size();
        } else {
            return 0;
        }
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
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
        TvViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
