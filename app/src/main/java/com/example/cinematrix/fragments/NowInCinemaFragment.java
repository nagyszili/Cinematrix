package com.example.cinematrix.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinematrix.R;
import com.example.cinematrix.adapters.TopMoviesAdapter;
import com.example.cinematrix.api.MovieResponse;
import com.example.cinematrix.api.MovieResult;
import com.example.cinematrix.api.Service;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NowInCinemaFragment extends Fragment {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public int PAGE = 1;
    public static String API_KEY = "99fd430c3be6ec05d080de47353ce38e";

    private Context context;
    private TopMoviesAdapter adapter;
    private List<MovieResult> movies = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;


    public NowInCinemaFragment(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_now_in_cinema, container, false);

        recyclerView = view.findViewById(R.id.nowPlayingRV);
        layoutManager = new LinearLayoutManager(context);

        getMovies();

        return view;
    }

    private void getMovies() {

        Call<MovieResponse> call = apiInterface().getNowPlaying(API_KEY, PAGE);


        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse responseBody = response.body();
                try {
                    List<MovieResult> movieList = responseBody.getMovieResults();
//                    Toast.makeText(getContext(), movieList.get(0).getTitle(), Toast.LENGTH_SHORT).show();

                    movies.addAll(movieList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    adapter = new TopMoviesAdapter(context, movies,getFragmentManager());
                    adapter.setOnBottomReachedListener(position -> loadMoreData(++PAGE));
                    recyclerView.setAdapter(adapter);
                    recyclerView.getAdapter().notifyDataSetChanged();

                } catch (Exception e) {
//                    Toast.makeText(getContext(), "Something went wrong:" + e.toString(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("HomeFragmentResponse", "Failed api call!");

            }
        });

    }

    private void loadMoreData(int pageNumber) {

        Call<MovieResponse> call = apiInterface().getNowPlaying(API_KEY, pageNumber);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieResult> movieList = response.body().getMovieResults();
                movies.addAll(movieList);

                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }




    private Service apiInterface() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service apiService = retrofit.create(Service.class);

        return apiService;
    }

}
