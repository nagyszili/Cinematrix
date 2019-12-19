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

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    private Context context;
    private List<MovieResult> myMovies;
    private FragmentManager fragmentManager;

    public FavoriteMovieAdapter(Context context, List<MovieResult> myMovies,FragmentManager fragmentManager) {
        this.context = context;
        this.myMovies = myMovies;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.favorite_movie_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MovieResult movie = myMovies.get(position);

        Glide.with(holder.image)
                .load(movie.getImage())
                .into(holder.image);

        holder.title.setText(movie.getTitle());

        holder.description.setText(movie.getOverview());

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
        return myMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;
        private TextView description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.favoriteMovieImage);
            title = itemView.findViewById(R.id.favoriteMovieTitle);
            description = itemView.findViewById(R.id.favoriteMovieDescription);

        }
    }
}
