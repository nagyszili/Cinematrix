package com.example.cinematrix.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinematrix.R;
import com.example.cinematrix.adapters.FavoriteMovieAdapter;
import com.example.cinematrix.adapters.TopMoviesAdapter;
import com.example.cinematrix.api.MovieResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private FavoriteMovieAdapter adapter;
    private List<MovieResult> movies;

    private FirebaseDatabase database;
    private DatabaseReference dbUser;
    private SharedPreferences mPreferences;
//    private SharedPreferences.Editor mEditor;

    public FavoritesFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.favoriteRecyclerView);
        movies = new ArrayList<>();

        getFavoriteMovies();

        return view;
    }


    private void getFavoriteMovies() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userKey = mPreferences.getString("userKey", "");
        database = FirebaseDatabase.getInstance();
        if (!userKey.isEmpty()) {
            dbUser = database.getReference("users").child(userKey);
            dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("favoriteMovies").exists()) {
                        for (DataSnapshot dbMovie : dataSnapshot.child("favoriteMovies").getChildren())
                        {
                            MovieResult movie = dbMovie.getValue(MovieResult.class);
                            movies.add(movie);

                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            adapter = new FavoriteMovieAdapter(context, movies,getFragmentManager());
                            recyclerView.setAdapter(adapter);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }
}
