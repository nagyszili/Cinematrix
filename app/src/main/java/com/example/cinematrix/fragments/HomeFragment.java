package com.example.cinematrix.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinematrix.Movie;
import com.example.cinematrix.MovieResult;
import com.example.cinematrix.MoviesResponse;
import com.example.cinematrix.R;
import com.example.cinematrix.TopMoviesAdapter;
import com.example.cinematrix.api.Client;
import com.example.cinematrix.api.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public static String BASE_URL = "https://api.themoviedb.org";
    public static int PAGE = 1;
    public static String API_KEY = "99fd430c3be6ec05d080de47353ce38e";
    public static String LANGUAGE = "en-US";
    public static String CATEGORY = "popular";

    private Context context;
    private TopMoviesAdapter adapter;
    private List<MovieResult> movies;
    private RecyclerView recyclerView;
    private Service movieService;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.homeFragmentRecyclerView);
        movies = new ArrayList<>();

        movieService = Client.getClient().create(Service.class);

        loadMovies();

//        adapter = new TopMoviesAdapter(getContext(), movies);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            /**
//             * z
//             * @param recyclerView
//             * @param dx
//             * @param dy
//             */
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
//                    if (PAGE <= 5) {
//                        loadMoreData(PAGE);
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                        ++PAGE;
//                    } else {
//                        return;
//                    }
//                }
//            }
//        });

        return view;
    }

    private void loadMovies() {

//        Client client = new Client();
        Service apiService = Client.getClient().create(Service.class);

        Call<MoviesResponse> call = apiService.getPopularMovies(API_KEY, PAGE);
        ++PAGE;

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                MoviesResponse result = response.body();
                List<MovieResult> movieList = result.getMovieResults();
                Log.d("HomeFragmentResponse", response.toString());
//                Toast.makeText(getContext(), movieList.get(0).getTitle(), Toast.LENGTH_SHORT).show();


//                movies.addAll(movieList);
//                recyclerView.setAdapter(new TopMoviesAdapter(getContext(),movieList));
//                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("HomeFragmentResponse", "Failed api call!");

            }
        });


    }

    public void loadMoreData(int pageNumber) {

//        Client client = new Client();
        Service apiService = Client.getClient().create(Service.class);

        Call<MoviesResponse> call = apiService.getPopularMovies(API_KEY, pageNumber);


        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<MovieResult> movieList = response.body().getMovieResults();
                movies.addAll(movieList);

                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
