package com.example.cinematrix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class TopMoviesAdapter extends RecyclerView.Adapter<TopMoviesAdapter.TopMovieViewHolder> {

    private Context context;
    private List<MovieResult> movies;

//    public TopMoviesAdapter(Context context) {
//        this.context = context;
//        this.movies = new ArrayList<>();
//
//    }

    public TopMoviesAdapter(Context context, List<MovieResult> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public TopMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_movie_list_item, parent, false);
        TopMovieViewHolder viewHolder = new TopMovieViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopMovieViewHolder holder, int position) {

        MovieResult movie = movies.get(position);

        Glide.with(holder.imageView)
                .load(movie.getPosterPath())
                .into(holder.imageView);

        holder.textView.setText(movie.getTitle());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class TopMovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public TopMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.topMovieImage);
            textView = itemView.findViewById(R.id.topMovieTitle);

        }
    }
}
