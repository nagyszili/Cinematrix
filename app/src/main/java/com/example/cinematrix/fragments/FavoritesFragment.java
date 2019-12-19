package com.example.cinematrix.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinematrix.R;
import com.example.cinematrix.adapters.FavoriteMovieAdapter;

public class FavoritesFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private FavoriteMovieAdapter adapter;

    public FavoritesFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.favoriteRecyclerView);

        return view;
    }
}
