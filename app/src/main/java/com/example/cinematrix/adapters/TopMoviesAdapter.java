package com.example.cinematrix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinematrix.R;
import com.example.cinematrix.api.MovieResult;
import com.example.cinematrix.fragments.DetailDialogFragment;

import java.util.List;

public class TopMoviesAdapter extends RecyclerView.Adapter<TopMoviesAdapter.TopMovieViewHolder> {

    private Context context;
    private List<MovieResult> movies;
    private OnBottomReachedListener onBottomReachedListener;
    private FragmentManager fragmentManager;

//    public TopMoviesAdapter(Context context) {
//        this.context = context;
//        this.movies = new ArrayList<>();
//
//    }

    public TopMoviesAdapter(Context context, List<MovieResult> movies, FragmentManager fragmentManager) {
        this.context = context;
        this.movies = movies;
        this.fragmentManager = fragmentManager;
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

        if (position == movies.size() - 1) {

            onBottomReachedListener.onBottomReached(position);

        }

        MovieResult movie = movies.get(position);

        Glide.with(holder.imageView)
                .load(movie.getPosterPath())
                .into(holder.imageView);

        holder.movieTitle.setText(movie.getTitle());

        holder.movieDescription.setText(movie.getOverview());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDialogFragment detail = new DetailDialogFragment(movie, context);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                detail.show(transaction,"detail");


            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener;
    }

    class TopMovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView movieTitle, movieDescription;

        public TopMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.topMovieImage);
            movieTitle = itemView.findViewById(R.id.topMovieTitle);
            movieDescription = itemView.findViewById(R.id.topMovieDescription);

        }
    }
}
