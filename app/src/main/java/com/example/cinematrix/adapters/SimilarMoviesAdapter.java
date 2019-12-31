package com.example.cinematrix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinematrix.R;
import com.example.cinematrix.api.MovieResult;
import com.example.cinematrix.fragments.DetailDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder> {

    private Context context;
    private List<MovieResult> movies;
    private FragmentManager fragmentManager;
    private DetailDialogFragment dialogFragment;




    public SimilarMoviesAdapter(Context context, List<MovieResult> movies, FragmentManager fragmentManager,DetailDialogFragment detailDialogFragment) {
        this.context = context;
        this.movies = movies;
        this.fragmentManager = fragmentManager;
        this.dialogFragment = detailDialogFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.similar_movie_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieResult movie = movies.get(position);

        Glide.with(holder.imageView)
                .load(movie.getImage())
                .into(holder.imageView);

        holder.textView.setText(movie.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailDialogFragment detail = new DetailDialogFragment(movie, context);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                detail.show(transaction,"detail");
                dialogFragment.dismiss();

            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.similarMovieImage);
            textView = itemView.findViewById(R.id.similarMovieTitle);
        }
    }
}
